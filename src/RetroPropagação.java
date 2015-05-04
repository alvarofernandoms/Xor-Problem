import funcaoTranmissao.Sigmoide;


public class RetroPropagação {
	
	static double[][] w1CamadaDeEntrada = new double [2][3]; 
	static double[] w2CamadaDeSaida = new double [3];
	static double[][] entrada = new double[4][2];
	static double[] saida = new double[4];
	static double[] saidaCalculada = new double[4];
	static double[] x = new double[2]; 
	static double x0Bias=1.0;
	static double H0Bias=1.0;
	static double[] hidden = new double[2];
	static double erroSaida=0;
	static double[] erroIntermediario= new double[3];
	
	static final int numMaximoEpoca=15;
	
	static private double somatorio(double n, double[] vetor) {
		double soma = 0;
		for (int linha = 0; linha < vetor.length; linha++) {
			soma= soma + (n*vetor[linha]);
		}		
		return soma;
	}
	
	private static void iniciandoPesosDaEntrada(){
		for (int coluna = 0; coluna < w1CamadaDeEntrada.length; coluna++) {
			for (int linha = 0; linha < w1CamadaDeEntrada[coluna].length; linha++) {
				w1CamadaDeEntrada[coluna][linha] = 1.0;
			}
		}
	}
	
	private static void iniciandoPesosDaSaida() {
		for (int linha = 0; linha < w2CamadaDeSaida.length; linha++) {
			w2CamadaDeSaida[linha]=1;
		}
	}
	
	private static void possiveisCombinacoes() {
		entrada[0][0] = 0.0;
		entrada[0][1] = 0.0;
		saida[0] = 0.0;
		
		entrada[1][0] = 0.0;
		entrada[1][1] = 1.0;
		saida[1] = 1.0;
		
		entrada[2][0] = 1.0;
		entrada[2][1] = 0.0;
		saida[2] = 1.0;
		
		entrada[3][0] = 1.0;
		entrada[3][1] = 1.0;
		saida[3] = 0.0;
	}
	
	private static void sinapseHidden(double[] xEntrada){
		for (int linha = 0; linha < hidden.length; linha++) {
			hidden[linha]= Sigmoide.funcaoSigmoide(w1CamadaDeEntrada[linha], xEntrada);
		}
	}
	
	private static void execucao(double[] entrada, int index){
		sinapseHidden(entrada);
		saidaCalculada[index]=Sigmoide.funcaoSigmoide(w2CamadaDeSaida,hidden);
		erroSaida = saida[index]*(1-saida[index])*(saidaCalculada[index]-saida[index]);
		
		for (int linha = 0; linha < hidden.length; linha++) {
			erroIntermediario[linha+1] = hidden[linha]*(1-hidden[linha])*(somatorio(erroSaida, w2CamadaDeSaida));
		}
	}
	
	private static void treinarRede(){
		iniciandoPesosDaEntrada();
		iniciandoPesosDaSaida();
		
		for(int iteracoes=0;iteracoes<numMaximoEpoca;iteracoes++){
			execucao(entrada[0],0);
//			sinapse(entrada[1]);
//			sinapse(entrada[2]);
//			sinapse(entrada[3]);
		}
		
		
	}
	
	public static void main(String[] args) {
		possiveisCombinacoes();
	}

}
