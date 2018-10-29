package util;

import java.util.Map;
import util.ReadMap;

/**
 *
 * @author Louai Alarabi
 *
 */
public class EuclideanDistanceCalculator {
	private final static double EARTH_RADIUS = 6378.137;
	
	private static double rad(double d)
	{
	  return d * Math.PI / 180.0;
	}


	/**
	 * 
	 * @param center
	 * @param target
	 * @return
	 */
	public static double getGPSDistance(Point center, Point target){
		
		double lat1 = center.getLatitude();
		double lng1 = center.getLongtitude();
		double lat2 = target.getLatitude();
		double lng2 = target.getLongtitude();
		
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +  
		Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * EARTH_RADIUS;

//		DecimalFormat df = new DecimalFormat("#.000");
//		return Double.parseDouble(df.format(s));
		
		return s;
	}
	
	/**
	 * 
	 * @param center
	 * @param target
	 * @return
	 */
	public static double getGPSDistance(double lat1, double lng1, double lat2, double lng2){
	
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +  
		Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * EARTH_RADIUS;

//		DecimalFormat df = new DecimalFormat("#.000");
//		return Double.parseDouble(df.format(s));
		
		return s;
	}
	
	public static double getGPSDistance(long NodeID1, long NodeID2){
		Map<Integer, Node> nodeMap = ReadMap.getNodeMap();
		Node center = nodeMap.get(NodeID1);
		Node target = nodeMap.get(NodeID2);
		
		return getGPSDistance(center, target);
	}
	
	public static double getGPSDistance(Node center, Point target){
		Node target1 = new Node(target);
		
		return getGPSDistance(center, target1);
	}
	
	/**
	 * 
	 * @param center
	 * @param target
	 * @return
	 */
	public static double getLineDistance(Point center, Point target){
		double lat1 = center.getLatitude();
		double lng1 = center.getLongtitude();
		double lat2 = target.getLatitude();
		double lng2 = target.getLongtitude();
		
		return Math.sqrt((lat2 - lat1) * (lat2 - lat1) + (lng2 - lng1) * (lng2 - lng1));
		
	}
	
	public static double getLineDistance(double lat1, double lng1, double lat2, double lng2){
		
		return Math.sqrt((lat2 - lat1) * (lat2 - lat1) + (lng2 - lng1) * (lng2 - lng1));
	}
	
	/**
	 * 
	 * @param center
	 * @param target
	 * @return
	 */
	public static double getGPSDistance(Node center, Node target){
		double lat1 = center.getLatitude();
		double lng1 = center.getLongtitude();
		double lat2 = target.getLatitude();
		double lng2 = target.getLongtitude();
		
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +  
		Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * EARTH_RADIUS;
		
//		DecimalFormat df = new DecimalFormat("#.000");
//		return Double.parseDouble(df.format(s));
		
		return s;
	}
	
	public static void main(String[] args){
		
		System.out.println(getGPSDistance(32.822525, -118.444075, 32.822525, -118.444075));
	}
	
}
