package backgammonator.core;

/**
 * Validates the game play. There will be a method - boolean
 * validateMove(Backgammon currB, Move move), which will validate the move of
 * the player according to the current backgammon.
 */
public class MoveValidator {

	public boolean validateMove(BackgammonBoard board, Move move, Dice dice) {
		validatePlain(move, dice);
		return true;
	}

	private boolean validatePlain(Move move, Dice dice) {
		if (move.isDouble() != dice.isDouble()) {
			return false;
		}
		CheckerMove m = move.getMove(1);
		if (move.isDouble()) {
			
		}
		return true;
	}
}