package backgammonator.sample.player.interfacce;

import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.Player;
import backgammonator.lib.game.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface. This player causes a
 * deadlock.
 */
public class DeadlockInMovePlayer extends AbstractSamplePlayer {

	private Object monitor1 = new Object();
	private Object monitor2 = new Object();

	/**
	 * @see Player#getMove(BackgammonBoard, Dice)
	 */
	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice) {
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

	/**
	 * @see Player#getName()
	 */
	@Override
	public String getName() {
		return "Sample Player Deadlock";
	}

	/**
	 * Runnable that causes a deadlock with the game thread.
	 */
	class DeadlockRunnable implements Runnable {

		@SuppressWarnings("synthetic-access")
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
