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
	/**
	 * Positions 0..23 are the points of the board from the view of the white
	 * player. 24 and 25 are the points of the hit white and black checkers and
	 * 26 and 27 are the points of the born off white and black checkers.
	 */
	private Point[] board;

	public BackgammonBoard() {
		board = new Point[28];
	}

	public Point getPoint(int point, PlayerColor color) {
		if (point <= 0 || point > 24) {
			throw new IllegalArgumentException("Illegal position: " + point);
		}
		return color == PlayerColor.WHITE ? board[point - 1]
				: board[24 - point];
	}

	public int getHits(PlayerColor color) {
		return (color == PlayerColor.WHITE ? board[24] : board[25]).getCount();
	}

	public int getBornOff(PlayerColor color) {
		return (color == PlayerColor.WHITE ? board[24] : board[25]).getCount();
	}
}