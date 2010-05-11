package backgammonator.sample.players.interfacce;

import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.Player;
import backgammonator.lib.game.PlayerMove;

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