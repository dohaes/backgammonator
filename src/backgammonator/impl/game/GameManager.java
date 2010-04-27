package backgammonator.impl.game;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.GameOverStatus;
import backgammonator.core.GameLogger;
import backgammonator.core.Player;
import backgammonator.core.PlayerMove;
import backgammonator.impl.logger.GameLoggerFactory;
import backgammonator.util.Debug;

/**
 * An instance of this class is created for each game between two players. It is
 * used for realization of the rules of backgammon and manages the game. With
 * each instance of the GameManager class a {@link BackgammonBoard} object is
 * associated. Class GameManager uses class {@link MoveValidator} to validate
 * the palyers' moves.
 */

public final class GameManager {

	private Player whitePlayer;
	private Player blackPlayer;

	private static final long MOVE_TIMEOUT = 1000;

	private BackgammonBoardImpl board;
	private DiceImpl dice;
	private GameLogger logger;
	private boolean logMoves;

	private PlayerMove currentMove = null;
	private Object synch = new Object();
	private Throwable t = null;

	/**
	 * Constructs a game between two AI players.
	 */
	public GameManager(Player whitePlayer, Player blackPlayer, boolean logMoves) {
		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;
		dice = new DiceImpl();
		this.logMoves = logMoves;
	}

	/**
	 * This method starts and navigates the game between the two players. Always
	 * the white player is first.
	 * @return the status of the game.
	 */
	public GameOverStatus start() {
		board = new BackgammonBoardImpl();
		if (logMoves) {
			logger = GameLoggerFactory.getLogger(GameLoggerFactory.HTML);
			logger.startGame(whitePlayer, blackPlayer);
		}

		GameOverStatus status = null;
		while (true) {
			board.switchPlayer();
			status = makeMove(whitePlayer, blackPlayer);
			if (status != null)
				break;
			board.switchPlayer();
			status = makeMove(blackPlayer, whitePlayer);
			if (status != null)
				break;
		}

		if (logMoves) logger.endGame(status, status == GameOverStatus.OK ?
				board.getCurrentPlayerColor() : board.getCurrentPlayerColor().oposite());
		return status;
	}

//	/**
//	 * Return the end game status if the game is over, on null otherwise
//	 */
//	private GameOverStatus _makeMove(Player currentPlayer, Player other) {
//		PlayerMove currentMove;
//		dice.generateNext();
//		try {
//			currentMove = currentPlayer.getMove(board, dice);
//			if (currentMove == null || !board.makeMove(currentMove, dice)) {
//				currentPlayer.gameOver(false);
//				other.gameOver(true);
//				return GameOverStatus.INVALID_MOVE;
//			}
//			logger.logMove(currentMove, board.getCurrentPlayerColor(), dice, board.getHits(board.getCurrentPlayerColor()),
//					                          board.getBornOff(board.getCurrentPlayerColor()));
//			if (board.getBornOff(board.getCurrentPlayerColor()) == 15) {
//				currentPlayer.gameOver(true);
//				other.gameOver(false);
//				return GameOverStatus.OK;
//			}
//		} catch (Exception e) {
//			currentPlayer.gameOver(false);
//			other.gameOver(true);
//			return GameOverStatus.EXCEPTION;
//		}
//
//		return null;
//	}

	/**
	 * Return the end game status if the game is over, on null otherwise
	 */
	private GameOverStatus makeMove(Player currentPlayer, Player other) {

		dice.generateNext();
		mover = new MoveThread(currentPlayer);
		try {
			mover.start();
			waitForMoveDone();

			if (t != null) {
				Debug.getInstance().error(
						"Exception thrown while performing move",
						Debug.GAME_MODULE, t);
				currentPlayer.gameOver(false);
				other.gameOver(true);
				return GameOverStatus.EXCEPTION;
			}

			if (!notified) {
				Debug.getInstance().error("Move timeout", Debug.GAME_MODULE,
						null);
				currentPlayer.gameOver(false);
				other.gameOver(true);
				stop(mover, true, 100);
				return GameOverStatus.TIMEDOUT;
			}

			boolean invalid = false;
			
			if (currentMove == null || !board.makeMove(currentMove, dice)) {
				Debug.getInstance().error("Invalid move", Debug.GAME_MODULE, null);
				currentPlayer.gameOver(false);
				other.gameOver(true);
				invalid = true;
			}

			if (logMoves && currentMove != null) {
				logger.logMove(currentMove, board.getCurrentPlayerColor(),
						dice, board.getHits(board.getCurrentPlayerColor().oposite()),
						board.getBornOff(board.getCurrentPlayerColor()), invalid);
			}

			if (invalid)  return GameOverStatus.INVALID_MOVE;
			
			if (board.getBornOff(board.getCurrentPlayerColor()) == 15) {
				currentPlayer.gameOver(true);
				other.gameOver(false);
				return GameOverStatus.OK;
			}
		} catch (Exception e) {
			Debug.getInstance().error(
					"Exception thrown while performing move",
					Debug.GAME_MODULE, e);
			currentPlayer.gameOver(false);
			other.gameOver(true);
			return GameOverStatus.EXCEPTION;
		}

		return null;
	}

	private boolean notified = false;
	private Thread mover = null;

	private void waitForMoveDone() {
		synchronized (synch) {
			try {
				synch.wait(MOVE_TIMEOUT);
			} catch (InterruptedException e) {
				Debug.getInstance().error("Error waitin for move",
						Debug.GAME_MODULE, e);
			}
		}
	}

	final class MoveThread extends Thread {

		private Player currentPlayer;

		MoveThread(Player player) {
			currentPlayer = player;
			notified = false;
			t = null;
		}

		@Override
		public void run() {
			try {
				currentMove = currentPlayer.getMove(board, dice);
			} catch (Exception e) {
				t = e;
			}
			synchronized (synch) {
				notified = true;
				synch.notify();
			}
		}
	}

	/**
	 * An utility method to safely stop a thread. Initially it tries to
	 * interrupt the thread. It it is still alive, it stops it. And if stop
	 * doesn't help, it calls destroy.
	 * 
	 * @param thread the thread to stop
	 * @param callStop <code>true</code> to call stop and destroy.
	 * @param joinTime the time to wait for the thread to die.
	 * @return <code>true</code> if the thread is stopped
	 */
	@SuppressWarnings("deprecation")
	private static final boolean stop(Thread thread, boolean callStop,
			int joinTime) {
		boolean alive = thread.isAlive();
		if (alive) {
			try {
				thread.interrupt();
			} catch (Throwable t) {
			}
			if (callStop && thread.isAlive())
				try {
					thread.join(joinTime);
					if (thread.isAlive())
						thread.stop();
					if (thread.isAlive())
						thread.join(joinTime);
					if (thread.isAlive())
						thread.destroy();
				} catch (Throwable t) {
				}
			alive = thread.isAlive();
		}
		return !alive;
	}

}
