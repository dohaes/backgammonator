package backgammonator.core;

/**
 * Represents the move of a single checker.
 */
public final class CheckerMove {
	
	private int from;
	private int to;

	/**
	 * Getter for the position from which the checker is moved
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * Setter for the position from which the checker is moved
	 */
	public void setFrom(int from) {
		this.from = from;
	}

	/**
	 * Getter for the position to which the checker is moved
	 */
	public int getTo() {
		return to;
	}

	/**
	 * Setter for the position to which the checker is moved
	 */
	public void setTo(int to) {
		this.to = to;
	}
	
	


}
