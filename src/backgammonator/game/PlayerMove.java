package backgammonator.game;

/**
 * This class is used for the representation of a single move of a player. Every
 * move is represented by player color(it will be accessed with getters ) and
 * two(or four for double) checker moves (they will be accessed by int
 * getCheckerMove(int index) method).
 */
public final class PlayerMove {

	private CheckerMove[] checkerMoves;

	/**
	 * Creates new PlayerMove object with the given arguments.
	 * 
	 * @param checkerMoves
	 *            array of CheckerMove objects.
	 * @throws IllegalArgumentException
	 *             if the given array represents invalid moves.
	 */
	public PlayerMove(CheckerMove[] checkerMoves) {
		if (checkerMoves == null) {
			throw new NullPointerException("checkerMoves is null");
		}
		if (checkerMoves.length != Constants.DICE_COUNT
				&& checkerMoves.length != Constants.DOUBLE_DICE_COUNT) {
			throw new IllegalArgumentException(
					"Invalid length for checkerMoves : " + checkerMoves.length);
		}
		this.checkerMoves = new CheckerMove[checkerMoves.length];
		System.arraycopy(checkerMoves, 0, this.checkerMoves, 0,
				checkerMoves.length);
	}

	/**
	 * Gets the corresponding {@link CheckerMove} object from the array.
	 * 
	 * @param index
	 *            the index of the {@link CheckerMove} object in the array. its
	 *            value should be between 0 and 1 or between 0 and 3 depending
	 *            if the die is double.
	 * @return the corresponding {@link CheckerMove}
	 * @throws IllegalArgumentException
	 *             if the index is out of bounds.
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
		return this.checkerMoves.length == Constants.DOUBLE_DICE_COUNT;
	}
}