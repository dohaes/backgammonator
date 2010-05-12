package backgammonator.lib.game;

/**
 * Represents the move of a single checker.
 */
public final class CheckerMove {

	private CheckerMoveType type;
	private int startPoint;
	private int die;

	/**
	 * Create CheckerMove object of a given type for the specified die.
	 * 
	 * @param die must be between 1 and 6.
	 * @param type the type of the checker move.
	 * @throws IllegalArgumentException if die is invalid.
	 */
	public CheckerMove(CheckerMoveType type, int die) {
		this.type = type;
		this.startPoint = Constants.POINTS_COUNT + 1;
		setDie(die);
	}

	/**
	 * Create CheckerMove object with given start point and move length.
	 * 
	 * @param startPoint must be between 1 and 24.
	 * @param die must be between 1 and 6.
	 * @throws IllegalArgumentException if die or start point are invalid.
	 */
	public CheckerMove(int startPoint, int die) {
		this.type = CheckerMoveType.STANDARD_MOVE;
		setStartPoint(startPoint);
		setDie(die);
	}

	/**
	 * Getter for the position from which the checker is moved.
	 */
	public int getStartPoint() {
		return startPoint;
	}

	/**
	 * Getter for the die that the checker is moved.
	 */
	public int getDie() {
		return die;
	}

	/**
	 * Getter for the end position of the checker.
	 */
	public int getEndPoint() {
		return startPoint - die;
	}

	/**
	 * Returns true if the move bears off the checker.
	 */
	public boolean isBearingOff() {
		return startPoint - die <= 0;
	}

	/**
	 * Returns true if there is no available move.
	 */
	public boolean isUnavailableMove() {
		return type == CheckerMoveType.NO_AVAILABLE_MOVE;
	}

	/**
	 * Returns true if the move reenters a hit checker.
	 */
	public boolean isReenterHitChecker() {
		return type == CheckerMoveType.REENTER_HIT_CHECKER;
	}

	private void setStartPoint(int startPoint) {
		if (startPoint < 1 || startPoint > Constants.POINTS_COUNT) {
			throw new IllegalArgumentException("Invalid start point : "
					+ startPoint);
		}
		this.startPoint = startPoint;
	}

	private void setDie(int die) {
		if (die < 1 || die > Constants.MAX_DIE) {
			throw new IllegalArgumentException("Invalid die : " + die);
		}
		this.die = die;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return type + "(" + this.startPoint + ", " + this.die + ")";
	}
}