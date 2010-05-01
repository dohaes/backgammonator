package backgammonator.impl.game;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.CheckerMove;
import backgammonator.core.Constants;
import backgammonator.core.Dice;
import backgammonator.core.PlayerColor;
import backgammonator.core.PlayerMove;
import backgammonator.core.Point;

/**
 * Represents implementation of the {@link BackgammonBoard} interface.
 */
public class BackgammonBoardImpl implements BackgammonBoard {
	
	private static final int HIT_WHITE = 24;
	private static final int HIT_BLACK = 25;
	private static final int BORN_WHITE = 26;
	private static final int BORN_BLACK = 27;

	/**
	 * Positions 0..23 are the points of the board from the view of the white
	 * player. 24 and 25 are the points of the hit white and black checkers and
	 * 26 and 27 are the points of the born off white and black checkers.
	 */
	private PointImpl[] board;

	/**
	 * Shows the color of the player that has to move on the board.
	 */
	private PlayerColor currentColor = PlayerColor.BLACK;

	public BackgammonBoardImpl() {
		board = new PointImpl[28];
		for (int i = 0; i < 28; i++) {
			board[i] = new PointImpl();
		}
		board[0] = new PointImpl(2, PlayerColor.WHITE);
		board[5] = new PointImpl(5, PlayerColor.BLACK);
		board[7] = new PointImpl(3, PlayerColor.BLACK);
		board[11] = new PointImpl(5, PlayerColor.WHITE);
		board[12] = new PointImpl(5, PlayerColor.BLACK);
		board[16] = new PointImpl(3, PlayerColor.WHITE);
		board[18] = new PointImpl(5, PlayerColor.WHITE);
		board[23] = new PointImpl(2, PlayerColor.BLACK);
	}

	public Point getPoint(int point) {
		return getPoint0(point);
	}

	public int getHits(PlayerColor color) {
		return (color.equals(PlayerColor.WHITE) ? board[HIT_WHITE]
				: board[HIT_BLACK]).getCount();
	}

	public int getBornOff(PlayerColor color) {
		return (color.equals(PlayerColor.WHITE) ? board[BORN_WHITE]
				: board[BORN_BLACK]).getCount();
	}

	public PlayerColor getCurrentPlayerColor() {
		return currentColor;
	}

	void setPoint(int point, PlayerColor color, int count) {
		if (point <= 0 || point > Constants.POINTS_COUNT) {
			throw new IllegalArgumentException("Illegal position: " + point);
		}
		if (count < 0 || count > Constants.CHECKERS_COUNT) {
			throw new IllegalArgumentException("Illegal count: " + count);
		}
		if (color.equals(PlayerColor.WHITE)) {
			board[point - 1] = new PointImpl(count, color);
		} else {
			board[Constants.POINTS_COUNT - point] = new PointImpl(count, color);
		}
	}

	void setHits(PlayerColor color, int count) {
		if (count < 0 || count > Constants.CHECKERS_COUNT) {
			throw new IllegalArgumentException("Illegal count: " + count);
		}
		if (color.equals(PlayerColor.WHITE)) {
			board[HIT_WHITE] = new PointImpl(count, color);
		} else {
			board[HIT_BLACK] = new PointImpl(count, color);
		}
	}

	void setBornOff(PlayerColor color, int count) {
		if (count < 0 || count > Constants.CHECKERS_COUNT) {
			throw new IllegalArgumentException("Illegal count: " + count);
		}
		if (color.equals(PlayerColor.WHITE)) {
			board[BORN_WHITE] = new PointImpl(count, color);
		} else {
			board[BORN_BLACK] = new PointImpl(count, color);
		}
	}

	private PointImpl getPoint0(int point) {
		if (point <= 0 || point > Constants.POINTS_COUNT) {
			throw new IllegalArgumentException("Illegal position: " + point);
		}
		return currentColor.equals(PlayerColor.WHITE) ? board[point - 1]
				: board[Constants.POINTS_COUNT - point];
	}

	void switchPlayer() {
		currentColor = currentColor.opposite();
	}

	boolean makeMove(PlayerMove move, Dice dice) throws Exception {
		if (!MoveValidator.validateMove(this, move, dice)) {
			return false;
		}
		if (!makeMove(move.getCheckerMove(0))) {
			return false;
		}
		if (!makeMove(move.getCheckerMove(1))) {
			revertMove(move.getCheckerMove(0));
			return false;
		}
		if (move.isDouble()) {
			if (!makeMove(move.getCheckerMove(2))) {
				revertMove(move.getCheckerMove(1));
				revertMove(move.getCheckerMove(0));
				return false;
			}
			if (!makeMove(move.getCheckerMove(3))) {
				revertMove(move.getCheckerMove(2));
				revertMove(move.getCheckerMove(1));
				revertMove(move.getCheckerMove(0));
				return false;
			}
		}

		return true;
	}

	private void revertMove(CheckerMove checkerMove) {
		int from = checkerMove.getStartPoint();
		if (from == 25) {
			return;
		}
		int to = checkerMove.getStartPoint() + checkerMove.getMoveLength();
		PointImpl source = getPoint0(from);
		source.increase(currentColor);
		if (to > Constants.POINTS_COUNT) {
			setBornOff(currentColor, getBornOff(currentColor) - 1);
		} else {
			PointImpl dest = getPoint0(to);
			dest.decrease();
			if (checkerMove.hasHit()) {
				dest.increase(currentColor.opposite());
				setHits(currentColor.opposite(), getHits(currentColor) - 1);
			}
		}
	}

	private boolean makeMove(CheckerMove checkerMove) {
		int from = checkerMove.getStartPoint();
		if (from == 25) {
			return true;
		}
		if (!MoveValidator.validateMove(this, checkerMove)) {
			return false;
		}
		int to = checkerMove.getStartPoint() + checkerMove.getMoveLength();
		if (from == 0) {
			setHits(currentColor, getHits(currentColor) - 1);
		} else {
			PointImpl source = getPoint0(from);
			source.decrease();
		}
		if (to > Constants.POINTS_COUNT) {
			setBornOff(currentColor, getBornOff(currentColor) + 1);
		} else {
			PointImpl dest = getPoint0(to);
			if (dest.increase(currentColor)) {
				setHits(currentColor.opposite(), getHits(currentColor) + 1);
				checkerMove.setHit();
			}
		}
		return true;
	}
}