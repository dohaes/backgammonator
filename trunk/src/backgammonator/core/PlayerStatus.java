package backgammonator.core;

/**
 * Enumeration to represent the status of the game.
 */
public enum PlayerStatus {
	
	NOT_FINISHED,
	WINS_NORMAL,
	LOSE_NORMAL,
	WIN_INVALID_MOVE,
	LOSE_INVALID_MOVE,
	WIN_EXCEPTION,
	LOSE_EXCEPTION, 
	WIN_TIMEDOUT,
	LOSE_TIMEDOUT,
	WIN_DOUBLE,
	LOSE_DOUBLE,
	WIN_TRIPLE,
	LOSE_TRIPLE;
}