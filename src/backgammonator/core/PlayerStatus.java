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
	 * 
	 */
	WINS_NORMAL,
	LOSEE_NORMAL,
	WINS_INVALID_MOVE,
	LOSES_INVALID_MOVE,
	WINS_EXCEPTION,
	LOSES_EXCEPTION, 
	WINS_TIMEDOUT,
	LOSES_TIMEDOUT,
	WINS_DOUBLE,
	LOSES_DOUBLE,
	WINS_TRIPLE,
	LOSES_TRIPLE;
}