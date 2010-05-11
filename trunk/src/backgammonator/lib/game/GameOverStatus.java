package backgammonator.lib.game;

/**
 * Enumeration to represent the status of the game when it is over.
 */
public enum GameOverStatus { // TODO
  /**
   * Indicates that the game has finished normally.
   */
	NORMAL,
	
	/**
	 * Indicates that the game has finished bacuse one of the pleyers performed na invalid move.
	 */
	INVALID_MOVE,
	
	/**
   * Indicates that the game has finished bacuse one of the pleyers have thrown exception.
   */
	EXCEPTION,
	
	/**
   * Indicates that the game has finished bacuse one of the pleyers have been timed out.
   */
	TIMEDOUT,
	
	/**
   * Indicates that the game has finished bacuse one of the pleyers made double.
   */
	DOUBLE,
	
	/**
   * Indicates that the game has finished bacuse one of the pleyers made triple.
   */
	TRIPLE;
}