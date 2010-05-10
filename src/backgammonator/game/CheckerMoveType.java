package backgammonator.game;

/**
 * Types of checker moves.
 */
public enum CheckerMoveType {
	STANDARD_MOVE, NO_AVAILABLE_MOVE, REENTER_HIT_CHECKER;

	public String toString() {
		return this == STANDARD_MOVE ? "" : this == NO_AVAILABLE_MOVE ? "unavailable" : "reentering";
	}
}