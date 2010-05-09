package backgammonator.test.players;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.GameOverStatus;
import backgammonator.core.PlayerStatus;
import backgammonator.core.Player;

/**
 * Base implementation of the {@link Player} interface. Provides way to verify
 * the game over status sent to the player.
 */
public abstract class AbstractTestPlayer implements Player {

	private PlayerStatus status;

	@Override
	public void gameOver(BackgammonBoard board, PlayerStatus status) {
		this.status = status;
	}

	public PlayerStatus getStatus() {
		return status;
	}
}
