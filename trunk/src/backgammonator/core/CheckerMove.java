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
		this.startPoint = startPoint;
		this.moveLength = moveLength;
	}

	/**
	 * Getter for the position from which the checker is moved
	 */
	public int getStartPoint() {
		return startPoint;
	}

	/**
	 * Setter for the position from which the checker is moved
	 */
	public void setStartPoint(int startPosistion) {
		this.startPoint = startPosistion;
	}

	/**
	 * Getter for the length that the checker is moved
	 */
	public int getMoveLength() {
		return moveLength;
	}

	/**
	 * Setter for the length that the checker is moved
	 */
	public void setMoveLength(int moveLength) {
		this.moveLength = moveLength;
	}

}
