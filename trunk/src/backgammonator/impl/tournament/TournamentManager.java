package backgammonator.impl.tournament;

import java.util.List;

import backgammonator.lib.game.Player;
import backgammonator.lib.tournament.Tournament;

/**
 * @author georgi.b.andreev
 */
public class TournamentManager {
	/**
	 * @param players the players.
	 * @return the tournament.
	 */
	public static Tournament newTournament(List<Player> players) {
		return new TournamentImpl(players);
	}
}