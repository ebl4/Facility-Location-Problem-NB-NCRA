package paa.tpfinal;
import java.util.ArrayList;
import java.util.List;

public class Cluster {
	List<Point> customers;
	Point centroid;
	
	public Cluster(){
		customers = new ArrayList<Point>();
		this.centroid = new Point();
	}
	
	public void addPoint(Point a){
		customers.add(a);
	}
	
	public Point removePoint(Point a){
		int index = customers.indexOf(a);
		if(customers.contains(a))
			return customers.remove(index);
		else
			return null;
	}
	
}
