package backgammonator.core;

/**
 * Represents the move of a single checker.
 */
public final class CheckerMove {

	private int startPoint;
	private int moveLength;
	private boolean hasHit = false;

	/**
	 * Create CheckerMove object with given start point and move length.
	 * 
	 * @param startPoint
	 *            the start point.
	 * @param moveLength
	 *            the move length.
	 */
	public CheckerMove(int startPoint, int moveLength) {
		setStartPoint(startPoint);
		setMoveLength(moveLength);
	}

	/**
	 * Getter for the position from which the checker is moved.
	 */
	public int getStartPoint() {
		return startPoint;
	}

	/**
	 * Getter for the length that the checker is moved.
	 */
	public int getMoveLength() {
		return moveLength;
	}

	private void setStartPoint(int startPoint) {
		if (startPoint < 1 || startPoint > Constants.POINTS_COUNT) {
			throw new IllegalArgumentException("Invalid start point : "
					+ startPoint);
		}
		this.startPoint = startPoint;
	}

	private void setMoveLength(int moveLength) {
		if (moveLength < 1 || moveLength > Constants.MAX_DIE) {
			throw new IllegalArgumentException("Invalid move length : "
					+ moveLength);
		}
		this.moveLength = moveLength;
	}

	public void setHit() {
		this.hasHit = true;
	}

	public boolean hasHit() {
		return this.hasHit;
	}
}