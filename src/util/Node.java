package util;

import java.io.Serializable;
import java.util.ArrayList;

import util.EuclideanDistanceCalculator;

/**
 * @author Louai Alarabi
 *
 */
public class Node  implements Serializable{
	private int id = 0;			//***
	private double latitude = Double.MAX_VALUE;	//γ**
	private double longtitude = Double.MAX_VALUE;	//X****
	private ArrayList<Edge> adjecentList;
	
	public Node(int id, double longtitude, double latitude) {
		super();
		this.id = id;
		this.longtitude = longtitude;
		this.latitude = latitude;
		this.adjecentList = new ArrayList<Edge>();
	}
	
	public Node(Point p){
		this.latitude = p.getLatitude();
		this.longtitude = p.getLongtitude();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongitude(double longitude) {
		this.longtitude = longitude;
	}
	
	public double getDistance(Node n){
		return EuclideanDistanceCalculator.getGPSDistance(n, this);
	}

	public ArrayList<Edge> getAdjecentList() {
		return adjecentList;
	}
	
	public void addAdjecentList(Edge edge){
		this.adjecentList.add(edge);
	}

	public void setAdjecentList(ArrayList<Edge> adjecentList) {
		this.adjecentList = adjecentList;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id+" "+this.latitude+" "+this.longtitude;
	}
}
