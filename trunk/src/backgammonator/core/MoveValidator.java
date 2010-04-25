package backgammonator.core;

/**
 * Validates the game play. There will be a method - boolean
 * validateMove(Backgammon currB, Move move), which will validate the move of
 * the player according to the current backgammon.
 */
public class MoveValidator {

	public static boolean validateMove(BackgammonBoard board, PlayerMove move, Dice dice) {
		return validatePlain(move, dice);
	}

	private static boolean validatePlain(PlayerMove move, Dice dice) {
		if (move.isDouble() != dice.isDouble()) {
			return false;
		}
		CheckerMove m1 = move.getMove(1);
		CheckerMove m2 = move.getMove(2);
		if (!((m1.getMoveLength() == dice.getDie1() && m2.getMoveLength() == dice
				.getDie2()) || (m1.getMoveLength() == dice.getDie2() && m2
				.getMoveLength() == dice.getDie1()))) {
			return false;
		}

		if (move.isDouble()
				&& (move.getMove(3).getMoveLength() != dice.getDie1() || move
						.getMove(4).getMoveLength() != dice.getDie1())) {
			return false;
		}
		return true;
	}
}