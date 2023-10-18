package ro.ase.ie.procesare.paralela;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		long N = (long) 3e5;
		int nrPrime = 0;
		
		System.out.println("Start...");
		
		double tStart = System.currentTimeMillis();
		nrPrime = Prim.getNrPrime(1, N);
		double tFinal = System.currentTimeMillis();
		
		System.out.println(String.format(
				"Durata test secvential = %f secunde cu %d nr prime",
				(tFinal-tStart)/1000, nrPrime));
		
		
		tStart = System.currentTimeMillis();
		nrPrime = Prim.getNrPrimeParalel(1, N);
		tFinal = System.currentTimeMillis();
		
		System.out.println(String.format(
				"Durata test paralel = %f secunde cu %d nr prime",
				(tFinal-tStart)/1000, nrPrime));
		
		tStart = System.currentTimeMillis();
		nrPrime = Prim.getNrPrimeParalelCuPas(0, N);
		tFinal = System.currentTimeMillis();
		
		System.out.println(String.format(
				"Durata test paralel cu pas = %f secunde cu %d nr prime",
				(tFinal-tStart)/1000, nrPrime));
		
	}

}
