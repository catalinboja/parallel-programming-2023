package ro.ase.ie.procesare.paralela;

import java.util.ArrayList;

public class ThreadProcesare extends Thread{

	Rezultat rezultat;
	ArrayList<Integer> valori;
	int indexStart;
	int indexFinal;
	int baza;
	int nrIteratii;
	
	
	public ThreadProcesare(Rezultat rezultat, ArrayList<Integer> valori, int indexStart, int indexFinal, int baza,
			int nrIteratii) {
		super();
		this.rezultat = rezultat;
		this.valori = valori;
		this.indexStart = indexStart;
		this.indexFinal = indexFinal;
		this.baza = baza;
		this.nrIteratii = nrIteratii;
	}

	@Override
	public void run() {
		for (int j = 0; j < nrIteratii; j++) {
			for (int i = indexStart; i < indexFinal; i++) {
				if (valori.get(i) % baza == 0) {
					rezultat.increment();
				}
			}
		}
	}
	
	
}
