package backgammonator.sample.players.interfacce;

import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;

/**
 * Base implementation of the {@link Player} interface. Provides way to verify
 * the game over status sent to the player.
 */
public abstract class AbstractSamplePlayer implements Player {

	private boolean wins;
	private GameOverStatus status;

	@Override
	public void gameOver(BackgammonBoard board, boolean wins,
			GameOverStatus status) {
		this.wins = wins;
		this.status = status;
	}

	public GameOverStatus getStatus() {
		return status;
	}

	public boolean wins() {
		return wins;
	}
}
