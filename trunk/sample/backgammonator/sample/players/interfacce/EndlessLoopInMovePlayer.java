package backgammonator.sample.players.interfacce;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.Player;
import backgammonator.core.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface.
 * This player is timed out.
 */
public class EndlessLoopInMovePlayer extends AbstractSamplePlayer {

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice) throws Exception {
		while (true) {
			Thread.sleep(200);
		    System.out.println("endless loop");
		}
	}

	@Override
	public String getName() {
		return "Dummy Player Timedout";
	}
}
