package util;

import java.io.Serializable;

/**
 * 
 * @author Louai Alarabi
 *
 */

public class Point implements Serializable{
	private double longtitude;	//x
	private double latitude;	//γ
	private Edge matchEdge = null;

	public Point(double longtitude, double latitude) {
//		if(latitude < 0 || latitude > 90){
//			System.out.println("latitude is invalid !");
//			System.exit(0);
//		}
		this.longtitude = longtitude;
		this.latitude = latitude;
	}
	
//	public Point(){
//		this.latitude = Math.random() * (MAX_LATITUDE - MIN_LATITUDE) + MIN_LATITUDE;
//		this.longtitude = Math.random() * (MAX_LONGTITUDE - MIN_LONGTITUDE) + MIN_LONGTITUDE;
//	}
	
	public Point(Node node){
		this.latitude = node.getLatitude();
		if(latitude < 0 || latitude > 90){
			System.out.println("latitude is invalid !");
			System.exit(0);
		}
		this.longtitude = node.getLongtitude();
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		if(latitude < 0 || latitude > 90){
			System.out.println("latitude is invalid !");
			System.exit(0);
		}
		this.latitude = latitude;
	}


	public double getLongtitude() {
		return longtitude;
	}


	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	
	public Edge getMatchEdge() {
		return matchEdge;
	}

	public void setMatchEdge(Edge matchEdge) {
		this.matchEdge = matchEdge;
	}

//	public static void main(String[] args){
//		for(int i = 0; i < 10; i++){
//			System.out.print(Math.random() * (MAX_LATITUDE - MIN_LATITUDE) + MIN_LATITUDE + "\t");
//			System.out.println(Math.random() * (MAX_LONGTITUDE - MIN_LONGTITUDE) + MIN_LONGTITUDE);
//		}
//	}
	
}
