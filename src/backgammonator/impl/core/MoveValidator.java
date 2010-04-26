package backgammonator.impl.core;

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
	private static final int HIT_POINT = 25;
	private static final int MAX_POINTS = 24;

	public static boolean validateMove(BackgammonBoard board, PlayerMove move,
			Dice dice) {
		PlayerColor color = move.getPlayerColor();
		if (!validatePlain(move, dice) || !validateHit(board, color, move)) {
			return false;
		}

		CheckerMove m = move.getCheckerMove(1);
		if (!validatePoint(board, color, m)) {
			return false;
		}
		m = move.getCheckerMove(2);
		if (!validatePoint(board, color, m)) {
			return false;
		}
		if (move.isDouble()) {
			m = move.getCheckerMove(3);
			if (!validatePoint(board, color, m)) {
				return false;
			}
			m = move.getCheckerMove(4);
			if (!validatePoint(board, color, m)) {
				return false;
			}
		}

		return validateEmptyMoves(board, move)
				&& validateBearOffs(board, color, move);
	}

	private static boolean validatePlain(PlayerMove move, Dice dice) {
		if (move.isDouble() != dice.isDouble()) {
			return false;
		}
		CheckerMove m1 = move.getCheckerMove(1);
		CheckerMove m2 = move.getCheckerMove(2);
		if (!((m1.getMoveLength() == dice.getDie1() && m2.getMoveLength() == dice
				.getDie2()) || (m1.getMoveLength() == dice.getDie2() && m2
				.getMoveLength() == dice.getDie1()))) {
			return false;
		}

		if (move.isDouble()
				&& (move.getCheckerMove(3).getMoveLength() != dice.getDie1() || move
						.getCheckerMove(4).getMoveLength() != dice.getDie1())) {
			return false;
		}

		return true;
	}

	public static boolean validatePoint(BackgammonBoard board,
			PlayerColor color, CheckerMove move) {
		int from = move.getStartPoint();
		Point fromPoint = board.getPoint(from, color);
		if (fromPoint.getColor() != color || fromPoint.getCount() <= 0) {
			return false;
		}
		int to = from + move.getMoveLength();
		if (to <= MAX_POINTS) {
			Point toPoint = board.getPoint(to, color);
			if (toPoint.getCount() > 0 && toPoint.getColor() != color) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateHit(BackgammonBoard board, PlayerColor color,
			PlayerMove move) {
		for (int i = 1; i <= board.getHits(color); i++) {
			CheckerMove m = move.getCheckerMove(i);
			if (m.getStartPoint() != HIT_POINT) {
				return false;
			}
		}
		return true;
	}

	public static boolean validateBearOffs(BackgammonBoard board,
			PlayerColor color, PlayerMove move) {
		// TODO
		return true;
	}

	public static boolean validateEmptyMoves(BackgammonBoard board,
			PlayerMove move) {
		// TODO
		return true;
	}
}