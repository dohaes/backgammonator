package backgammonator.sample.players.interfacce;

import backgammonator.game.BackgammonBoard;
import backgammonator.game.Dice;
import backgammonator.game.Player;
import backgammonator.game.PlayerMove;

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
