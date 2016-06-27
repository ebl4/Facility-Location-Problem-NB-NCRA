package paa.tpfinal;
import java.util.ArrayList;
import java.util.List;

public class NCRA {
	List<Cluster> clusters;
	int contReassign;
	int limite;

	public NCRA(){
		clusters = new ArrayList<Cluster>();
		contReassign = 0;
	}

	public void initClustersXPoint(){
		for (int i = 0; i < clusters.size(); i++) {
			Cluster aux = clusters.get(i);
			aux.xPoint = this.centroid(aux);
			clusters.set(i, aux);
		}
	}
	
	public void nearestCenter(boolean byCentroid){
		boolean reassign = true;
		if(byCentroid){
			initClustersXPoint();
		}
		while(reassign){
			reassign = false;
			for (int i = 0; i < clusters.size(); i++) {
				Cluster iCluster = clusters.get(i);
				List<Point> customersCluster = iCluster.customers;
				for (int j = 0; j < customersCluster.size(); j++) {
					Point aj = customersCluster.get(j);
					double d = this.distance(iCluster.xPoint, aj);
					for (int k = 0; k < clusters.size(); k++) {
						Cluster kCluster = clusters.get(k);
						double d2 = this.distance(aj, kCluster.xPoint);
						//System.out.println("Cluster " + i +" comparando com Cluster "+k);
						//System.out.println("Distancia d "+d);
						//System.out.println("Distancia d2 "+d2);
						if(d2 < d){
							//(reassign aj)
							//							System.out.println("Point +"+ aj.x+" "+aj.y+" reassigned "
							//									+ "from cluster "+ i+" to "+k);

							iCluster.customers.remove(aj);
							kCluster.customers.add(aj);
							//System.out.println("Reassign "+ cont++);

							//recalcula os novos centroids
							iCluster.xPoint = this.centroid(clusters.get(i));
							kCluster.xPoint = this.centroid(clusters.get(k));
							clusters.set(i, iCluster);
							clusters.set(k, kCluster);
							reassign = true;
							contReassign++;
							//se já reatribuiu entao para a comparacao, ja que foi 
							//para outro cluster
						}
					}
				}
			}
		}
	}

	public List<Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}

	public int getContReassign() {
		return contReassign;
	}

	public void setContReassign(int contReassign) {
		this.contReassign = contReassign;
	}

	public Point centroid(Cluster cluster){
		List<Point> points = cluster.customers;
		double xc = 0, yc = 0;
		int len = points.size();
		for (int i = 0; i < points.size(); i++) {
			xc += points.get(i).x;
			yc += points.get(i).y;
		}
		xc /= len; 
		yc /= len;
		return new Point(xc, yc);
	}

	public double distance(Point a, Point b){
		return Math.abs(Math.sqrt(Math.pow(a.x - b.x, 2) +
				Math.pow(a.y - b.y, 2)));
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		NCRA algo = new NCRA();
		Arquivo arq = new Arquivo("in0", "out0");
		List<Point> points = new ArrayList<Point>();
		int m = arq.readInt(), limite;
		int numP = arq.readInt();
		for (int i = 0; i < numP; i++) {
			double x = arq.readDouble();
			double y = arq.readDouble();
			points.add(new Point(x,y));
		}
		limite = arq.readInt();
		for (int i = 0; i < m; i++) {
			Cluster c = new Cluster(limite);
			for (int j = i*numP/m; j < (i+1)*numP/m; j++) {
				c.addPoint(points.get(j));
			}
			algo.clusters.add(c);
		}

		algo.limite = limite;

		//algo.nearestCenter();
		for (int i = 0; i < algo.clusters.size(); i++) {
			arq.println(algo.clusters.get(i).xPoint.x);
			//System.out.println(algo.clusters.get(i).limite);
		}
		arq.println(algo.contReassign);
		long end = System.currentTimeMillis();
		arq.println(end-start);
		arq.close();
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

}
