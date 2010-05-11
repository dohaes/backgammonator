package backgammonator.lib.game;

/**
 * Represents a game between two players.
 */
public interface Game {
	
	/**
	 * Timeout in milliseconds to wait for a single move.
	 * If the time elapses and the player has not yet returned a move,
	 * the game is over and the player loses.
	 */
	long MOVE_TIMEOUT = 1000;

	/**
	 * Starts and navigates the game between the two players.
	 * Always the white player is first.
	 * 
	 * @return the status of the game.
	 * @see GameOverStatus
	 */
	GameOverStatus start();
	
	/**
	 * Returns the winner in this game.
	 * 
	 * @return Player the winner in this game or <code>null</code> if the game is not over.
	 */
	Player getWinner();
	
	/**
   * Returns the name of the game log file.
   * 
   * @return the name of the game log file or <code>null</code> if the game is not over.
   */
  String getFilename();
}
