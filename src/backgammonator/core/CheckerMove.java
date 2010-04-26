package backgammonator.core;

/**
 * Represents the move of a single checker.
 */
public final class CheckerMove {

	private int startPoint;
	private int moveLength;

	/**
	 * Create CheckerMove object with given start point and move lenght
	 */

	public CheckerMove(int startPoint, int moveLength) {
		setStartPoint(startPoint);
		setMoveLength(moveLength);
	}

	/**
	 * Getter for the position from which the checker is moved
	 */
	public int getStartPoint() {
		return startPoint;
	}

	/**
	 * Setter for the position from which the checker is moved
	 * @throws IllegalArgumentException if startPoint is smaller than 1 or greater than 24
	 */
	public void setStartPoint(int startPoint) {
		if (startPoint > 24 || startPoint < 1) {
			throw new IllegalArgumentException("Invalid start point : " + startPoint);
		}
		this.startPoint = startPoint;
	}

	/**
	 * Getter for the length that the checker is moved
	 */
	public int getMoveLength() {
		return moveLength;
	}

	/**
	 * Setter for the length that the checker is moved
	 * @throws IllegalArgumentException if moveLength is negative or greater than 6
	 */
	public void setMoveLength(int moveLength) {
		if (moveLength < 1 || moveLength > 6) {
			throw new IllegalArgumentException("Invalid move length : " + moveLength);
		}
		this.moveLength = moveLength;
	}

}
