package ro.csie.ie.procesare.paralela;

class ThreadHello extends Thread{
	public void run() {	
		while(true) {			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			System.out.println("Hello !");
			
			return;		
		}
	}
}

class ThreadBye implements Runnable{

	@Override
	public void run() {		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Bye !");
	}
	
}

public class Test {

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Start");
		
		ThreadHello thello = new ThreadHello();
		thello.start();

		
		Thread tbye = new Thread(new ThreadBye());
		tbye.start();
		
		//asteptam terminarea executiei celor 2 thread-uri
		//operatie blocanta
		thello.join();
		tbye.join();
		
		//test race condition
		ContBancar cont = new ContBancar(1000);
		CardBancar tSot = new CardBancar("Gigel", cont);
		CardBancar tSotie = new CardBancar("Ana", cont);
		
		tSot.start();
		tSotie.start();
		
		tSot.join();
		tSotie.join();
		
		System.out.println("Sold final " + cont.getSold());
		
		
		System.out.println("The end");
		
	}

}
