package backgammonator.core;

/**
 * Represents the structure of the backgammon board.
 */
public interface BackgammonBoard {

	/**
	 * Returns the specified point on the board according to the given point
	 * index for the current player.
	 */
	public Point getPoint(int point);

	/**
	 * Returns the hits for the specified color.
	 */
	public int getHits(PlayerColor color);

	/**
	 * Returns the born off for the specified color.
	 */
	public int getBornOff(PlayerColor color);

	/**
	 * Returns the color of the current player to move.
	 */
	public PlayerColor getCurrentPlayerColor();
}