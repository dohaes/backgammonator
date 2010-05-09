package backgammonator.core;

/**
 * Represents the structure of the backgammon board.
 */
public interface BackgammonBoard {

	/**
	 * Returns the specified point on the board according to the given point
	 * index for the specified player. The two players move their checkers in
	 * opposing directions, from the 24-point towards the 1-point.
	 */
	public Point getPoint(int point);

	/**
	 * Returns the number of hit checkers for the specified player.
	 */
	public int getHits(PlayerColor color);

	/**
	 * Returns the number of born off checkers for the specified color.
	 */
	public int getBornOff(PlayerColor color);

	/**
	 * Returns the color of the current player to move.
	 * If the game is over the last player who made move will be returned.
	 */
	public PlayerColor getCurrentPlayerColor();
}