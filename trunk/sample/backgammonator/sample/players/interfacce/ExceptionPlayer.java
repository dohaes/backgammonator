package backgammonator.sample.players.interfacce;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.Player;
import backgammonator.core.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface.
 * This player throws exception in its getMove method.
 */
public class ExceptionPlayer extends AbstractSamplePlayer {

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice) throws Exception {
		throw new Exception("Test exeption");
	}

	@Override
	public String getName() {
		return "Sample Player Exception";
	}

}
