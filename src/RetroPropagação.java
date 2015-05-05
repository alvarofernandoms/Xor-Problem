import javax.swing.text.TabableView;

import funcaoTranmissao.Sigmoide;


public class RetroPropagação {
	
	static double[][] w1CamadaDeEntrada = new double [2][3];
	static double[][] erroW1 = new double [2][3];
	static double[] w2CamadaDeSaida = new double [3];
	static double[] erroW2=new double[2];
	static double[][] entrada = new double[4][2];
	static double[] saida = new double[4];
	static double[] saidaCalculada = new double[4];
	static double[] x = new double[2]; 
	static double x0Bias=1.0;
	static double H0Bias=1.0;
	static double[] hidden = new double[2];
	static double erroSaida=0;
	static double[] erroIntermediario= new double[2];
	
	static final int numMaximoEpoca=15;
	static final double alphaTaxaDeAprendizagem=0.5;
	
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
		entrada[0][0] = 0.0001;
		entrada[0][1] = 0.0001;
		saida[0] = 0.0001;
		
		entrada[1][0] = 0.0001;
		entrada[1][1] = 1.0001;
		saida[1] = 1.0001;
		
		entrada[2][0] = 1.0001;
		entrada[2][1] = 0.0001;
		saida[2] = 1.0001;
		
		entrada[3][0] = 1.0001;
		entrada[3][1] = 1.0001;
		saida[3] = 0.0001;
	}
	
	private static void sinapseHidden(double[] xEntrada){
		for (int linha = 0; linha < hidden.length; linha++) {
			hidden[linha]= Sigmoide.funcaoSigmoide(w1CamadaDeEntrada[linha], xEntrada);
		}
	}
	
	private static void execucao(double[] xEntrada, int index){
		sinapseHidden(xEntrada);
		saidaCalculada[index]=Sigmoide.funcaoSigmoide(w2CamadaDeSaida,hidden);
		
		System.out.println("Entrada: "+xEntrada[0]+" "+xEntrada[1]);
		System.out.println("Saida Calculada:"+saidaCalculada[index]+"\n");
		
		erroSaida = saida[index]*(1-saida[index])*(saidaCalculada[index]-saida[index]);
		System.out.println("Erro Saída: "+erroSaida);
		
		//erroIntermediario[0]=H0Bias*(1-H0Bias)*somatorio(erroSaida, w2CamadaDeSaida);
		for (int linha = 0; linha < hidden.length; linha++) {
			erroIntermediario[linha] = hidden[linha]*(1-hidden[linha])*(somatorio(erroSaida, w2CamadaDeSaida));
		}
		
//		erroW2[0]=alphaTaxaDeAprendizagem * erroSaida * H0Bias ;
//		w2CamadaDeSaida[0]= w2CamadaDeSaida[0]+erroW2[0];
//		System.out.println("Novo w2 0"+" :"+w2CamadaDeSaida[0]);
		
		for (int linha = 0; linha < hidden.length; linha++) {
			erroW2[linha]=alphaTaxaDeAprendizagem * erroSaida * hidden[linha];
			System.out.println("Erro w2 "+linha+" :"+erroW2[linha]);
			w2CamadaDeSaida[linha]= w2CamadaDeSaida[linha+1]+erroW2[linha];
			
//			System.out.println("Novo w2 "+(linha+1)+" :"  +w2CamadaDeSaida[linha+1]);
		}
		
//		erroW1[0][0]=alphaTaxaDeAprendizagem*erroIntermediario[0]*x0Bias;
//		erroW1[1][0]=alphaTaxaDeAprendizagem*erroIntermediario[0]*x0Bias;
				
		for (int linha = 0; linha < entrada[0].length; linha++) {
			erroW1[0][linha]=alphaTaxaDeAprendizagem*erroIntermediario[linha]*xEntrada[linha];
			w1CamadaDeEntrada[0][linha]= w1CamadaDeEntrada[0][linha]+erroW1[0][linha];
			System.out.println("Erro w1 0 "+linha+" :"+erroW1[0][linha]);
			
//			System.out.println("Novo w1 0 "+(linha+1)+" :"+w1CamadaDeEntrada[0][linha+1]);
			
			erroW1[1][linha]=alphaTaxaDeAprendizagem*erroIntermediario[linha]*xEntrada[linha];
			w1CamadaDeEntrada[1][linha]= w1CamadaDeEntrada[1][linha]+erroW1[1][linha];
			System.out.println("Erro w1 1 "+linha+" :"+erroW1[1][linha]);
			
//			System.out.println("Novo w1 1 "+(linha+1)+" :"+w1CamadaDeEntrada[1][linha+1]);
		}
		System.out.println("\n");
	}
	
	private static void treinarRede(){
		iniciandoPesosDaEntrada();
		iniciandoPesosDaSaida();
		possiveisCombinacoes();
		
		for(int iteracoes=0;iteracoes<numMaximoEpoca;iteracoes++){
			System.out.println("Epoca "+iteracoes+"\n");
			execucao(entrada[0],0);
			execucao(entrada[1], 1);
			execucao(entrada[2], 2);
			execucao(entrada[3], 3);
		}
	}
	
	public static void main(String[] args) {
		treinarRede();
	}

}
