package backgammonator.core;

/**
 * Represents the move of a single checker.
 */
public final class CheckerMove {

	private int startPoint;
	private int die;
	private CheckerMoveType type;

	/**
	 * Create CheckerMove object with given start point and move length.
	 * 
	 * @param startPoint
	 *            the start point.
	 * @param die
	 *            the die.
	 */
	public CheckerMove(CheckerMoveType type, int die) {
		this.type = type;
		setStartPoint(1);
		setDie(die);
	}

	/**
	 * Create CheckerMove object with given start point and move length.
	 * 
	 * @param startPoint
	 *            the start point.
	 * @param die
	 *            the die.
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
		return startPoint + die;
	}

	/**
	 * Returns true if the move bears off the checker.
	 * 
	 * @return true if the move bears off the checker.
	 */
	public boolean isBearingOff() {
		return startPoint + die > Constants.POINTS_COUNT;
	}

	/**
	 * Returns true if there is no available move.
	 * 
	 * @return true if there is no available move.
	 */
	public boolean isUnavailableMove() {
		return type == CheckerMoveType.NO_AVAILABLE_MOVE;
	}

	/**
	 * Returns true if the move reenters a hit checker.
	 * 
	 * @return true if the move reenters a hit checker.
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
}