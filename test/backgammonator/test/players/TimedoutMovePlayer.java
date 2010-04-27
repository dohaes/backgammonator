package backgammonator.test.players;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.Player;
import backgammonator.core.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface.
 * This player is timed out.
 */
public class TimedoutMovePlayer extends AbstractTestPlayer {

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice) throws Exception {
		Thread.sleep(1000);
		return null;
	}

	@Override
	public String getName() {
		return "Dummy Player Timedout";
	}
}