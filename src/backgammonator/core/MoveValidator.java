package backgammonator.core;

/**
 * Validates the game play. There will be a method - boolean
 * validateMove(Backgammon currB, Move move), which will validate the move of
 * the player according to the current backgammon.
 */
public class MoveValidator {

	public static final int MAX_POINTS = 24;

	public static boolean validateMove(BackgammonBoard board, PlayerMove move,
			Dice dice) {
		if (!validatePlain(move, dice)) {
			return false;
		}

		PlayerColor c = move.getPlayerColor();
		CheckerMove m = move.getCheckerMove(1);
		if (!validatePoint(board, c, m)) {
			return false;
		}

		m = move.getCheckerMove(2);

		if (move.isDouble()) {
			m = move.getCheckerMove(3);
			m = move.getCheckerMove(4);
		}

		return validateEmptyMoves(board, move) && validateHit(board, move)
				&& validateBearOffs(board, move);
	}

	public static boolean validateEmptyMoves(BackgammonBoard board,
			PlayerMove move) {
		// TODO
		return true;
	}

	public static boolean validateHit(BackgammonBoard board, PlayerMove move) {
		// TODO
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

	public static boolean validateBearOffs(BackgammonBoard board,
			PlayerMove move) {
		// TODO
		return true;
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
}