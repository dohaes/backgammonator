package backgammonator.test.players;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.Player;
import backgammonator.core.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface.
 * This player returns null for a move.
 */
public class ExceptionPlayer extends AbstractSamplePlayer {

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice) throws Exception {
		throw new Exception("Test exeption");
	}

	@Override
	public String getName() {
		return "Dummy Player Exception";
	}

}
