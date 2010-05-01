package backgammonator.core;

/**
 * Represents a game between two players.
 */

public interface Game {

	/**
	 * Starts and navigates the game between the two players.
	 * Always the white player is first.
	 * 
	 * @return the status of the game.
	 * @see GameOverStatus
	 */
	GameOverStatus start();
}
