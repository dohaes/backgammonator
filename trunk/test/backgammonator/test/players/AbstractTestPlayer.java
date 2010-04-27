package backgammonator.test.players;

import backgammonator.core.Player;

/**
 * Base implementation of the {@link Player} interface.
 * Provides way to verify the game over status sent to the player.
 */
public abstract class AbstractTestPlayer implements Player {

	private boolean wins;
	
	@Override
	public void gameOver(boolean wins) {
		this.wins = wins;
	}
	
	public boolean wins() {
		return wins;
	}
}
