package backgammonator.core;

/**
 * This class is used for the representation of a single move of a player. Every
 * move is represented by player color(it will be accessed with getters ) and
 * two(or four for double) checker moves (they will be accessed by int
 * getCheckerMove(int index) method).
 */

public class PlayerMove {

	private CheckerMove[] checkerMoves;
	PlayerColor playerColor;

	/**
	 * Creates new PlayerMove object with the given arguments
	 * @param checkerMoves array of CheckerMove objects
	 * @param playerColor 
	 */
	public PlayerMove(CheckerMove[] checkerMoves, PlayerColor playerColor) {
		if (checkerMoves == null) {
			throw new NullPointerException("checkerMoves is null");
		}
		if (checkerMoves.length != 2 && checkerMoves.length != 4) {
			throw new IllegalArgumentException("Invalid length for checkerMoves : " + checkerMoves.length);
		}
		this.checkerMoves = new CheckerMove[checkerMoves.length];
		System.arraycopy(checkerMoves, 0, this.checkerMoves, 0, checkerMoves.length);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public CheckerMove getCheckerMove(int index) {
		return this.checkerMoves[index];
	}

	public PlayerColor getPlayerColor() {
		return playerColor;
	}

	public boolean isDouble() {
		return this.checkerMoves.length == 4;
	}

}
