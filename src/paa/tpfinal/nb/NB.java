package paa.tpfinal.nb;

import java.util.ArrayList;
import java.util.List;

import paa.tpfinal.Point;

public class NB {

	Bracket brt; 
	Point x;
	double epsilon;
	double totalTCost;
	Point totalCostGradient;

	public NB() {
		// TODO Auto-generated constructor stub
	}

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

	public double linearCombination(double upperbound, double lowerbound){
		double alpha, result;
		alpha = Math.random();
		result =  alpha*upperbound + (1-alpha)*lowerbound;
		return result;
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

	public void setTotalCost(List<Point> customers, Point x, List<Double> w){
		totalTCost = 0;
		Point costGradient = new Point();
		for (int i = 0; i < customers.size(); i++) {
			Point vector = makeVector(customers.get(i), x);
			double normaVj = norma(vector);
			totalTCost += w.get(i)*normaVj;
			costGradient = sumVector(costGradient, normalizedVector(vector, normaVj));
		}

		//Vetor gradiente de custo total com transporte
		totalCostGradient = costGradient;
	}

	public void nbMethod(List<Point> customers, Point x, List<Double> w){
		Point x1;
		int count = 0;
		if(count == 0){
			setTotalCost(customers, x, w);
			count++;
		}
		double totalCostX = totalTCost;
		double totalCostX1 = 0;
		if(brt.upperbound - brt.lowerbound < this.epsilon){

		}
		else{
			double middlebound = linearCombination(brt.upperbound, brt.lowerbound);
			double partial = (totalCostX-middlebound)/Math.pow(norma(totalCostGradient),2);
			x1 = multVector(x, partial);

			setTotalCost(customers, x1, w);
			totalCostX1 = totalTCost;

			if(totalCostX1 < totalCostX){
				brt.upperbound = totalCostX1;
			}
			else{
				brt.lowerbound = middlebound;
				x = x1;
			}
		}
	}

	public static void main(String[] args) {

	}
}
