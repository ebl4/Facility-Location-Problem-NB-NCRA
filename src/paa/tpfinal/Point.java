package paa.tpfinal;

public class Point {
	double x, y;
	double w;
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
		this.w = 0;
	}
	
	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public Point(){
		
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}
