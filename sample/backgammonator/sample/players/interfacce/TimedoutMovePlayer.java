package backgammonator.sample.players.interfacce;

import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.Player;
import backgammonator.lib.game.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface. This player is timed
 * out.
 */
public class TimedoutMovePlayer extends AbstractSamplePlayer {

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice)
			throws Exception {
		Thread.sleep(2100);
		return null;
	}

	@Override
	public String getName() {
		return "Sample Player Timedout";
	}
}
