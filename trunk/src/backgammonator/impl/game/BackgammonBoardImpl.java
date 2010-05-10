package backgammonator.impl.game;

import java.util.ArrayList;

import backgammonator.game.BackgammonBoard;
import backgammonator.game.CheckerMove;
import backgammonator.game.Constants;
import backgammonator.game.Dice;
import backgammonator.game.GameOverStatus;
import backgammonator.game.PlayerColor;
import backgammonator.game.PlayerMove;
import backgammonator.game.Point;

/**
 * Represents implementation of the {@link BackgammonBoard} interface.
 */
public final class BackgammonBoardImpl implements BackgammonBoard {

	private static final int HIT_WHITE = 24;
	private static final int HIT_BLACK = 25;
	private static final int BORN_WHITE = 26;
	private static final int BORN_BLACK = 27;
	private static final int BOARD_SIZE = 28;

	/**
	 * Positions 0..23 are the points of the board from the view of the white
	 * player. 24 and 25 are the points of the hit white and black checkers and
	 * 26 and 27 are the points of the born off white and black checkers.
	 */
	private PointImpl[] board;
	private PlayerColor currentColor;
	private ArrayList<CheckerMove> movesThatHit;
	private MoveValidator validator;

	/**
	 * Creates default board initiated with the default playing positions.
	 */
	public BackgammonBoardImpl() {
		movesThatHit = new ArrayList<CheckerMove>(4);
		board = new PointImpl[BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			board[i] = new PointImpl();
		}
		reset();
		validator = new MoveValidator(this);
	}

	void reset() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			board[i].updatePoint(0, PlayerColor.WHITE);
		}
		board[23].updatePoint(2, PlayerColor.WHITE);
		board[18].updatePoint(5, PlayerColor.BLACK);
		board[16].updatePoint(3, PlayerColor.BLACK);
		board[12].updatePoint(5, PlayerColor.WHITE);
		board[11].updatePoint(5, PlayerColor.BLACK);
		board[7].updatePoint(3, PlayerColor.WHITE);
		board[5].updatePoint(5, PlayerColor.WHITE);
		board[0].updatePoint(2, PlayerColor.BLACK);
		// FIXME: Current color should be white!
		currentColor = PlayerColor.BLACK;

	}

	public Point getPoint(int point) {
		if (point < 1 || point > Constants.POINTS_COUNT) {
			throw new IllegalArgumentException("Illegal position: " + point);
		}
		return getPoint0(point);
	}

	public int getHits(PlayerColor color) {
		return (color == PlayerColor.WHITE ? board[HIT_WHITE]
				: board[HIT_BLACK]).getCount();
	}

	public int getBornOff(PlayerColor color) {
		return (color == PlayerColor.WHITE ? board[BORN_WHITE]
				: board[BORN_BLACK]).getCount();
	}

	public PlayerColor getCurrentPlayerColor() {
		return currentColor;
	}

	/**
	 * Switches the current player on turn.
	 */
	void switchPlayer() {
		currentColor = currentColor.opposite();
	}

	/**
	 * If the player wins the type of victory that the player has earned (OK,
	 * DOUBLE or TRIPLE). Otherwise the method returns EXCEPTION.
	 */
	GameOverStatus getWinStatus() {
		if (getBornOff(currentColor) != Constants.CHECKERS_COUNT) {
			return GameOverStatus.EXCEPTION;
		}
		if (getBornOff(currentColor.opposite()) == 0) {
			for (int i = 1; i <= Constants.MAX_DIE; i++) {
				if (getPoint0(i).getColor() == currentColor.opposite()
						&& getPoint0(i).getCount() > 0) {
					return GameOverStatus.TRIPLE;
				}
			}
			return GameOverStatus.DOUBLE;
		}
		return GameOverStatus.NORMAL;
	}

	private PointImpl getPoint0(int point) {
		return currentColor == PlayerColor.WHITE ? board[point - 1]
				: board[Constants.POINTS_COUNT - point];
	}

	private void increaseHits(PlayerColor color) {
		board[color == PlayerColor.WHITE ? HIT_WHITE : HIT_BLACK]
				.increase(color);
	}

	private void decreaseHits(PlayerColor color) {
		board[color == PlayerColor.WHITE ? HIT_WHITE : HIT_BLACK].decrease();
	}

	private void increaseBornOffs(PlayerColor color) {
		board[color == PlayerColor.WHITE ? BORN_WHITE : BORN_BLACK]
				.increase(color);
	}

	private void decreaseBornOffs(PlayerColor color) {
		board[color == PlayerColor.WHITE ? BORN_WHITE : BORN_BLACK].decrease();
	}

	/**
	 * Update the board with the given move.
	 * 
	 * @param move
	 *            the move.
	 * @param dice
	 *            the dice corresponding to the move.
	 * @return true if the move is valid.
	 */
	boolean makeMove(PlayerMove move, Dice dice) {
		try {
			if (!validator.validateMove(move, dice)) {
				return false;
			}
			CheckerMove m1 = move.getCheckerMove(0);
			if (!makeMove(m1)) {
				return false;
			}
			CheckerMove m2 = move.getCheckerMove(1);
			if (!makeMove(m2)) {
				revertMove(m1);
				return false;
			}
			if (move.isDouble()) {
				CheckerMove m3 = move.getCheckerMove(2);
				if (!makeMove(m3)) {
					revertMove(m2);
					revertMove(m1);
					return false;
				}
				CheckerMove m4 = move.getCheckerMove(3);
				if (!makeMove(m4)) {
					revertMove(m3);
					revertMove(m2);
					revertMove(m1);
					return false;
				}
			}
			return true;
		} finally {
			movesThatHit.clear();
		}
	}

	private boolean makeMove(CheckerMove move) {
		if (!validator.validateMove(move)) {
			return false;
		}
		if (move.isUnavailableMove()) {
			return true;
		}

		if (move.isReenterHitChecker()) {
			decreaseHits(currentColor);
		} else {
			getPoint0(move.getStartPoint()).decrease();
		}

		if (move.isBearingOff()) {
			increaseBornOffs(currentColor);
		} else {
			PointImpl dest = getPoint0(move.getEndPoint());
			if (dest.increase(currentColor)) {
				increaseHits(currentColor.opposite());
				movesThatHit.add(move);
			}
		}
		return true;
	}

	private void revertMove(CheckerMove move) {
		if (move.isUnavailableMove()) {
			return;
		}

		getPoint0(move.getStartPoint()).increase(currentColor);

		if (move.isBearingOff()) {
			decreaseBornOffs(currentColor);
		} else {
			PointImpl dest = getPoint0(move.getEndPoint());
			dest.decrease();
			if (movesThatHit.contains(move)) {
				dest.increase(currentColor.opposite());
				decreaseHits(currentColor.opposite());
			}
		}
	}
}