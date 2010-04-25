package backgammonator.core;

/**
 * Represents the move of a single checker.
 */
public final class CheckerMove {
	
	private int startPosistion;
	private int moveLength;
	

	/**
	 * Getter for the position from which the checker is moved
	 */
	public int getStartPoint() {
		return startPosistion;
	}

	/**
	 * Setter for the position from which the checker is moved
	 */
	public void setStartPosistion(int startPosistion) {
		this.startPosistion = startPosistion;
	}

	/**
	 * Getter for the length that the checker is moved
	 */
	public int getMoveLength() {
		return moveLength;
	}

	/**
	 * Setter for  the length that the checker is moved
	 */
	public void setMoveLength(int moveLength) {
		this.moveLength = moveLength;
	}
	


}
