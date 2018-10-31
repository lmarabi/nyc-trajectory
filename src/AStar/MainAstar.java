package AStar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Node;
import util.ReadMap;
import AStar.Graph.Edge;
import AStar.Graph.Vertex;

/**
 * 
 * @author louai alarabi
 *
 */

public class MainAstar {

	public static String nodePath = "/Users/louai/MEGA/scripts/nyc/nyc_networkGraph/copiedalghazali/LargeNYC/newnode.txt";
	public static String graphPath = "/Users/louai/MEGA/scripts/nyc/nyc_networkGraph/copiedalghazali/LargeNYC/newgraph.txt";
	public static String nycrowPath = "/Users/louai/MEGA/scripts/nyc/code/nycsample.txt";
	public static String nycTrajectoryPath = "/Users/louai/MEGA/scripts/nyc/yellow/sample_trajectory.txt";
	private static Map<Integer, Node> nodeMap = null;
	private static Graph<Integer> graph = null;
	private static List<Vertex<Integer>> verticies = null;
	private static List<Edge<Integer>> edges = null;
	private static Map<Integer, Integer> vertixMapping = null;

	public MainAstar(String nodefile, String graphfile, String dataset,
			String trajectoryfile) {
		nodePath = nodefile;
		graphPath = graphfile;
		nycrowPath = dataset;
		nycTrajectoryPath = trajectoryfile;
	}

	private static void buildVerticies() {
		verticies = new ArrayList<Vertex<Integer>>();
		vertixMapping = new HashMap<Integer, Integer>();
		nodeMap = ReadMap.getNodeMap(nodePath);
		Vertex<Integer> vertex = null;
		int index = 0;
		for (Map.Entry<Integer, Node> entry : nodeMap.entrySet()) {
			Node node = entry.getValue();
			vertex = new Vertex<Integer>(node.getId(), node.getLatitude(),
					node.getLongtitude());
			verticies.add(vertex);
			vertixMapping.put(node.getId(), index);
			index++;
		}
	}

	private static int getVertexIndex(int checkID) {
		return vertixMapping.get(checkID);
	}

	private static void buildEdges() {
		edges = new ArrayList<Edge<Integer>>();
		File file = new File(graphPath);
		InputStreamReader instream = null;
		BufferedReader br = null;
		try {
			instream = new InputStreamReader(new FileInputStream(file));
			br = new BufferedReader(instream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String line = "";
		Edge<Integer> edge = null;
		Node startNode = null;
		Node endNode = null;
		Vertex<Integer> endVertex = null;
		Vertex<Integer> startVertex = null;
		try {
			while ((line = br.readLine()) != null) {
				if (line.contains(" ")) {
					String[] array = line.split(" ");
					int start = Integer.parseInt(array[1]);
					int end = Integer.parseInt(array[2]);
					double cost = Double.parseDouble(array[3]);
					startVertex = verticies.get(getVertexIndex(start));
					endVertex = verticies.get(getVertexIndex(end));
					edge = new Graph.Edge<Integer>(cost, startVertex, endVertex);
					edges.add(edge);
				}

			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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

	public static void processing() {
		// initialize + construct the verticies.
		long t1 = System.currentTimeMillis();
		buildVerticies();
		long t2 = System.currentTimeMillis();
		System.out.println("Time for building Verticies: " + (t2 - t1) + " ms");
		// initialize + construct edges.
		t1 = System.currentTimeMillis();
		buildEdges();
		t2 = System.currentTimeMillis();
		System.out.println("Time for building edges: " + (t2 - t1) + " ms");
		// create the graph
		t2 = System.currentTimeMillis();
		graph = new Graph<Integer>(verticies, edges);
		t2 = System.currentTimeMillis();
		System.out.println("Time for building graph: " + (t2 - t1) + " ms");
		// Compute The Shortestpath.

		t1 = System.currentTimeMillis();
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
		Vertex<Integer> startV;
		Vertex<Integer> endV;
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
						startV = verticies
								.get(getVertexIndex(startNode.getId()));
						endV = verticies.get(getVertexIndex(endNode.getId()));
						// Get the shortest path between start node and end node
						AStar<Integer> aStar = new AStar<Integer>();
						List<Edge<Integer>> path = aStar.aStar(graph, startV,
								endV);
						if (path != null) {
							System.out.println("Path " + startNode.getId()
									+ "--->" + endNode.getId());
							outputline = id + "," + time + ","
									+ start_longtitude + "&" + start_latitude;
							fwriter.write(outputline);
							for (Edge<Integer> edge : path) {
								endV = edge.getToVertex();
								// System.out.println(sNode.toString() + "\t"
								// + eNode.toString());
								outputline = "," + endV.getLongtitude() + "&"
										+ endV.getLatitude();
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

		t2 = System.currentTimeMillis();
		System.out.println("Time for computing shortest Path graph: "
				+ (t2 - t1) + " ms");

	}

	public static void main(String[] args) {
		if (args.length < 4) {
			System.out
					.println("Usage: nodefile graphfile rowdataset outputtrajectoryfile\n");
		}
		MainAstar mn = new MainAstar(args[0], args[1], args[2], args[3]);
		long t1 = System.currentTimeMillis();
		processing();
		long t2 = System.currentTimeMillis();
		System.out.println("Total Time: " + (t2 - t1) + " ms");
	}

}
