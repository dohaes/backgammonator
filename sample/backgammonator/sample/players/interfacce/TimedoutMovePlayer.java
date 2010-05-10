package backgammonator.sample.players.interfacce;

import backgammonator.game.BackgammonBoard;
import backgammonator.game.Dice;
import backgammonator.game.Player;
import backgammonator.game.PlayerMove;

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
		return "Sample Player Timedout";
	}
}
