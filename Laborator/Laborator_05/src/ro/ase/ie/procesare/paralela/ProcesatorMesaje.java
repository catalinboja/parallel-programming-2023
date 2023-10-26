package ro.ase.ie.procesare.paralela;

import java.util.concurrent.LinkedBlockingDeque;

public class ProcesatorMesaje implements Runnable{

	LinkedBlockingDeque<String> coadaMesaje;

	public ProcesatorMesaje(LinkedBlockingDeque<String> coadaMesaje) {
		super();
		this.coadaMesaje = coadaMesaje;
	}

	@Override
	public void run() {
		while(true) {
			try {
				
				System.out.println("Extrage mesaj din coada........");
				String mesaj = this.coadaMesaje.take();
				System.out.println("Procesare " + mesaj);
				
				if(mesaj.equals("Mesaj 50")) {
					Thread.sleep(10000);
				}
				
				//verificare poisen-pill
				if(mesaj.equals("end")) {
					System.out.println("Inchidere procesator mesaje");
					return;
				}
								
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
