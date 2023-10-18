package ro.ase.ie.procesare.paralela;

import java.util.ArrayList;

public class SolutieParalela {
	public static int getMultipliInParalel(ArrayList<Integer> valori, int baza, int nrIteratii) {
		Rezultat rezultat = new Rezultat();
		
		int nrThreaduri = 4;
		ArrayList<Thread> threaduri = new ArrayList<>();
		
		for(int i = 0; i < nrThreaduri; i++) {
			int indexStart = i * valori.size() / nrThreaduri;
			int indexFinal = (i+1) * valori.size() / nrThreaduri;
			if(i == nrThreaduri-1)
				indexFinal = valori.size();
			threaduri.add(
					new ThreadProcesare(rezultat, valori, indexStart, indexFinal, baza, nrIteratii));
		}
		
		for(int i = 0; i < nrThreaduri; i++) {
			threaduri.get(i).start();
		}
		
		for(int i = 0; i < nrThreaduri; i++) {
			try {
				threaduri.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return rezultat.getRezultat();
		
	}
	
	public static int getMultipliInParalelFaraRaceCondition(ArrayList<Integer> valori, int baza, int nrIteratii) {
		
		int nrThreaduri = 4;
		ArrayList<Thread> threaduri = new ArrayList<>();
		ArrayList<Rezultat> rezultate = new ArrayList<>();
		
		for(int i = 0; i <nrThreaduri ;i++) {
			rezultate.add(new Rezultat());
		}
		
		for(int i = 0; i < nrThreaduri; i++) {
			int indexStart = i * valori.size() / nrThreaduri;
			int indexFinal = (i+1) * valori.size() / nrThreaduri;
			if(i == nrThreaduri-1)
				indexFinal = valori.size();
			threaduri.add(
					new ThreadProcesare(rezultate.get(i), valori, indexStart, indexFinal, baza, nrIteratii));
		}
		
		for(int i = 0; i < nrThreaduri; i++) {
			threaduri.get(i).start();
		}
		
		for(int i = 0; i < nrThreaduri; i++) {
			try {
				threaduri.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int rezultat = 0;
		for(Rezultat rez : rezultate) {
			rezultat += rez.getRezultat();
		}
		
		return rezultat;
		
	}
}
