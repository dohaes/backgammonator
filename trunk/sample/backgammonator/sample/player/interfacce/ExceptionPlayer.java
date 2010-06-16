package backgammonator.sample.player.interfacce;

import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.Player;
import backgammonator.lib.game.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface. This player throws
 * exception in its getMove method.
 */
public class ExceptionPlayer extends AbstractSamplePlayer {

	/**
	 * @see Player#getMove(BackgammonBoard, Dice)
	 */
	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice)
			throws Exception {
		throw new Exception("Test exeption");
	}

	/**
	 * @see Player#getName()
	 */
	@Override
	public String getName() {
		return "Sample Player Exception";
	}

}
