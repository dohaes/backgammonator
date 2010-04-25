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
		this.setCount(0);
		this.setColor(PlayerColor.WHITE);
	}

	/**
	 * Creates point with checkers on it and the color is not in important.
	 * 
	 * @param count
	 */
	public Point(int count) {
		this.setCount(count);
		this.setColor(PlayerColor.WHITE);
	}

	/**
	 * Creates point with checkers on it.
	 * 
	 * @param count
	 * @param color
	 */
	public Point(int count, PlayerColor color) {
		this.setCount(count);
		this.setColor(color);
	}

	/**
	 * @param count
	 */
	public void setCount(int count) {
		if (count < 0 || count > 15) {
			throw new IllegalArgumentException("Illegal count number: " + count);
		}
		this.count = count;
	}

	/**
	 * @return
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param color
	 */
	public void setColor(PlayerColor color) {
		this.color = color;
	}

	/**
	 * @return
	 */
	public PlayerColor getColor() {
		return color;
	}
}