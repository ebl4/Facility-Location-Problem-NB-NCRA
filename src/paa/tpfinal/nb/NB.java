package paa.tpfinal.nb;

import java.util.ArrayList;
import java.util.List;

import paa.tpfinal.Arquivo;
import paa.tpfinal.Cluster;
import paa.tpfinal.NCRA;
import paa.tpfinal.Point;

public class NB {

	VectorUtils vu;
	Bracket brt; 
	double epsilon;
	double totalTCost;


	public NB() {
		vu = new VectorUtils();
		this.epsilon = 0.001;
		this.brt = new Bracket();
		this.totalCostGradient = new Point();
		this.totalTCost = 0;
		sb = new StringBuffer();
	}

	public Bracket getBrt() {
		return brt;
	}

	public void setBrt(Bracket brt) {
		this.brt = brt;
	}

	public double getTotalTCost() {
		return totalTCost;
	}

	public void setTotalTCost(double totalTCost) {
		this.totalTCost = totalTCost;
	}

	public Point getTotalCostGradient() {
		return totalCostGradient;
	}

	public void setTotalCostGradient(Point totalCostGradient) {
		this.totalCostGradient = totalCostGradient;
	}

	Point totalCostGradient;
	StringBuffer sb;

	public int minWeitght(List<Double> w){
		double min = Double.MAX_VALUE;
		int indexMin = -1;
		for (int i = 0; i < w.size(); i++) {
			if(w.get(i) < min){ 
				min = w.get(i);
				indexMin = i;
			}
		}
		return indexMin;
	}

	public void initBracket(List<Point> customers, Point x, List<Double> w){
		double weigthRemoved;
		int indexMinW2;
		this.setTotalCost(customers, x, w);
		brt.upperbound = totalTCost;

		//primeiro menor peso (indice)
		int indexMinW = this.minWeitght(w);
		Point ai = customers.get(indexMinW);
		if(w.size() > 1){
			weigthRemoved = w.remove(indexMinW);
			//segundo menor peso (indice)
			indexMinW2 = this.minWeitght(w);
			//reinserindo elemento removido
			w.add(indexMinW, weigthRemoved);
		}
		else{
			indexMinW2 = indexMinW;
		}
		Point aj = customers.get(indexMinW2);
		brt.lowerbound = vu.norma(vu.makeVector(ai, aj));
	}

	public double linearCombination(double upperbound, double lowerbound){
		double alpha, result;
		alpha = Math.random();
		result =  alpha*upperbound + (1-alpha)*lowerbound;
		return result;
	}

	public void setTotalCost(List<Point> customers, Point x, List<Double> w){
		totalTCost = 0;
		totalCostGradient = new Point();
		Point costGradient = new Point();
		for (int i = 0; i < customers.size(); i++) {
			if(vu.isEqual(x, customers.get(i))){
				System.out.println("Equal!");
			}
			Point vector = vu.makeVector(customers.get(i), x);
			double normaVj = vu.norma(vector);
			totalTCost += w.get(i)*normaVj;
			costGradient = vu.sumVector(costGradient, 
					vu.multVector(vu.normalizedVector(vector, normaVj), w.get(i)));			
		}
		//Vetor gradiente de custo total com transporte
		totalCostGradient = costGradient;
	}

	public Point nbMethod(List<Point> customers, Point x, List<Double> w, int k){
		sb = new StringBuffer();
		Point x1;
		if(k <= 0){
			this.initBracket(customers, x, w);
		}
		int count = 0;
		while((brt.upperbound - brt.lowerbound) >= this.epsilon){			
			count++;
			//gravacao dos resultados
			sb.append("Iteracao ");
			sb.append(count);
			sb.append(" limite inferior = ");
			sb.append(brt.lowerbound);
			sb.append(" limite superior = ");
			sb.append(brt.upperbound);
			sb.append("\n");
			sb.append("Valor do ponto x: ");
			sb.append(x.getX());
			sb.append(" ");
			sb.append(x.getY());
			sb.append("\n");

			double totalCostX = totalTCost;
			double totalCostX1 = 0;
			double middlebound = linearCombination(brt.upperbound, brt.lowerbound);
			double partial = (totalCostX-middlebound)/Math.pow(vu.norma(totalCostGradient),2);			
			x1 = vu.diffVector(x, vu.multVector(totalCostGradient, partial));
			setTotalCost(customers, x1, w);
			totalCostX1 = totalTCost;
			if(totalCostX1 < totalCostX){ //C(x_k+1) < C(x_k) 
				brt.upperbound = totalCostX1;
			}
			else{
				brt.lowerbound = middlebound;
				//x = x1;
			}
		}
		return x;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		NB algo = new NB();
		Arquivo arq = new Arquivo("inNB4", "outNB4");
		double x, y;
		List<Double> weights = new ArrayList<Double>();
		List<Point> pointsCustomers = new ArrayList<Point>();
		Point xPoint;
		int m = arq.readInt();
		int numP = arq.readInt();
		for (int i = 0; i < numP; i++) {
			x = arq.readDouble();
			y = arq.readDouble();
			pointsCustomers.add(new Point(x,y));
		}
		for (int i = 0; i < numP; i++) {
			weights.add(arq.readDouble());
		}
		xPoint = new Point(arq.readDouble(),arq.readDouble());
		algo.nbMethod(pointsCustomers, xPoint, weights, 0);
		long end = System.currentTimeMillis();
		//		System.out.println(xPoint.getX());
		//		System.out.println(xPoint.getY());
		arq.print(algo.sb.substring(0, algo.sb.length()));
		arq.print("Total cost: ");
		arq.println(algo.totalTCost);
		arq.print("Tempo: ");
		arq.println(end-start);
		arq.close();
	}
}
