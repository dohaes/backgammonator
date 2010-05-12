package backgammonator.lib.game;

/**
 * Types of checker moves.
 */
public enum CheckerMoveType {
	/**
	 * Indicates standard move.
	 */
	STANDARD_MOVE,
	
	/**
	 * Indicates that the player cannot move.
	 */
	NO_AVAILABLE_MOVE,
	
	/**
	 * Indicates that the player will move with a hit checker.
	 */
	REENTER_HIT_CHECKER;

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return this == STANDARD_MOVE ? ""
				: this == NO_AVAILABLE_MOVE ? "unavailable" : "reentering";
	}
}