package ro.ase.csie.ie.procesare.paralela;

import java.util.concurrent.atomic.AtomicInteger;

public class Piata {
	AtomicInteger stoc;
	final int maxStoc = 1000;
	
	boolean getMaxStoc() {
		return this.maxStoc == this.stoc.get();
	}
	
	public Piata(int stoc) {
		this.stoc = new AtomicInteger(stoc);
	}

	public void adaugaStoc(int valoare, Producator t) {
		//this.stoc += valoare;
		this.stoc.getAndAdd(valoare);
		if(this.stoc.get() >= this.maxStoc) {
			System.out.println("*********Piata suspenda producatorul**********");
			synchronized(t) {
				
			try {
				
					t.wait();
				}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		}
	}
	
	public void scadeStoc(int valoare) {
		//this.stoc -= valoare;
		this.stoc.getAndAdd(valoare * -1);
	}
	
	public int getStoc() {
		return this.stoc.get();
	}
}
