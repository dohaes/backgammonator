package backgammonator.core;

/**
 * Represents abstraction of the AI player. If a contestant wants to use the
 * Backgammonator Library to test his AI, he has to implement this interface.
 */
public interface Player {

	/**
	 * Returns the name of the player.
	 */
	String getName();

	/**
	 * This method returns the players move according to the current
	 * configuration of the backgammon board and the given dice.
	 * 
	 * @param board
	 *            the current configuration of the board
	 * @param dice
	 *            the dice.
	 * @throws Exception
	 *             if any error occurs.
	 * @return the move of the player.
	 */
	PlayerMove getMove(BackgammonBoard board, Dice dice) throws Exception;

	/**
	 * Called from the backgammonator engine to identify the end of the game.
	 * 
	 * @param wins
	 *            true if this player wins, or false otherwise.
	 */
	void gameOver(boolean wins);
}