package ro.ase.ie.procesare.paralela;

import java.util.Random;

public class Producator implements Runnable{
	
	Piata piata;
	
	public Producator(Piata piata) {
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
				this.piata.adaugaProduse(random.nextInt(100));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Eroare adaugare produse");
			}
		}
		System.out.println("Producatorul se inchide");
	}
}
