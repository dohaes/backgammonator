package backgammonator.core;

/**
 * The color of the player.
 */
public enum PlayerColor {
	WHITE, BLACK;

	public PlayerColor oposite() {
		return this == WHITE ? BLACK : WHITE;
	}
	
	public String toString() {
		return this == WHITE ? "white" : "black";
	}
}