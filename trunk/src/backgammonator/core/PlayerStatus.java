package backgammonator.core;

/**
 * Enumeration to represent the status of the game.
 */
public enum PlayerStatus {
	
	/**
	 * The player is still playing because the game is not over yet.
	 */
	NOT_FINISHED,
	
	/**
	 * The player wins the game.
	 */
	WINS_NORMAL,
	
	/**
	 * The player loses the game.
	 */
	LOSEE_NORMAL,
	
	/**
	 * The player wins the game because the other player makes an invalid move. 
	 */
	WINS_INVALID_MOVE,
	
	/**
	 * The player loses the game because he makes an invalid move
	 */
	LOSES_INVALID_MOVE,
	
	/**
	 * The player wins the game because the other player throws exception.
	 */
	WINS_EXCEPTION,
	
	/**
	 * The player loses the game because he throws exception.
	 */
	LOSES_EXCEPTION, 
	
	/**
	 * The player wins the game because the other player is timed out.
	 */
	WINS_TIMEDOUT,
	
	/**
	 * The player loses the game because he is timed out.
	 */
	LOSES_TIMEDOUT,
	
	/**
	 * The player wins the game with double.
	 */
	WINS_DOUBLE,
	
	/**
	 * The player loses the game because the other player makes double.
	 */
	LOSES_DOUBLE,
	
	/**
	 * The player wins the game with triple.
	 */
	WINS_TRIPLE,
	
	/**
	 * The player loses the game because the other player makes triple.
	 */
	LOSES_TRIPLE;
}