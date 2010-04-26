package backgammonator.core;

/**
 * Represents a point of the board including number of checkers and color of the
 * checkers.
 */
public interface Point {

	/**
	 * Returns the number of checkers on that point
	 */
	public int getCount();

	/**
	 * Returns the color of the checkers on that point.
	 */
	public PlayerColor getColor();

}