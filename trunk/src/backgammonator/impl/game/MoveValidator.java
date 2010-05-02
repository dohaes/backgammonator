package backgammonator.impl.game;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.CheckerMove;
import backgammonator.core.Constants;
import backgammonator.core.Dice;
import backgammonator.core.PlayerColor;
import backgammonator.core.PlayerMove;
import backgammonator.core.Point;

/**
 * Validates the game play. There will be a method - boolean
 * validateMove(Backgammon currB, Move move), which will validate the move of
 * the player according to the current backgammon.
 */
public final class MoveValidator {

	/**
	 * Validates if the move is valid according the board and the dice.
	 * 
	 * @param board
	 *            the board.
	 * @param move
	 *            the move.
	 * @param dice
	 *            the dice.
	 * @return true if the move is valid.
	 */
	public static boolean validateMove(BackgammonBoard board, PlayerMove move,
			Dice dice) {
		// TODO
		if (move.isDouble() != dice.isDouble()) {
			return false;
		}
		CheckerMove m1 = move.getCheckerMove(0);
		CheckerMove m2 = move.getCheckerMove(1);
		if (!((m1.getDie() == dice.getDie1() && m2.getDie() == dice.getDie2()) || (m1
				.getDie() == dice.getDie2() && m2.getDie() == dice.getDie1()))) {
			return false;
		}

		if (move.isDouble()
				&& (move.getCheckerMove(2).getDie() != dice.getDie1() || move
						.getCheckerMove(3).getDie() != dice.getDie1())) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if the move is valid according to the board.
	 * 
	 * @param board
	 *            the board.
	 * @param move
	 *            the move.
	 * @return true if the move is valid according to the board.
	 */
	public static boolean validateMove(BackgammonBoardImpl board,
			CheckerMove move) {
		if (move.isUnavailableMove()) {
			if (!validateEmptyMoves(board, move)) {
				return false;
			}
			return true;
		}
		if (!validatePoint(board, move)) {
			return false;
		}
		if (!validateHits(board, move)) {
			return false;
		}
		if (!validateBearOffs(board, move)) {
			return false;
		}
		return true;
	}

	private static boolean validatePoint(BackgammonBoardImpl board,
			CheckerMove move) {
		PlayerColor color = board.getCurrentPlayerColor();
		if (move.isReenterHitChecker()) {
			if (board.getHits(color) <= 0) {
				return false;
			}
		} else {
			Point source = board.getPoint(move.getStartPoint());
			if (source.getColor() != color || source.getCount() <= 0) {
				return false;
			}
		}
		if (!move.isBearingOff()) {
			Point dest = board.getPoint(move.getEndPoint());
			if (dest.getCount() > 1 && dest.getColor() != color) {
				return false;
			}
		}
		return true;
	}

	private static boolean validateHits(BackgammonBoard board, CheckerMove move) {
		return !((board.getHits(board.getCurrentPlayerColor()) > 0 && !move
				.isReenterHitChecker()));
	}

	private static boolean validateBearOffs(BackgammonBoard board,
			CheckerMove move) { // TODO
		if (move.isBearingOff()) {
			for (int i = 0; i <= Constants.POINTS_COUNT - Constants.MAX_DIE; i++) {
				if (board.getPoint(i).getCount() > 0
						&& board.getPoint(i).getColor() == board
								.getCurrentPlayerColor()) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean validateEmptyMoves(BackgammonBoard board,
			CheckerMove move) {
		if (!move.isUnavailableMove())
			return true;
		int die = move.getDie();
		PlayerColor color = board.getCurrentPlayerColor();

		if (board.getHits(color) > 0) {
			return board.getPoint(die).getCount() > 1
					&& board.getPoint(die).getColor() == color;
		}

		boolean isBearingOff = true;
		for (int i = 0; i <= Constants.POINTS_COUNT - Constants.MAX_DIE; i++) {
			if (board.getPoint(i).getCount() > 0
					&& board.getPoint(i).getColor() == board
							.getCurrentPlayerColor()) {
				isBearingOff = false;
				Point dest = board.getPoint(i + die);
				if (dest.getCount() < 2
						|| (dest.getCount() > 1 && dest.getColor() == color)) {
					return false;
				}
			}
		}

		for (int i = Constants.POINTS_COUNT - Constants.MAX_DIE + 1; i <= Constants.POINTS_COUNT
				- die; i++) {
			if (board.getPoint(i).getCount() > 0
					&& board.getPoint(i).getColor() == color) {// TODO
				return false;
			}
		}

		if (isBearingOff) {
			for (int i = Constants.POINTS_COUNT - die + 1; i <= Constants.POINTS_COUNT; i++) {
				if (board.getPoint(i).getCount() > 0
						&& board.getPoint(i).getColor() == color) {
					return false;
				}
			}
		}

		return true;
	}
}