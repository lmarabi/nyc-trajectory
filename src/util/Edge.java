package util;

import java.io.Serializable;

/**
 * 
 * @author Louai Alarabi
 *
 */
public class Edge implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7447488218650918107L;
	private int ID = -1;			
	private int start = -1;
	private int end = -1;
	private double weight = 0.0;
	
	public Edge(int iD, int start, int end, double weight) {
		super();
		ID = iD;
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.ID+" "+this.start+" "+this.end+" "+this.weight;
	}
	
	
}
