package ro.ase.ie.procesare.paralela;

import java.util.concurrent.LinkedBlockingDeque;

public class InjectorMesaje implements Runnable{

	LinkedBlockingDeque<String> coadaMesaje;

	public InjectorMesaje(LinkedBlockingDeque<String> coadaMesaje) {
		super();
		this.coadaMesaje = coadaMesaje;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0 ;i < 100; i++) {
			try {
				Thread.sleep(200);
				
				System.out.println("Se insereaza mesajul " + (i+1));
				this.coadaMesaje.put("Mesaj "+(i+1));
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Terminam adaugarea de mesaje");
		try {
			this.coadaMesaje.put("end");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
}
