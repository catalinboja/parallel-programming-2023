package ro.csie.ie.procesare.paralela;

public class ContBancar {
	
	double sold;

	public ContBancar(double sold) {
		this.sold = sold;
	};
	
	public double getSold() {
		return this.sold;
	}
	
	
	public synchronized void plata(double suma) {
		if(suma <= this.sold) {
			System.out.println("Sold actualizat cu suma " + suma);
			this.sold -= suma;
			System.out.println("Sold curent " + this.sold);
		}
	}

}

