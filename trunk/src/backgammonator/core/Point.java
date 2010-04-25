package backgammonator.core;

/**
 * Represents the checkers on a point of the board;
 */
public class Point {
	private int count;
	private PlayerColor color;

	/**
	 * Creates empty point.
	 */
	public Point() {
		this.count = 0;
		this.color = PlayerColor.WHITE;
	}

	/**
	 * Creates point with checkers on it.
	 * 
	 * @param count
	 * @param color
	 */
	public Point(int count, PlayerColor color) {
		if (count < 0 || count > 15) {
			throw new IllegalArgumentException("Illegal count number: " + count);
		}
		this.count = count;
		this.color = color;
	}

	public int getCount() {
		return count;
	}

	public PlayerColor getColor() {
		return color;
	}

	public void decrease() {
		count--;
		if (count < 0) {
			throw new IllegalArgumentException("Illegal count number: " + count);
		}
	}

	public boolean increase(PlayerColor color) {
		if (this.count > 0 && !color.equals(this.color)) {
			this.count = 1;
			this.color = color;
			return true;
		}
		count++;
		if (count > 15) {
			throw new IllegalArgumentException("Illegal count number: " + count);
		}
		return false;
	}
}