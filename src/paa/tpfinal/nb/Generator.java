package paa.tpfinal.nb;

import paa.tpfinal.Arquivo;

public class Generator {
	public static void main(String[] args) {
		Arquivo arq = new Arquivo("entradas", "inNB4");
		int m = 4, limite = 20;
		int numP = 100000;
		StringBuffer sb = new StringBuffer();
		sb.append(m);
		sb.append(" ");
		sb.append(numP);
		sb.append("\n");
		for (int i = 1; i <= numP; i++) {
			double x = limite*Math.random();
			double y = limite*Math.random();
			sb.append(x); 
			sb.append(" "); 
			sb.append(y);
			sb.append(" ");
			if(i%((numP/m)) == 0){
				sb.append("\n");
			}
		}
		for (int i = 1; i <= numP; i++) {
			double weight = limite*Math.random();
			sb.append(weight); 
			sb.append(" "); 
		}
		sb.append("\n");
		double x = limite*Math.random();
		double y = limite*Math.random();
		sb.append(x); 
		sb.append(" "); 
		sb.append(y);
		sb.append(" ");
		String resp = sb.substring(0, sb.length()-1);
		arq.print(resp);
		arq.close();
	}
}
