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
	 * Indicates that the game has finished because one of the players performed
	 * invalid move.
	 */
	INVALID_MOVE,

	/**
	 * Indicates that the game has finished because one of the players have
	 * thrown exception.
	 */
	EXCEPTION,

	/**
	 * Indicates that the game has finished because one of the players have been
	 * timed out.
	 */
	TIMEDOUT,

	/**
	 * Indicates that the game has finished because one of the players made
	 * double.
	 */
	DOUBLE,

	/**
	 * Indicates that the game has finished because one of the players made
	 * triple.
	 */
	TRIPLE;

	public String toString() {
		String resultString;

		switch (this) {
		case NORMAL:
			resultString = "normal victory";
			break;
		case DOUBLE:
			resultString = "double victory";
			break;
		case TRIPLE:
			resultString = "triple victory";
			break;
		case EXCEPTION:
			resultString = "exception";
			break;
		case INVALID_MOVE:
			resultString = "invalid move";
			break;
		case TIMEDOUT:
			resultString = "timed out";
			break;
		default:
			throw new IllegalArgumentException("Invalid status:" + this.name());
		}

		return resultString;
	};
}