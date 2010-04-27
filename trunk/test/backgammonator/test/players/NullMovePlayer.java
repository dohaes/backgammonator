package backgammonator.test.players;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.Player;
import backgammonator.core.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface.
 * This player returns null for a move.
 */
public class NullMovePlayer implements Player {

	public boolean wins;
	
	@Override
	public void gameOver(boolean wins) {
		this.wins = wins;
	}

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice) throws Exception {
		return null;
	}

	@Override
	public String getName() {
		return "Dummy Player NPE";
	}

}
