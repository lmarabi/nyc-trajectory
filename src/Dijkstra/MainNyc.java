package Dijkstra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

import util.DirectedEdge;
import util.EdgeWeightedDigraph;
import util.In;
import util.Node;
import util.ReadMap;

/*
 * 
 * @author louai alarabi
 */

public class MainNyc {

	public static String nodePath = "/Users/louai/MEGA/scripts/nyc/nyc_networkGraph/copiedalghazali/LargeNYC/newnode.txt";
	public static String graphPath = "/Users/louai/MEGA/scripts/nyc/nyc_networkGraph/copiedalghazali/LargeNYC/newgraph.txt";
	public static String nycrowPath = "/Users/louai/MEGA/scripts/nyc/code/nycsample.txt";
	public static String nycTrajectoryPath = "/Users/louai/MEGA/scripts/nyc/yellow/sample_trajectory.txt";

	public MainNyc(String nodefile, String graphfile, String dataset,
			String trajectoryfile) {
		nodePath = nodefile;
		graphPath = graphfile;
		nycrowPath = dataset;
		nycTrajectoryPath = trajectoryfile;
	}

	private static Map<Integer, Node> nodeMap = null;

	private static int mapMatching(Node originalNode) {
		int nodeId = 0;
		double Mindistance = Double.MAX_VALUE;
		double dist;
		for (Map.Entry<Integer, Node> entry : nodeMap.entrySet()) {
			Node node = entry.getValue();
			dist = originalNode.getDistance(node);
			if (dist < Mindistance) {
				Mindistance = dist;
				nodeId = node.getId();
			}
		}
		return nodeId;
	}

	private static void processing() {
		// read node information
		nodeMap = ReadMap.getNodeMap(nodePath);
		// read graph information
		In in = new In(graphPath);
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(in, true);
		// create new file.
		FileWriter fwriter = null;
		try {
			fwriter = new FileWriter(new File(nycTrajectoryPath));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// read nyc row dataset.
		File file = new File(nycrowPath);
		InputStreamReader instream = null;
		BufferedReader br = null;
		try {
			instream = new InputStreamReader(new FileInputStream(file));
			br = new BufferedReader(instream);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String line = null;
		String outputline = null;
		String[] s;
		double start_longtitude, start_latitude, end_longtitude, end_latitude;
		String id, time;
		Node startNode;
		Node endNode;
		try {
			while ((line = br.readLine()) != null) {
				// ID,StartTime,Start_Long,Start_Lat,End_Long,End_Lat.
				if (line.contains(",")) {
					s = line.split(",");
					id = s[0];
					time = s[1];
					start_longtitude = Double.parseDouble(s[2]);
					start_latitude = Double.parseDouble(s[3]);
					end_longtitude = Double.parseDouble(s[4]);
					end_latitude = Double.parseDouble(s[5]);
					if (start_longtitude != 0 || end_longtitude != 0
							|| start_latitude != 0 || end_latitude != 0) {

						startNode = new Node(0, start_longtitude,
								start_latitude);
						endNode = new Node(0, end_longtitude, end_latitude);
						startNode.setId(mapMatching(startNode));
						endNode.setId(mapMatching(endNode));
						// Get the shortest path between start node and end node
						DijkstraSP sp = new DijkstraSP(G, startNode.getId());
						Iterable<DirectedEdge> path = sp
								.pathTo(endNode.getId());
						if (path != null) {
							System.out.println("Path " + startNode.getId()
									+ "--->" + endNode.getId());
							outputline = id + "," + time + ","
									+ start_longtitude + "&" + start_latitude;
							fwriter.write(outputline);
							for (DirectedEdge edge : path) {
								// int start = edge.from();
								int end = edge.to();
								// Node sNode = nodeMap.get(start);
								Node eNode = nodeMap.get(end);
								// System.out.println(sNode.toString() + "\t"
								// + eNode.toString());
								outputline = "," + eNode.getLongtitude() + "&"
										+ eNode.getLatitude();
								fwriter.write(outputline);

							}
							fwriter.write("\n");
						}
						// System.out.print("Cost of Distance: ");
						// System.out.println(sp.distTo(1343307));
					}

				}
			}
			fwriter.flush();
			fwriter.close();
			br.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		 if(args.length < 4){
		 System.out.println("Usage: nodefile graphfile rowdataset outputtrajectoryfile\n");
		 }
		 MainNyc mn = new MainNyc(args[0], args[1], args[2], args[3]);
		long t1 = System.currentTimeMillis(); 
		processing();
		long t2 = System.currentTimeMillis();
		System.out.print("Time for computing shortestpath: "+ (t2-t1) +" ms");
	}

}
