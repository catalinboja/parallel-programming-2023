package ro.ase.csie.ie.procesare.paralela;

public class JucatorTombola implements Runnable{
	
	String nume;
	int numarNorocos;
	Tombola tombola;
	
	public JucatorTombola(String nume, int numarNorocos, Tombola tombola) {
		this.nume = nume;
		this.numarNorocos = numarNorocos;
		this.tombola = tombola;
	}

	@Override
	public void run() {
		
		System.out.println(
				this.nume + " a inceput jocul cu numarul " + 
				this.numarNorocos);
		
		while(true) {
			
			//nici cu volatile valoare nu se modifica instant
			/*
			 * if(this.tombola.getNumarExtras() == this.numarNorocos) {
			 * System.out.println(this.nume + " a castigat un premiu"); return; }
			 */
			
			if(this.tombola.getNumereExtrase().contains(this.numarNorocos)) {
				System.out.println(this.nume + " a castigat un premiu");
				return;
			}
		}
	}

}
