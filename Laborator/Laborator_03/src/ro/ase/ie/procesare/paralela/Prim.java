package ro.ase.ie.procesare.paralela;

import java.util.ArrayList;
import java.util.List;

public class Prim {
	
	
	public static boolean estePrim(long numar) {
		boolean estePrim = true;
		for(long i = 2; i <= numar/2; i++) {
			if(numar % i == 0) {
				estePrim = false;
				break;
			}
		}
		return estePrim;
	}

	public static int getNrPrime(long startInterval, long finalInterval) {
		int nrPrime = 0;
		for(long i = startInterval; i < finalInterval; i++) {
			if(estePrim(i))
				nrPrime += 1;
		}
		return nrPrime;
	}
	
	public static int getNrPrimeCuPas(
			long startInterval, long finalInterval, int pas) {
		int nrPrime = 0;
		for(long i = startInterval; i < finalInterval; i += pas) {
			if(estePrim(i))
				nrPrime += 1;
		}
		return nrPrime;
	}
	
	public static int getNrPrimeParalel(
			long startInterval, long finalInterval) throws InterruptedException {
		
		int nrProcesoare = Runtime.getRuntime().availableProcessors();
		System.out.println("Nr procesoare: " + nrProcesoare);
		
		int interval = (int) ((finalInterval - startInterval)/nrProcesoare);
		List<ThreadPrim> fireExecutie = new ArrayList<>();
		
		for(int i = 0; i < nrProcesoare; i++) {
			int startI = (int) (i * interval + startInterval);
			int finalI = (int) ((i == nrProcesoare-1) ? finalInterval :
				(i+1)*interval + startInterval);
			fireExecutie.add(new ThreadPrim(startI, finalI));
		}
		
		for(Thread t : fireExecutie) {
			t.start();
		}
		for(Thread t : fireExecutie) {
			t.join();
		}
		
		int rezultat = 0;
		for(ThreadPrim t : fireExecutie) {
			rezultat += t.getNrPrime();
		}
		return rezultat;
	}
	
	public static int getNrPrimeParalelCuPas(
			long startInterval, long finalInterval) throws InterruptedException {
		
		int nrProcesoare = Runtime.getRuntime().availableProcessors();
		System.out.println("Nr procesoare: " + nrProcesoare);
		
		List<ThreadPrimCuPas> fireExecutie = new ArrayList<>();
		
		for(int i = 0; i < nrProcesoare; i++) {
			fireExecutie.add(new ThreadPrimCuPas(
					startInterval+(i*2+1), finalInterval, 2*nrProcesoare));
		}
		
		for(Thread t : fireExecutie) {
			t.start();
		}
		for(Thread t : fireExecutie) {
			t.join();
		}
		
		int rezultat = 0;
		for(ThreadPrimCuPas t : fireExecutie) {
			rezultat += t.getNrPrime();
		}
		return rezultat;
	}
}





