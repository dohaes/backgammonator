package backgammonator.test.players;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.Player;
import backgammonator.core.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface.
 * This player is timed out.
 */
public class TimedoutMovePlayer extends AbstractSamplePlayer {

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice) throws Exception {
		Thread.sleep(2100);
		return null;
	}

	@Override
	public String getName() {
		return "Dummy Player Timedout";
	}
}
