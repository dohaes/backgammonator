package backgammonator.core;

/**
 * Represents the structure of the backgammon board. Game objects know
 * about it and can update it so it shows the current configuration of the
 * pieces on the board. Each BackgammonBoard object is associated with a
 * generator of numbers which will represent the dices.
 */
public interface BackgammonBoard {
	public static final int MAX_POINTS = 24;
	public static final int MAX_CHECKERS = 15;
	public static final int HIT_WHITE = 24;
	public static final int HIT_BLACK = 25;
	public static final int BORN_WHITE = 26;
	public static final int BORN_BLACK = 27;

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