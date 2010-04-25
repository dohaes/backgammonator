package backgammonator.core;

/**
 * Represents the structure of the backgammon board. GameManager objects know
 * about it and can update it so it shows the current configuration of the
 * pieces on the board. Each BackgammonBoard object is associated with a
 * generator of numbers which will represent the dices.
 */
public final class BackgammonBoard {
	private static final int HIT_WHITE = 24;
	private static final int HIT_BLACK = 25;
	private static final int BORN_WHITE = 26;
	private static final int BORN_BLACK = 27;

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
		return color.equals(PlayerColor.WHITE) ? board[point - 1]
				: board[24 - point];
	}

	public int getHits(PlayerColor color) {
		return (color.equals(PlayerColor.WHITE) ? board[HIT_WHITE]
				: board[HIT_BLACK]).getCount();
	}

	public int getBornOff(PlayerColor color) {
		return (color.equals(PlayerColor.WHITE) ? board[BORN_WHITE]
				: board[BORN_BLACK]).getCount();
	}

	public void setPoint(int point, PlayerColor color, int count) {
		if (point <= 0 || point > 24) {
			throw new IllegalArgumentException("Illegal position: " + point);
		}
		if (count < 0 || count > 15) {
			throw new IllegalArgumentException("Illegal count: " + count);
		}
		if (color.equals(PlayerColor.WHITE)) {
			board[point - 1] = new Point(count, color);
		} else {
			board[24 - point] = new Point(count, color);
		}
	}

	public void setHits(PlayerColor color, int count) {
		if (count < 0 || count > 15) {
			throw new IllegalArgumentException("Illegal count: " + count);
		}
		if (color.equals(PlayerColor.WHITE)) {
			board[HIT_WHITE] = new Point(count, color);
		} else {
			board[HIT_BLACK] = new Point(count, color);
		}
	}

	public void setBornOff(PlayerColor color, int count) {
		if (count < 0 || count > 15) {
			throw new IllegalArgumentException("Illegal count: " + count);
		}
		if (color.equals(PlayerColor.WHITE)) {
			board[BORN_WHITE] = new Point(count, color);
		} else {
			board[BORN_BLACK] = new Point(count, color);
		}
	}

	public void makeMove(PlayerMove move) {
		CheckerMove m = null;
		PlayerColor color = move.getPlayerColor();
		m = move.getMove(1);
		makeMove(color, m.getStartPoint(), m.getStartPoint()
				+ m.getMoveLength());
		m = move.getMove(2);
		makeMove(color, m.getStartPoint(), m.getStartPoint()
				+ m.getMoveLength());
		if (move.isDouble()) {
			m = move.getMove(3);
			makeMove(color, m.getStartPoint(), m.getStartPoint()
					+ m.getMoveLength());
			m = move.getMove(4);
			makeMove(color, m.getStartPoint(), m.getStartPoint()
					+ m.getMoveLength());
		}
	}

	private void makeMove(PlayerColor color, int from, int to) {
		Point source = getPoint(from, color);
		if (to > 24) {
			to = color.equals(PlayerColor.WHITE) ? BORN_WHITE : BORN_BLACK;
		}
		Point dest = getPoint(to, color);
		source.decrease();
		if (dest.increase(color)) {
			setHits(color.oposite(), getHits(color));
		}
	}
}