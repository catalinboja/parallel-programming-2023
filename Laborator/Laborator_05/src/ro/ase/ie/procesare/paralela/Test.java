package ro.ase.ie.procesare.paralela;

import java.util.concurrent.LinkedBlockingDeque;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		
		Piata piata = new Piata();
		
		Thread tProducator = new Thread(new Producator(piata));
		Thread tConsumator = new Thread(new Consumator(piata));
		
		tProducator.start();
		tConsumator.start();
			
		Thread.sleep(20000);
		piata.oprestePiata();
		
		
		tProducator.join();
		tConsumator.join();
		

		//test producator/consumator cu un LinkedBlockingDeque
		
		LinkedBlockingDeque<String> coadaMesaje = new LinkedBlockingDeque<>(10);
		
		Thread tProcesator = new Thread(new ProcesatorMesaje(coadaMesaje));
		Thread tInjector = new Thread(new InjectorMesaje(coadaMesaje));
		
		tProcesator.start();
		
		Thread.sleep(5000);
		
		tInjector.start();
		
		tInjector.join();
		tProcesator.join();
		
		
	}

}
