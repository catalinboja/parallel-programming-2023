package ro.ase.csie.ie.procesare.paralela;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		
		//test deadlock	
		/*
		 * ChatBot gigel = new ChatBot("Gigel"); ChatBot ana = new ChatBot("Ana");
		 * gigel.setPrieten(ana); ana.setPrieten(gigel);
		 * 
		 * ana.start(); gigel.start();
		 * 
		 * ana.join(); gigel.join();
		 */
		
		//test volatile
		/*
		 * Tombola tombola = new Tombola(); Thread gigel = new Thread(new
		 * JucatorTombola("Gigel", 13, tombola)); Thread ana = new Thread(new
		 * JucatorTombola("Ana", 45, tombola)); Thread ionel = new Thread(new
		 * JucatorTombola("Ionel", 34, tombola));
		 * 
		 * gigel.start(); ana.start(); ionel.start(); tombola.start();
		 * 
		 * tombola.join(); gigel.join(); ana.join(); ionel.join();
		 */
		
		//test Producator - Consumator
		Piata piata = new Piata(0);
		Producator prod = new Producator(piata);
		Consumator cons = new Consumator(piata);
		Consumator cons2 = new Consumator(piata);
		
		prod.start();
		cons.start();
		cons2.start();
		
		
		cons.join();
		cons2.join();
		prod.join();
	}
}
