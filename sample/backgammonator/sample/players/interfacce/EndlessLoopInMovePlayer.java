package backgammonator.sample.players.interfacce;

import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.Player;
import backgammonator.lib.game.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface. This player is timed
 * out due to endless loop.
 */
public class EndlessLoopInMovePlayer extends AbstractSamplePlayer {

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice)
			throws Exception {
		while (true) {
			Thread.sleep(200);
			System.out.println("endless loop");
		}
	}

	@Override
	public String getName() {
		return "Sample Player Endless Loop";
	}
}
