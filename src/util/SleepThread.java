package util;

public class SleepThread extends Thread{
	
	@Override
	public void run() {
		try {
			this.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
