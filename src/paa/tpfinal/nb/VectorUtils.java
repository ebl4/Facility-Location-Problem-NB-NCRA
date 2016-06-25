package paa.tpfinal.nb;

import paa.tpfinal.Point;

public class VectorUtils {
	
	public Point makeVector(Point x, Point a){
		Point vector = new Point();
		vector.setX(x.getX() - a.getX());
		vector.setY(x.getY() - a.getY());
		return vector;
	}

	public Point sumVector(Point x, Point a){
		Point vector = new Point();
		vector.setX(x.getX() + a.getX());
		vector.setY(x.getY() + a.getY());
		return vector;
	}
	
	public double norma(Point vector){
		return Math.sqrt(Math.pow(vector.getX(), 2) + Math.pow(vector.getY(), 2));
	}

	public Point multVector(Point vector, double a){
		vector.setX(vector.getX()*a);
		vector.setY(vector.getY()*a);
		return vector;
	}

	public Point normalizedVector(Point vector, double norma){
		vector.setX(vector.getX()/norma);
		vector.setY(vector.getY()/norma);
		return vector;
	}

}
