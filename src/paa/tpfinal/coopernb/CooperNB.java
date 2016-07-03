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
	int reassigns;

	public CooperNB() {
		ncra = new NCRA();
		nb = new NB();
		this.reassigns = 0;
	}

	public void initNCRA(List<Cluster> clusters, int limite){
		ncra.setClusters(clusters);
		ncra.setLimite(limite);;
	}

	public Cluster calcXPoint(Cluster clusterCurr, List<Double> w, int k){
		clusterCurr.setxPoint(nb.nbMethod(clusterCurr.getCustomers(), 
				clusterCurr.getxPoint(), w, k));
		return clusterCurr;
	}
	
	public List<Double> wCluster(Cluster cluster){
		List<Double> result = new ArrayList<Double>();
		for (int i = 0; i < cluster.getCustomers().size(); i++) {
			result.add(cluster.getCustomers().get(i).getW());
		}
		return result;
	}
	
	public void initCustormersWeights(Cluster cluster){
		for (int i = 0; i < cluster.getCustomers().size(); i++) {
			cluster.getCustomers().get(i).setW(ncra.getLimite()*Math.random());
		}
	}
	
	public List<Cluster> cooperNBMethod(List<Cluster> clusters, int iter){
		int contReassign = 0, cont = 0;		
		boolean reassign = true;

		//inicializando os pesos dos clientes dos clusters e
		//calculando pontos dos clusters via NB method
		for (int i = 0; i < clusters.size(); i++) {
			initCustormersWeights(clusters.get(i));
			clusters.set(i,calcXPoint(clusters.get(i), wCluster(clusters.get(i)), 0));
		}	
		
		//reatribuicoes			
		while(reassign){
			reassign = false;
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

							clusterCurr = calcXPoint(clusterCurr, wCluster(clusterCurr), cont+1);
							kCluster = calcXPoint(kCluster, wCluster(kCluster), cont+1);
							
							clusters.set(l, clusterCurr);
							clusters.set(k, kCluster);
							reassign = true;
							contReassign++;
						}
					}
				}
			}
			cont++;
		}
		reassigns = cont;
		return clusters;
	}
	
	public void showResult(List<Cluster> clusters){
		for (int i = 0; i < clusters.size(); i++) {
			System.out.println(clusters.get(i).getxPoint().getX());
			System.out.println(clusters.get(i).getxPoint().getY());
		}
	}

	public static void main(String[] args) {
		CooperNB cNB = new CooperNB();
		Arquivo arq = new Arquivo("inCNB4", "outCNB4");
		long start = System.currentTimeMillis();
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
		clusters = cNB.cooperNBMethod(clusters, 10);
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		System.out.println(cNB.reassigns);
		System.out.println(cNB.nb.getTotalTCost());
		//cNB.showResult(clusters);
	}
}
