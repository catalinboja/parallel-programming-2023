package ro.ase.ie.procesare.paralela;

public class Piata {
	
	int stoc;
	volatile boolean esteOprita = false;
	
	public void oprestePiata() {
		System.out.println("$$$$$$$$$$$ Piata se inchide $$$$$$$$$$$$");
		this.esteOprita = true;
	}
	
	public boolean esteOprita() {
		return this.esteOprita;
	}
	
	public synchronized void adaugaProduse(int nrProduse) throws InterruptedException {
		
		
		//notificam consumatorul sa isi reia activitatea
		this.notifyAll();
		
		System.out.println(">>>> Se adauga " + nrProduse);
		this.stoc += nrProduse;
		System.out.println("Stoc curent: " + this.stoc);
		
		//oprim producatorul
		if(this.stoc >= 500) {
			System.out.println("*********** Producatorul a fost oprit **********");
			this.wait();
		}
	}
	
	public synchronized void scoateProduse(int nrProduse) throws InterruptedException {
		
		//notificam producatorii sa isi reia activitatea
		this.notifyAll();
		
		if(this.stoc - nrProduse < 0) {
			System.out.println("############  Consumatorul a fost oprit #######");
			this.wait();
		} else {
			System.out.println("<<<< Se scot " + nrProduse);
			this.stoc -= nrProduse;
			System.out.println("Stoc curent: " + this.stoc);
		}
	}
}
