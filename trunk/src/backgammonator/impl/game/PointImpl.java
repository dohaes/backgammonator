package backgammonator.impl.game;

import backgammonator.core.PlayerColor;
import backgammonator.core.Point;

/**
 * Represents implementation of the {@link Point} interface.
 */
public final class PointImpl implements Point {
	private int count;
	private PlayerColor color;

	/**
	 * Creates empty point.
	 */
	public PointImpl() {
		this.count = 0;
		this.color = PlayerColor.WHITE;
	}

	/**
	 * Creates point with checkers on it.
	 * 
	 * @param count
	 * @param color
	 */
	public PointImpl(int count, PlayerColor color) {
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

	void decrease() {
		count--;
		if (count < 0) {
			throw new IllegalArgumentException("Illegal count number: " + count);
		}
	}

	boolean increase(PlayerColor color) {
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