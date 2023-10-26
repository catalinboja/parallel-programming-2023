package ro.ase.csie.ie.procesare.paralela;

import java.util.Random;

public class Producator extends Thread{
	
	Piata piata;

	public Producator(Piata piata) {
		super();
		this.piata = piata;
	}
	
	@Override
	public void run() {
		while(true) {		
			Random random = new Random();
			int valoare = random.nextInt(100) + 1;
			System.out.println("Producatorul a produs " + valoare);	
			this.piata.adaugaStoc(valoare, this);
			System.out.println("Valoare stoc: " + this.piata.getStoc());
		}
	}
	
	
}
