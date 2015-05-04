package funcaoTranmissao;

import java.util.ArrayList;

public class Sigmoide {
	
	
	public static double funcaoSigmoide(double[] vetorPesos, double[] vetorEntrada){
		
		double sigmoide;
		double exponencial = Math.pow(Math.E, somatorio(vetorPesos, vetorEntrada));
		
		sigmoide = 1/(1+exponencial);
		
		return sigmoide;
	}
	
	public static double somatorio(double[] vetorPesos, double[] vetorEntrada){
		double somatorio=0;
		
		for(int i=0;i<vetorEntrada.length;i++){
			somatorio=somatorio+(vetorEntrada[i]*vetorPesos[i]);
		}
		
		return somatorio;
	}
	
}
