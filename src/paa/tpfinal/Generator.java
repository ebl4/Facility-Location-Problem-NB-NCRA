package paa.tpfinal;
public class Generator {
	public static void main(String[] args) {
		Arquivo arq = new Arquivo("entradas", "in5");
		int m = 4, limite = 10;
		int numP = 10000;
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
