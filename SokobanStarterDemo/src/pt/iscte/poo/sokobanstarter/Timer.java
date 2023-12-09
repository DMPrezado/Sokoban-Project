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
	}
	
	public Thread getThread() {return thread;}
	
	public void stop() {
		thread.interrupt();
		System.out.println("asdasdasdasdasdasdasdasdasdasdasdasdasd");
	}
	
	public void resetTimer() {
		seconds = initialTime;
		start();
	}
	
	
}
