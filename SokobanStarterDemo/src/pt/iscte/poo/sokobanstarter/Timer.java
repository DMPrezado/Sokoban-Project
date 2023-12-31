package pt.iscte.poo.sokobanstarter;

public class Timer {

	private  int initialTime;
	private int seconds;
	private Thread thread;
	
	public Timer(int seconds) {
		this.seconds=seconds;
		initialTime=seconds;
	}
	
	public int getSeconds() {return seconds;}
	
	 public void start() {
	        thread = new Thread(() -> {
	            try {
	                while (seconds != 0) {
	                    Thread.sleep(1000);
	                    seconds--;
	                    GameEngine.getInstance().update(null);
	                }
	                zero();
	            } catch (InterruptedException e) {}
	        });
	        thread.start();
	    }

	private void zero() {
		if(seconds == 0)
			stop();
		GameEngine.getInstance().levelFailed(); //Faltava esta linha quando entreguei
	}
	
	public Thread getThread() {return thread;}
	
	public void stop() {
		thread.interrupt();
	}
	
	public void resetTimer() {
		stop();
		seconds = initialTime;
		start();
	}
	
	
}
