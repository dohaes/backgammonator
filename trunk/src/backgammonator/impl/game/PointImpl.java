package backgammonator.impl.game;

import backgammonator.lib.game.Constants;
import backgammonator.lib.game.PlayerColor;
import backgammonator.lib.game.Point;

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
	 * @param count the count on the point.
	 * @param color the color of the checkers.
	 */
	public PointImpl(int count, PlayerColor color) {
		if (count < 0 || count > Constants.CHECKERS_COUNT) {
			throw new IllegalArgumentException("Illegal count number: " + count);
		}
		this.count = count;
		this.color = color;
	}

	/**
	 * @see Point#getCount()
	 */
	@Override
	public int getCount() {
		return count;
	}

	/**
	 * @see Point#getColor()
	 */
	@Override
	public PlayerColor getColor() {
		return color;
	}

	/**
	 * Decreases the number of checkers on the point.
	 */
	void decrease() {
		if (count <= 0) {
			throw new IllegalArgumentException("Illegal decrease operation.");
		}
		count--;
	}

	/**
	 * Increases the number of checkers on the point.
	 * 
	 * @param color the color of the placed checker.
	 * @return true if there has been hit checker of the opposite color on the
	 *         point.
	 */
	boolean increase(PlayerColor color) {
		if (count >= Constants.CHECKERS_COUNT) {
			throw new IllegalArgumentException("Illegal increase operation.");
		}
		if (this.count > 0 && color != this.color) {
			this.count = 1;
			this.color = color;
			return true;
		}
		this.count++;
		this.color = color;
		return false;
	}

	/**
	 * Updates the point's data.
	 * 
	 * @param count the count on the point.
	 * @param color the color of the checkers.
	 */
	void updatePoint(int count, PlayerColor color) {
		if (count < 0 || count > Constants.CHECKERS_COUNT) {
			throw new IllegalArgumentException("Illegal count number: " + count);
		}
		this.count = count;
		this.color = color;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return color + " " + count;
	}
}