public class Generator {
	public static void main(String[] args) {
		Arquivo arq = new Arquivo("entradas", "in0");
		int m = 4;
		int numP = 10;
		StringBuffer sb = new StringBuffer();
		sb.append(m);
		sb.append(" ");
		sb.append(numP);
		sb.append("\n");
		for (int i = 1; i <= numP; i++) {
			double x = 10*Math.random();
			double y = 10*Math.random();
			sb.append(x); 
			sb.append(" "); 
			sb.append(y);
			sb.append(" ");
			if(i%((numP/m)) == 0){
				sb.append("\n");
			}
		}
		String resp = sb.substring(0, sb.length()-1);
		arq.print(resp);
		arq.close();
	}
}
