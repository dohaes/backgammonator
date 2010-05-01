package backgammonator.core;

/**
 * This class is used for the representation of a single move of a player. Every
 * move is represented by player color(it will be accessed with getters ) and
 * two(or four for double) checker moves (they will be accessed by int
 * getCheckerMove(int index) method).
 */
public class PlayerMove {

	private CheckerMove[] checkerMoves;

	/**
	 * Creates new PlayerMove object with the given arguments
	 * 
	 * @param checkerMoves
	 *            array of CheckerMove objects
	 * @throws IllegalArgumentException
	 *             if the given array represents invalid moves
	 */
	public PlayerMove(CheckerMove[] checkerMoves) {
		if (checkerMoves == null) {
			throw new NullPointerException("checkerMoves is null");
		}
		if (checkerMoves.length != 2 && checkerMoves.length != 4) {
			throw new IllegalArgumentException(
					"Invalid length for checkerMoves : " + checkerMoves.length);
		}
		if (checkerMoves.length == 4
				&& (checkerMoves[0].getMoveLength() != checkerMoves[1]
						.getMoveLength()
						|| checkerMoves[0].getMoveLength() != checkerMoves[2]
								.getMoveLength() || checkerMoves[0]
						.getMoveLength() != checkerMoves[3].getMoveLength())) {
			throw new IllegalArgumentException("Invalid length for double : "
					+ //
					checkerMoves[0].getMoveLength() + ", "
					+ checkerMoves[1].getMoveLength() + ", "
					+ //
					checkerMoves[2].getMoveLength() + ", "
					+ checkerMoves[3].getMoveLength());
		}
		this.checkerMoves = new CheckerMove[checkerMoves.length];
		System.arraycopy(checkerMoves, 0, this.checkerMoves, 0,
				checkerMoves.length);
	}

	/**
	 * Gets the corresponding {@link CheckerMove} object from the array.
	 * 
	 * @param index the index of the {@link CheckerMove} object in the array.
	 * 		        its value should be between 0 and 1 or 3.
	 * @return the corresponding {@link CheckerMove}
	 * @throws IllegalArgumentException if the index is out of bounds.
	 */
	public CheckerMove getCheckerMove(int index) {
		if (index < 0 || index >= checkerMoves.length) {
			throw new IllegalArgumentException("Invalid index : " + index
					+ " : " + checkerMoves.length);
		}
		return this.checkerMoves[index];
	}

	/**
	 * Checks if the move is double.
	 * 
	 * @return true if the move is double, or false otherwise.
	 */
	public boolean isDouble() {
		return this.checkerMoves.length == 4;
	}
}