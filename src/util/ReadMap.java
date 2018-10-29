package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author Louai Alarabi
 *
 */
public class ReadMap implements Serializable {
	private static Map<Integer, Node> nodeMap = null;
	private static Map<Integer, Edge> edgeMap = null;
	private static EdgeWeightedDigraph edgeWeigthDigraph = null;

	// Mapping id's from Long to Integer.
	private static Map<Long, Integer> mappingLongToInt = null;
	private static int numberEdges = 0;

	private static String NODEPATH = "/Users/louai/MEGA/scripts/nyc/nyc_networkGraph/LargeNYC/node.txt";
	private static String EDGEPATH = "/Users/louai/MEGA/scripts/nyc/nyc_networkGraph/LargeNYC/edge.txt";
	private static String DIJKSTRAPATH = "/Users/louai/MEGA/scripts/nyc/nyc_networkGraph/LargeNYC/graph.txt";
	
	private static String newNODEPATH = "/Users/louai/MEGA/scripts/nyc/nyc_networkGraph/LargeNYC/node.txt";
	private static String newEDGEPATH = "/Users/louai/MEGA/scripts/nyc/nyc_networkGraph/LargeNYC/edge.txt";

	public static Map<Integer, Node> getNodeMap() {
		if (mappingLongToInt == null) {
			convertNodeIdFromLongToInt();
		}
		// long t1 = System.currentTimeMillis();
		if (nodeMap == null) {
			try {
				System.out.println("reading node");
				nodeMap = new HashMap<Integer, Node>();
				File file = new File(NODEPATH);
				InputStreamReader in = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader br = new BufferedReader(in);

				String line = null;
				String[] s;
				double longtitude, latitude;
				long id;
				while ((line = br.readLine()) != null) {
					if (line.contains(",")) {
						s = line.split(",");

						// WA�кͺ����нڵ�
						id = Long.parseLong(s[0]);
						latitude = Double.parseDouble(s[1]);
						longtitude = Double.parseDouble(s[2]);

						Node node = new Node(mappingLongToInt.get(id),
								longtitude, latitude);
						nodeMap.put(mappingLongToInt.get(id), node);
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// long t2 = System.currentTimeMillis();
			// System.out.println("Time for reading node: "+(t2-t1)+" ms");

		}

		return nodeMap;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static void convertNodeIdFromLongToInt() {
		mappingLongToInt = new HashMap<Long, Integer>();
		int surrogateNodeID = 0;

		try {
			System.out.println("Convert Node IDs from long to integer");
			File file = new File(NODEPATH);
			InputStreamReader in = new InputStreamReader(new FileInputStream(
					file));
			BufferedReader br = new BufferedReader(in);

			String line = null;
			String[] s;
			double longtitude, latitude;
			long id;
			while ((line = br.readLine()) != null) {
				if (line.contains(",")) {
					s = line.split(",");

					
					id = Long.parseLong(s[0]);
					latitude = Double.parseDouble(s[1]);
					longtitude = Double.parseDouble(s[2]);
//					while (mappingLongToInt.containsKey(id)) {
//						surrogateNodeID++;
//					}
					
					mappingLongToInt.put(id, surrogateNodeID);
					surrogateNodeID++;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// long t2 = System.currentTimeMillis();
		// System.out.println("Time for reading node: "+(t2-t1)+" ms");

	}

	// Map Matching between point on map with node ID.
	public static int getNodeID(double lng, double lat) {
		if (mappingLongToInt == null) {
			convertNodeIdFromLongToInt();
		}
		int nodeID = 0;
		double mindistnace = Double.MAX_VALUE;
		double distance = 0;
		double difflat = 0;
		double difflng = 0;
		// long t1 = System.currentTimeMillis();
		if (nodeMap == null) {
			try {
				System.out.println("reading node");
				nodeMap = new HashMap<Integer, Node>();
				File file = new File(NODEPATH);
				InputStreamReader in = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader br = new BufferedReader(in);

				String line = null;
				String[] s;
				double longtitude, latitude;
				long id;
				while ((line = br.readLine()) != null) {
					if (line.contains(" ")) {
						s = line.split(" ");

						
						id = Long.parseLong(s[0]);
						latitude = Double.parseDouble(s[1]);
						longtitude = Double.parseDouble(s[2]);
						difflat = lat - latitude;
						difflng = longtitude - lng;
						distance = Math.sqrt((difflng * difflng)
								+ (difflat * difflat));
						if (distance <= mindistnace) {
							mindistnace = distance;
							nodeID = mappingLongToInt.get(id);
						}
						Node node = new Node(mappingLongToInt.get(id),
								longtitude, latitude);
						nodeMap.put(node.getId(), node);
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// long t2 = System.currentTimeMillis();
			// System.out.println("Time for reading node: "+(t2-t1)+" ms");

		} else {
			Iterator it = nodeMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, Node> pair = (Map.Entry<Integer, Node>) it
						.next();

				difflat = lat - pair.getValue().getLatitude();
				difflng = pair.getValue().getLongtitude() - lng;
				distance = Math.sqrt((difflng * difflng) + (difflat * difflat));
				if (distance <= mindistnace) {
					mindistnace = distance;
					nodeID = pair.getKey();
				}
			}
		}

		return nodeID;
	}

	/**
	 * 
	 * @return
	 */
	public static Map<Integer, Node> getNodeMap(String nodePath) {
		long t1 = System.currentTimeMillis();
		if (nodeMap == null) {
			try {
				System.out.println("reading node file");
				nodeMap = new HashMap<Integer, Node>();
				File file = new File(nodePath);
				InputStreamReader in = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader br = new BufferedReader(in);

				String line = null;
				String[] s;
				double longtitude, latitude;
				int id;
				while ((line = br.readLine()) != null) {
					if (line.contains(" ")) {
						s = line.split(" ");

						// WA�кͺ����нڵ�
						id = Integer.parseInt(s[0]);
						latitude = Double.parseDouble(s[1]);
						longtitude = Double.parseDouble(s[2]);

						Node node = new Node(id,
								longtitude, latitude);
						nodeMap.put(id, node);
					}
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			long t2 = System.currentTimeMillis();
			System.out.println("Time for reading node: " + (t2 - t1) + " ms");

		}

		return nodeMap;
	}

	/**
	 * 
	 * @return
	 */
	public static Map<Integer, Edge> getEdgeMap() {
		if (mappingLongToInt == null) {
			convertNodeIdFromLongToInt();
			nodeMap = getNodeMap();
		}
		if (edgeMap == null) {
			try {
				System.out.println("reading edge");
				edgeMap = new HashMap<Integer, Edge>();

				File file = new File(EDGEPATH);
				InputStreamReader in = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader br = new BufferedReader(in);

				String line = null;
				String[] s;
				int iD = 0;
				long start, end;
				double weight;
				Edge edge;
				while ((line = br.readLine()) != null) {
					if (line.contains(",")) {
						s = line.split(",");
						start = Long.parseLong(s[1]);
						end = Long.parseLong(s[2]);
						Node sNode = nodeMap.get(mappingLongToInt.get(start));
						Node eNode = nodeMap.get(mappingLongToInt.get(end));
						weight = sNode.getDistance(eNode);
						

						edge = new Edge(iD, sNode.getId(), eNode.getId(),
								weight);
						edgeMap.put(iD, edge);
						sNode.addAdjecentList(edge);
						eNode.addAdjecentList(edge);
						nodeMap.put(mappingLongToInt.get(start),sNode);
						nodeMap.put(mappingLongToInt.get(end),eNode);
						iD++;

					}
				}

				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return edgeMap;
	}

	/**
	 * 
	 * @return
	 */
	public static Map<Integer, Edge> getEdgeMap(String edgePath) {
		long t1 = System.currentTimeMillis();
		if (edgeMap == null) {
			try {
				edgeMap = new HashMap<Integer, Edge>();

				File file = new File(edgePath);
				InputStreamReader in = new InputStreamReader(
						new FileInputStream(file));
				BufferedReader br = new BufferedReader(in);

				String line = null;
				String[] s;
				int iD, start, end;
				double weight;
				Edge edge;
				while ((line = br.readLine()) != null) {
					if (line.contains(",")) {
						s = line.split(",");

						iD = Integer.parseInt(s[0]);
						start = Integer.parseInt(s[1]);
						end = Integer.parseInt(s[2]);
						Node sNode = nodeMap.get(start);
						Node eNode = nodeMap.get(end);
						weight = sNode.getDistance(eNode);

						edge = new Edge(iD, start, end,
								weight);
						edgeMap.put(iD, edge);

					}
				}
				long t2 = System.currentTimeMillis();
				System.out.println("Time for reading edge: " + (t2 - t1)
						+ " ms");
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return edgeMap;
	}

	/**
	 * 
	 * @return
	 */
	public static EdgeWeightedDigraph getEdgeWeigthDigraph() {
		if (edgeWeigthDigraph == null) {
			In in = new In(DIJKSTRAPATH);
			edgeWeigthDigraph = new EdgeWeightedDigraph(in, true);
			System.out.println("generate EdgeWeigthDigraph...");

		}
		return edgeWeigthDigraph;
	}

	public static EdgeWeightedDigraph getEdgeWeigthDigraph(String path) {
		if (edgeWeigthDigraph == null) {
			long t1 = System.currentTimeMillis();
			In in = new In(path);
			edgeWeigthDigraph = new EdgeWeightedDigraph(in, true);
			// edgeWeigthDigraph = new EdgeWeightedDigraph(in);

			long t2 = System.currentTimeMillis();
			System.out.println("Time for generating EdgeWeigthDigraph: "
					+ (t2 - t1) + " ms");

		}
		return edgeWeigthDigraph;
	}

//	public static void generateWeightedGraph() throws IOException {
//		nodeMap = getNodeMap();
//		edgeMap = getEdgeMap();
//		FileWriter fwriter = new FileWriter(new File(DIJKSTRAPATH));
//		String line = "";
//		// add number of nodes, and number of edges, then add edges. 
//		fwriter.write(nodeMap.size()+"\n");
//		fwriter.write(edgeMap.size()+"\n");
//		for (Map.Entry<Integer, Edge> entry : edgeMap.entrySet()) {
//			Edge edge = entry.getValue();
//			line = edge.getID() + " " + " " + edge.getStart() + " "
//					+ edge.getEnd() + " " + edge.getWeight() + "\n";
//			fwriter.write(line);
//		}
//		fwriter.flush();
//		fwriter.close();
//	}

	private static int calcualteNumEdges(){
		numberEdges = 0;
		for (int i = 1; i < nodeMap.size(); i++) {
			Node n = nodeMap.get(i);
			numberEdges += n.getAdjecentList().size();
		}
		return numberEdges;
	}
	
	public static void generateWeightedGraph() throws IOException {
		nodeMap = getNodeMap();
		edgeMap = getEdgeMap();
		calcualteNumEdges();
		FileWriter fwriter = new FileWriter(new File(DIJKSTRAPATH));
		String line = "";
		// add number of nodes, and number of edges, then add edges. 
		fwriter.write(nodeMap.size()+1+"\n");
		fwriter.write(numberEdges+"\n");
		for (int i = 1; i < nodeMap.size(); i++) {
			Node n = nodeMap.get(i);
			for (Edge edge : n.getAdjecentList()) {
				line = edge.getID() + " " + edge.getStart() + " "
						+ edge.getEnd() + " " + edge.getWeight() + "\n";
				fwriter.write(line);
			}
			
		}
		
		fwriter.flush();
		fwriter.close();
		//create new files based on the new numbers mapped. 
		createNewEdgeFile();
		createNewNodeFile();
	}
	
	public static void createNewNodeFile() throws IOException{
		FileWriter fwriter = new FileWriter(new File(newNODEPATH));
		String line = "";
		for(int i=0; i<nodeMap.size();i++){
			Node node = nodeMap.get(i);
			line = node.toString()+"\n";
			fwriter.write(line);
		}
		fwriter.flush();
		fwriter.close();
	}
	
	
	public static void createNewEdgeFile() throws IOException{
		FileWriter fwriter = new FileWriter(new File(newEDGEPATH));
		String line = "";
		for(int i=0; i<edgeMap.size();i++){
			Edge edge = edgeMap.get(i);
			line = edge.toString()+"\n";
			fwriter.write(line);
		}
		fwriter.flush();
		fwriter.close();
	}
	
    
	
	public static void setDIJKSTRAPATH(String path) {
		DIJKSTRAPATH = path;
	}

	public static void setNODEPATH(String path) {
		NODEPATH = path;
	}

	public static void setEDGEPATH(String eDGEPATH) {
		EDGEPATH = eDGEPATH;
	}

	public static void main(String[] args) throws IOException {

//		 if(args.length < 3){
//		 System.out.println("please enter path to node.txt edge.txt and outputgraph.txt");
//		 }
//		 String inputnode = args[0];
//		 String inputEdge = args[1];
//		 String outputGraph = args[2];
		ReadMap readmap = new ReadMap();
//		 readmap.setNODEPATH(inputnode);
//		 readmap.setEDGEPATH(inputEdge);
//		 readmap.setDIJKSTRAPATH(outputGraph);
		readmap.setNODEPATH("/export/scratch/mntgData/nyc_networkGraph/LargeNYC/node.txt");
		readmap.setEDGEPATH("/export/scratch/mntgData/nyc_networkGraph/LargeNYC/edge.txt");
		readmap.setDIJKSTRAPATH("/export/scratch/mntgData/nyc_networkGraph/LargeNYC/newgraph.txt");
		readmap.setNewNODEPATH("/export/scratch/mntgData/nyc_networkGraph/LargeNYC/newnode.txt");
		readmap.setNewEDGEPATH("/export/scratch/mntgData/nyc_networkGraph/LargeNYC/newedge.txt");
		readmap.generateWeightedGraph();
		System.out.println("done");

	}

	public static String getNewNODEPATH() {
		return newNODEPATH;
	}

	public static void setNewNODEPATH(String newNODEPATH) {
		ReadMap.newNODEPATH = newNODEPATH;
	}

	public static String getNewEDGEPATH() {
		return newEDGEPATH;
	}

	public static void setNewEDGEPATH(String newEDGEPATH) {
		ReadMap.newEDGEPATH = newEDGEPATH;
	}

}
