package backgammonator.lib.game;

/**
 * The color of the player.
 */
public enum PlayerColor {
	WHITE, BLACK;

	/**
	 * Returns the opposite color.
	 */
	public PlayerColor opposite() {
		return this == WHITE ? BLACK : WHITE;
	}

	public String toString() {
		return this == WHITE ? "white" : "black";
	}
}