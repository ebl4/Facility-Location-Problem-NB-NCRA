package paa.tpfinal.coopernb;

import paa.tpfinal.Arquivo;

public class Generator {
	public static void main(String[] args) {
		Arquivo arq = new Arquivo("entradas", "inCNB4");
		int m = 5, limite = 50;
		int numP = 50000;
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
		sb.append(limite);
		String resp = sb.substring(0, sb.length());
		arq.print(resp);
		arq.close();
	}
}