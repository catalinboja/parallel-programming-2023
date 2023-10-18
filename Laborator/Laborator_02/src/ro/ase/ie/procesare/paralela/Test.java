package ro.ase.ie.procesare.paralela;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static List<Integer> generareDate(int N) {
		ArrayList<Integer> valori = new ArrayList<>();
		for(int i = 0; i < N; i+=1) {
			valori.add(i+1);
		}
		return valori;
	}
	
	public static void main(String[] args) {
		
		System.out.println("Generare date ...");
		int N = (int) (1e8);
		ArrayList<Integer> valori = (ArrayList<Integer>) generareDate(N);
		
		System.out.println("Start teste ...");
		
		//test solutie secventiala
		long tStart = System.currentTimeMillis();
		int rezultat = SolutieSecventiala.getMultipli(valori, 2, 25);
		long tFinal = System.currentTimeMillis();
		
		System.out.println(
				String.format("Rezultatul este %d, obtinut in %f secunde",
						rezultat, (float)(tFinal-tStart)/1000)
				);
		
		
		//test solutie paralela
		tStart = System.currentTimeMillis();
		rezultat = SolutieParalela.getMultipliInParalel(valori, 2, 25);
		tFinal = System.currentTimeMillis();
		
		System.out.println(
				String.format("Rezultatul este %d, obtinut in %f secunde",
						rezultat, (float)(tFinal-tStart)/1000)
				);
		
		
		//test solutie paralela fara race condition
		tStart = System.currentTimeMillis();
		rezultat = SolutieParalela.getMultipliInParalelFaraRaceCondition(valori, 2, 25);
		tFinal = System.currentTimeMillis();
		
		System.out.println(
				String.format("Rezultatul este %d, obtinut in %f secunde",
						rezultat, (float)(tFinal-tStart)/1000)
				);
		
	}

}
