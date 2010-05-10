package backgammonator.sample.player.protocol.java;

/**
 * Sample implementation of a player using the protocol.
 * This player causes a deadlock.
 */
public class DeadlockInMovePlayer {
  
  private static Object monitor1 = new Object();
  private static Object monitor2 = new Object();
  
  public static void main(String[] args) {
	  try {
	      synchronized (monitor1) {
	        Thread.sleep(500);
	        synchronized (monitor2) {
	          monitor2.notify();
	        }
	      }
	    } catch (Throwable t) {
	    }
  }
	
	/**
	 * Runnable that causes a deadlock with the game thread.
	 */
	class DeadlockRunnable implements Runnable {

    @Override
    public void run() {
      try {
        synchronized (monitor2) {
            Thread.sleep(500);
          synchronized (monitor1) {
            monitor1.notify();
          }
        }
      } catch (Throwable t) {
      }
    }

  }

}
