package backgammonator.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the structure of the backgammon board. GameManager objects know
 * about it and can update it so it shows the current configuration of the
 * pieces on the board. Each BackgammonBoard object is associated with a
 * generator of int numbers which will represent the dices.
 */
public final class BackgammonBoard {

	private List<Move> movesHistory;

	/**
	 * Position 0..23 are the positions of the board starting from the farthest
	 * position of the whites. 24 and 25 are the positions of the killed whites
	 * and blacks and 26 and 27 are the positions of the rescued whites and
	 * blacks.
	 */
	private Position[] board;

	public BackgammonBoard() {
		movesHistory = new ArrayList<Move>();
		board = new Position[28];
	}
}