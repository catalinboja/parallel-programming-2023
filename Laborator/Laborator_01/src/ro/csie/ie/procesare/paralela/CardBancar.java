package ro.csie.ie.procesare.paralela;

import java.util.Random;

public class CardBancar extends Thread{
	
	String titular;
	ContBancar cont;
	
	public CardBancar(String titular, ContBancar cont) {
		super();
		this.titular = titular;
		this.cont = cont;
	}
	
	public void run() {
		while(true) {
			if(cont.getSold() <= 0) {
				return;
			}
			Random random = new Random();
			double suma = random.nextInt(10);
			System.out.println(this.titular + " incearca o plata de " + suma);
			this.cont.plata(suma);
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
