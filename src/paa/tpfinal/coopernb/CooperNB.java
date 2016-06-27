package paa.tpfinal.coopernb;

import java.util.ArrayList;
import java.util.List;

import paa.tpfinal.Arquivo;
import paa.tpfinal.Cluster;
import paa.tpfinal.NCRA;
import paa.tpfinal.Point;
import paa.tpfinal.nb.NB;

public class CooperNB {
	NCRA ncra;
	NB nb;

	public CooperNB() {
		ncra = new NCRA();
		nb = new NB();
	}

	public void initNCRA(List<Cluster> clusters, int limite){
		ncra.setClusters(clusters);
		ncra.setLimite(limite);;
	}

	public Cluster calcXPoint(Cluster clusterCurr, List<Double> w){
		clusterCurr.setxPoint(nb.nbMethod(clusterCurr.getCustomers(), 
				clusterCurr.getxPoint(), w));
		return clusterCurr;
	}

	public void cooperNBMethod(List<Cluster> clusters, int iter){
		List<List<Double>> w = new ArrayList<List<Double>>();   
		int contReassign = 0, cont = 0;		
		boolean reassign = false;

		//calculando pontos dos clusters via NB method
		for (int i = 0; i < clusters.size(); i++) {
			List<Double> wCurr = new ArrayList<Double>();
			Cluster clusterCurr = clusters.get(i);
			for (int j = 0; j < clusterCurr.getCustomers().size(); j++) {
				wCurr.add(ncra.getLimite()*Math.random());
			}
			w.add(wCurr);
			calcXPoint(clusterCurr, w.get(i));
		}

		//reatribuicoes			
		while(cont < iter){
			for (int l = 0; l < clusters.size(); l++){
				Cluster clusterCurr = clusters.get(l);
				List<Point> customersCluster = clusterCurr.getCustomers();
				for (int j = 0; j < customersCluster.size(); j++) {
					Point aj = customersCluster.get(j);
					double d = ncra.distance(clusterCurr.getxPoint(), aj);
					for (int k = 0; k < clusters.size(); k++) {
						Cluster kCluster = clusters.get(k);
						double d2 = ncra.distance(aj, kCluster.getxPoint());
						//System.out.println("Cluster " + i +" comparando com Cluster "+k);
						//System.out.println("Distancia d "+d);
						//System.out.println("Distancia d2 "+d2);
						if(d2 < d){
							clusterCurr.getCustomers().remove(aj);
							kCluster.getCustomers().add(aj);

							clusters.set(l, calcXPoint(clusterCurr, w.get(l)));
							clusters.set(k, calcXPoint(kCluster, w.get(k)));
							reassign = true;
							contReassign++;
						}
					}
				}
			}
		}
		cont++;
	}


	public static void main(String[] args) {
		CooperNB cNB = new CooperNB();
		Arquivo arq = new Arquivo("in0", "out0");
		List<Point> points = new ArrayList<Point>();
		List<Cluster> clusters = new ArrayList<Cluster>();  
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
			clusters.add(c);
		}
		cNB.initNCRA(clusters, limite);				
		cNB.cooperNBMethod(clusters, 10);
	}
}
