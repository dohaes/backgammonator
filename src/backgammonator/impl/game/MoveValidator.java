package backgammonator.impl.game;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.CheckerMove;
import backgammonator.core.Dice;
import backgammonator.core.PlayerColor;
import backgammonator.core.PlayerMove;
import backgammonator.core.Point;

/**
 * Validates the game play. There will be a method - boolean
 * validateMove(Backgammon currB, Move move), which will validate the move of
 * the player according to the current backgammon.
 */
public class MoveValidator {
	private static final int MAX_POINTS = 24;

	public static boolean validateMove(BackgammonBoard board, PlayerMove move,
			Dice dice) {
		if (move.isDouble() != dice.isDouble()) {
			return false;
		}
		CheckerMove m1 = move.getCheckerMove(0);
		CheckerMove m2 = move.getCheckerMove(1);
		if (!((m1.getMoveLength() == dice.getDie1() && m2.getMoveLength() == dice
				.getDie2()) || (m1.getMoveLength() == dice.getDie2() && m2
				.getMoveLength() == dice.getDie1()))) {
			return false;
		}

		if (move.isDouble()
				&& (move.getCheckerMove(2).getMoveLength() != dice.getDie1() || move
						.getCheckerMove(3).getMoveLength() != dice.getDie1())) {
			return false;
		}

		return true;
	}

	public static boolean validateMove(BackgammonBoardImpl board,
			CheckerMove move) {
		PlayerColor color = board.getCurrentPlayerColor();
		if (!validatePoint(board, move, color)) {
			return false;
		}
		if (!validateHits(board, move, color)) {
			return false;
		}
		if (!validateBearOffs(board, move, color)) {
			return false;
		}
		if (!validateEmptyMoves(board, move, color)) {
			return false;
		}
		return true;
	}

	private static boolean validatePoint(BackgammonBoardImpl board,
			CheckerMove move, PlayerColor color) {
		int from = move.getStartPoint();
		if (from == 0) {
			if (board.getHits(color) <= 0) {
				return false;
			}
		} else {
			Point fromPoint = board.getPoint(from);
			if (fromPoint.getColor() != color || fromPoint.getCount() <= 0) {
				return false;
			}
		}
		int to = from + move.getMoveLength();
		if (to <= MAX_POINTS) {
			Point toPoint = board.getPoint(to);
			if (toPoint.getCount() > 1 && toPoint.getColor() != color) {
				return false;
			}
		}
		return true;
	}

	private static boolean validateHits(BackgammonBoard board,
			CheckerMove move, PlayerColor color) {
		if (board.getHits(color) > 0 && move.getStartPoint() != 0) {
			return false;
		}
		return true;
	}

	private static boolean validateBearOffs(BackgammonBoard board,
			CheckerMove move, PlayerColor color) {
		int from = move.getStartPoint();
		int to = from + move.getMoveLength();
		if (to <= 0) {
			for (int i = 0; i <= 18; i++) {
				if (board.getPoint(i).getCount() > 0
						&& board.getPoint(i).getColor().equals(color)) {
					return false;
				}
			}
		}
		return true;
	}

	private static boolean validateEmptyMoves(BackgammonBoard board,
			CheckerMove move, PlayerColor color) {
		// TODO
		return true;
	}
}