package ro.ase.ie.procesare.paralela;

public class ThreadPrimCuPas extends Thread{
	long limitaStart;
	long limitaFinal;
	int pas;
	int nrPrime = 0;
	
	public ThreadPrimCuPas(long limitaStart, long limitaFinal, int pas) {
		this.limitaStart = limitaStart;
		this.limitaFinal = limitaFinal;
		this.pas = pas;
	}

	public int getNrPrime() {
		return nrPrime;
	}

	public void run() {
		double tStart = System.currentTimeMillis();
		this.nrPrime = Prim.getNrPrimeCuPas(
				limitaStart, limitaFinal, pas);
		double tFinal = System.currentTimeMillis();
		System.out.println("**** Durata thread " 
				+ (tFinal-tStart)/1000 + " secunde");
	}
}
