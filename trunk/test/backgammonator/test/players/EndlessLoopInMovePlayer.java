package backgammonator.test.players;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.Player;
import backgammonator.core.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface.
 * This player is timed out.
 */
public class EndlessLoopInMovePlayer extends AbstractTestPlayer {

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice) throws Exception {
		while (true) {
		  System.out.println("=== endlessloop");
		}
	}

	@Override
	public String getName() {
		return "Dummy Player Timedout";
	}
}
