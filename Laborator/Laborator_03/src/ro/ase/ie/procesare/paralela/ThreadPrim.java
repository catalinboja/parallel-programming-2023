package ro.ase.ie.procesare.paralela;

public class ThreadPrim extends Thread{
	
	long limitaStart;
	long limitaFinal;
	int nrPrime = 0;
	
	public ThreadPrim(long limitaStart, long limitaFinal) {
		this.limitaStart = limitaStart;
		this.limitaFinal = limitaFinal;
	}

	public int getNrPrime() {
		return nrPrime;
	}

	public void run() {
		double tStart = System.currentTimeMillis();
		this.nrPrime = Prim.getNrPrime(
				limitaStart, limitaFinal);
		double tFinal = System.currentTimeMillis();
		System.out.println("**** Durata thread " 
				+ (tFinal-tStart)/1000 + " secunde");
	}
}
