package ro.ase.csie.ie.procesare.paralela;

import java.util.HashSet;
import java.util.Random;

public class Tombola extends Thread
{
	//merge dar nu asa cum trebuie
	volatile int numarExtras;
	
	HashSet<Integer> numereExtrase = new HashSet<>();
	
	@Override
	public void run() {
		
		System.out.println("Start joc....");
		Random random = new Random();
		
		while(numereExtrase.size() < 100) {
			this.numarExtras = (random.nextInt(100) + 1);
			System.out.println("Numar extras = " + this.numarExtras);
			numereExtrase.add(this.numarExtras);
			
			/*
			 * try { Thread.sleep(100); } catch (InterruptedException e) {
			 * e.printStackTrace(); }
			 */
		}
		
		System.out.println("***Stop joc***");
		
	}

	public int getNumarExtras() {
		return numarExtras;
	}

	public HashSet<Integer> getNumereExtrase() {
		return numereExtrase;
	}
	
	
	
	
}
