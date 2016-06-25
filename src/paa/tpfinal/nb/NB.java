package paa.tpfinal.nb;

import java.util.ArrayList;
import java.util.List;

import paa.tpfinal.Point;

public class NB {

	VectorUtils vu;
	Bracket brt; 
	Point x;
	double epsilon;
	double totalTCost;
	Point totalCostGradient;

	public NB() {
		vu = new VectorUtils();
	}

	public double linearCombination(double upperbound, double lowerbound){
		double alpha, result;
		alpha = Math.random();
		result =  alpha*upperbound + (1-alpha)*lowerbound;
		return result;
	}

	public void setTotalCost(List<Point> customers, Point x, List<Double> w){
		totalTCost = 0;
		Point costGradient = new Point();
		for (int i = 0; i < customers.size(); i++) {
			Point vector = vu.makeVector(customers.get(i), x);
			double normaVj = vu.norma(vector);
			totalTCost += w.get(i)*normaVj;
			costGradient = vu.sumVector(costGradient, vu.normalizedVector(vector, normaVj));
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
			double partial = (totalCostX-middlebound)/Math.pow(vu.norma(totalCostGradient),2);
			x1 = vu.multVector(x, partial);

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
