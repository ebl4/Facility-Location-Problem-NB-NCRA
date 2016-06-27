package paa.tpfinal;
import java.util.ArrayList;
import java.util.List;

public class Cluster {
	List<Point> customers;	
	Point xPoint;
	
	public Cluster(int limite){
		customers = new ArrayList<Point>();
		this.xPoint = new Point(limite*Math.random(), limite*Math.random());
	}
	
	public Point getxPoint() {
		return xPoint;
	}

	public void setxPoint(Point xPoint) {
		this.xPoint = xPoint;
	}

	public List<Point> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Point> customers) {
		this.customers = customers;
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
