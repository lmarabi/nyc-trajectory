package Dijkstra;

import java.util.Set;

import util.EdgeWeightedDigraph;
import util.ReadMap;

/**
 * 
 * @author Louai Alarabi
 *
 */

public class DijkstraDistanceCalculator {
	public static final double SPEED = 1.0;
	public static final double PRICE_RATE = 1.0;
	
	

	public static DijkstraSP get_DijkstraSP(int centerNodeID){
        EdgeWeightedDigraph G = ReadMap.getEdgeWeigthDigraph();
        DijkstraSP resultSP = new DijkstraSP(G, centerNodeID);       
        
        return resultSP;
		
	}
	
	public static DijkstraWithTargetSP get_Dijkstra_Distance(int centerNodeID, Set<Integer> targetSet){
        EdgeWeightedDigraph G = ReadMap.getEdgeWeigthDigraph();
   		DijkstraWithTargetSP resultSP = new DijkstraWithTargetSP(G, centerNodeID, targetSet);

   		return resultSP;
	}
	
	public static DijkstraWithTargetSP get_Dijkstra_Distance(int centerNodeID, 
			EdgeWeightedDigraph G, Set<Integer> targetSet){
   		DijkstraWithTargetSP resultSP = new DijkstraWithTargetSP(G, centerNodeID, targetSet);

   		return resultSP;
	}
	
//	public static DijkstraSP get_Dijkstra_Distance(int centerNodeID, BuildIndex index){
//		EdgeWeightedDigraph G = new EdgeWeightedDigraph(index.getNewNodeMap(), index.getNewEdgeMap());
//		DijkstraSP resultSP = new DijkstraSP(G, centerNodeID);
//
//   		return resultSP;
//	}


	/**
	 * 
	 * @param startNodeID
	 * @param endNodeID
	 * @param G
	 * @return
	 */
	public static double getUni_Dijkstra_Distance(int startNodeID, int endNodeID, EdgeWeightedDigraph G){
        DijkstraSP resultSP = new DijkstraSP(G, startNodeID);        //****Dijkstra****

        return resultSP.distTo(endNodeID);
	}
	
	/**
	 * 
	 * @param startNodeID
	 * @param endNodeID
	 * @return
	 */
	public static double getUni_Dijkstra_Distance(int startNodeID, int endNodeID){
		EdgeWeightedDigraph G = ReadMap.getEdgeWeigthDigraph();
        DijkstraSP pickupSP = new DijkstraSP(G, startNodeID);        //****Dijkstra****

        return pickupSP.distTo(endNodeID);
	}
	
	public static void main(String[] args){
 
		String path = "WebContent/data/Data_LA/LA";
		ReadMap.getEdgeWeigthDigraph("WebContent/data/Data_LA/LADijkstra.txt");
		System.out.println(getUni_Dijkstra_Distance(248, 643));
		System.out.println(getUni_Dijkstra_Distance(248, 448));
		
	}
}
