package backgammonator.lib.game;

/**
 * The color of the player.
 */
public enum PlayerColor {
	
	/**
	 * Indicates that the player plays with the white checkers.
	 */
	WHITE,
	
	/**
	 * Indicates that the player plays with the black checkers.
	 */
	BLACK;

	/**
	 * Returns the opposite color.
	 */
	public PlayerColor opposite() {
		return this == WHITE ? BLACK : WHITE;
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return this == WHITE ? "white" : "black";
	}
}