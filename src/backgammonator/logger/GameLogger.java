package backgammonator.logger;

import backgammonator.game.Dice;
import backgammonator.game.GameOverStatus;
import backgammonator.game.Player;
import backgammonator.game.PlayerColor;
import backgammonator.game.PlayerMove;

/**
 * Interface to provide format for the output of a single game. May be
 * implemented as writing plane text in a txt file, table in html document,
 * print on the console, etc.
 */
public interface GameLogger {

	/**
	 * Notifies the logger that a game has started between the specified
	 * players. The logger should prepare for logging.
	 * 
	 * @param whitePlayer
	 *            the first player to move.
	 * @param blackPlayer
	 *            the second player to move .
	 */
	void startGame(Player whitePlayer, Player blackPlayer);

	/**
	 * Logs a single move of the backgammon board.
	 * 
	 * @param move
	 *            the move to log.
	 * @param color
	 *            the color of the current player to move.
	 * @param dice
	 *            the numbers on the dice.
	 * @param hits
	 *            number of hits.
	 * @param bornOffs
	 *            number of bornOffs.
	 * @param invalid
	 *            true if the move to log is invalid, of false otherwise.
	 */
	void logMove(PlayerMove move, PlayerColor color, Dice dice, int hits,
			int bornOffs, boolean invalid);

	/**
	 * Notifies the logger that the game has finished giving the color of the
	 * winning player and the exist status of the game. The value of the exit
	 * status can be {@link GameOverStatus#OK},
	 * {@link GameOverStatus#INVALID_MOVE}, {@link GameOverStatus#TIMEDOUT}. or
	 * {@link GameOverStatus#EXCEPTION}.
	 * 
	 * @param status
	 *            is the exit code of the game
	 * @param winner
	 *            is the winner of the game
	 */
	void endGame(GameOverStatus status, PlayerColor winner);

	/**
	 * Returns the name of the output file or null when not available.
	 * 
	 * @return the name of the output file or null when not available.
	 */
	String getFilename();

	/**
	 * Returns the timestamp of the logged game or null when not available.
	 * 
	 * @return the timestamp of the logged game or null when not available.
	 */
	public String getGameTimestamp();

}
