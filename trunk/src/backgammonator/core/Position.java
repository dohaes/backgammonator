package backgammonator.core;

/**
 * Represents the pieces on a position of the board;
 */
public class Position {
	private int count;
	private PlayerColor color;

	/**
	 * Creates empty position.
	 */
	public Position() {
		this.setCount(0);
		this.setColor(PlayerColor.WHITE);
	}

	/**
	 * Creates position with pieces on it.
	 * 
	 * @param count
	 * @param color
	 */
	public Position(int count, PlayerColor color) {
		this.setCount(count);
		this.setColor(color);
	}

	/**
	 * @param count
	 */
	public void setCount(int count) {
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