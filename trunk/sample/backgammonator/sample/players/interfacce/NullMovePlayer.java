package backgammonator.sample.players.interfacce;

import backgammonator.game.BackgammonBoard;
import backgammonator.game.Dice;
import backgammonator.game.Player;
import backgammonator.game.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface.
 * This player returns null for a move.
 */
public class NullMovePlayer extends AbstractSamplePlayer {

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice) throws Exception {
		return null;
	}

	@Override
	public String getName() {
		return "Sample Player NPE";
	}

}
