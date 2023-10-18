package ro.ase.ie.procesare.paralela;

import java.util.ArrayList;

public class SolutieSecventiala {
	public static int getMultipli(ArrayList<Integer> valori, int baza, int nrIteratii) {
		int rezultat = 0;
		for (int j = 0; j < nrIteratii; j++) {
			for (int i = 0; i < valori.size(); i++) {
				if (valori.get(i) % baza == 0) {
					rezultat += 1;
				}
			}
		}
		return rezultat;
	}
}
