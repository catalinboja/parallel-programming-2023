package ro.ase.csie.ie.procesare.paralela;

import java.util.Random;

public class Consumator extends Thread {
	
	Piata piata;

	public Consumator(Piata piata) {
		this.piata = piata;
	}
	
	@Override
	public void run() {
		while(true) {
			Random random = new Random();
			int valoare = random.nextInt(50) + 1;
			if(this.piata.getStoc() >= valoare) {
				System.out.println("Consumatorul cumpara " + valoare);
				this.piata.scadeStoc(valoare);
				System.out.println("Stoc disponibil " + this.piata.getStoc());
			}
		}
	}

}
