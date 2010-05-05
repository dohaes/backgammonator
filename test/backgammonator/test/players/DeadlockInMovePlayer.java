package backgammonator.test.players;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.Player;
import backgammonator.core.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface.
 * This player returns null for a move.
 */
public class DeadlockInMovePlayer extends AbstractTestPlayer {
  
  private Object monitor1 = new Object();
  private Object monitor2 = new Object();

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice) throws Exception {
	  new Thread(new DeadlockRunnable(), "Deadlock in getMove").start();
    try {
      synchronized (monitor1) {
        Thread.sleep(500);
        synchronized (monitor2) {
          monitor2.notify();
        }
      }
    } catch (Throwable t) {
      System.out.println("[DeadlockInMovePlayer] Exception in getMove");
      t.printStackTrace();
    }
    System.out.println("[DeadlockInMovePlayer] Exited getMove");
    return null;
  }

	@Override
	public String getName() {
		return "Dummy Player NPE";
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
        System.out.println("[DeadlockRunnable] Exception in run");
        t.printStackTrace();
      }
      System.out.println("[DeadlockRunnable] Exited run");
    }

  }

}
