package backgammonator.lib.tournament;

import backgammonator.lib.game.Player;

/**
 * @author georgi.b.andreev
 */
public interface Tournament {

	/**
	 * @param config config.
	 * @return winner.
	 */
	public Player start(TournamentConfiguration config);
}