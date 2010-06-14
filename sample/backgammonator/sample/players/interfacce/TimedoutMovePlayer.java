package backgammonator.sample.players.interfacce;

import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.Player;
import backgammonator.lib.game.PlayerMove;
import backgammonator.util.BackgammonatorConfig;

/**
 * Sample implementation of the {@link Player} interface. This player is timed
 * out.
 */
public class TimedoutMovePlayer extends AbstractSamplePlayer {

	/**
	 * @see Player#getMove(BackgammonBoard, Dice)
	 */
	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice)
			throws Exception {
		Thread.sleep(BackgammonatorConfig.getProperty("backgammonator.game.moveTimeout", 2000) + 200);
		return null;
	}

	/**
	 * @see Player#getName()
	 */
	@Override
	public String getName() {
		return "Sample Player Timedout";
	}
}
