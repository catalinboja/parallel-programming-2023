package ro.ase.csie.ie.procesare.paralela;

public class ChatBot extends Thread{
	
	String nume;
	ChatBot prieten;
	
	public synchronized void hello(ChatBot bot) {
		System.out.println(this.nume + " saluta pe " + bot.nume);
		bot.raspunde(this);
	}
	
	public synchronized void raspunde(ChatBot bot) {
		System.out.println(this.nume + 
				" ii raspunde lui " + bot.nume);
	}
	
	@Override
	public void run() {
		this.hello(prieten);
	}

	public ChatBot(String nume) {
		this.nume = nume;
	}

	public void setPrieten(ChatBot prieten) {
		this.prieten = prieten;
	}
	
	
}
