package paa.tpfinal.coopernb;

import java.util.ArrayList;
import java.util.List;

import paa.tpfinal.Cluster;
import paa.tpfinal.NCRA;
import paa.tpfinal.Point;
import paa.tpfinal.nb.NB;

public class CooperNB {
	NCRA ncra;
	NB nb;
	
	public CooperNB() {
		ncra = new NCRA();
	}
	
	public void initNCRA(List<Cluster> clusters, int limite){
		ncra.setClusters(clusters);
		ncra.setLimite(limite);;
	}
	
	public void cooperNBMethod(List<Cluster> clusters){
		List<Double> w = new ArrayList<Double>();
		Point x = new Point(ncra.getLimite()*Math.random(), ncra.getLimite()*Math.random());
		
		for (int i = 0; i < clusters.size(); i++) {
			Cluster clusterCurr = clusters.get(i);
			for (int j = 0; j < clusterCurr.getCustomers().size(); j++) {
				w.add(ncra.getLimite()*Math.random());
			}
			x = nb.nbMethod(clusterCurr.getCustomers(), x, w);
			
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
							//(reassign aj)
							//							System.out.println("Point +"+ aj.x+" "+aj.y+" reassigned "
							//									+ "from cluster "+ i+" to "+k);

							clusterCurr.getCustomers().remove(aj);
							kCluster.getCustomers().add(aj);
							//System.out.println("Reassign "+ cont++);

							//recalcula os novos centroids
							clusterCurr.setxPoint(this.centroid(clusters.get(i)));
							kCluster.setXPoint(this.centroid(clusters.get(k)));
							
							clusters.set(i, clusterCurr);
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
	
	public static void main(String[] args) {
		
	}
}
