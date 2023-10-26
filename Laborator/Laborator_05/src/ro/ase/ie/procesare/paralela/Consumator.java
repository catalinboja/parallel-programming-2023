package ro.ase.ie.procesare.paralela;

import java.util.Random;

public class Consumator implements Runnable{
	
	Piata piata;
	
	public Consumator(Piata piata) {
		super();
		this.piata = piata;
	}

	@Override
	public void run() {	
		while(!this.piata.esteOprita()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Random random = new Random();
			try {
				this.piata.scoateProduse(random.nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Eroare scoate produs");
			}
		}
		
		System.out.println("Consumatorul se inchide");
	}
}
