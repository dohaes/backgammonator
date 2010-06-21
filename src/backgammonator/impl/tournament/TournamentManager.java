package backgammonator.impl.tournament;

import java.util.List;

import backgammonator.lib.game.Player;
import backgammonator.lib.tournament.Tournament;
import backgammonator.lib.tournament.TournamentException;

/**
 * This class is used to create new tournaments.
 */
public class TournamentManager {

	/**
	 * Creates new tournament from the given players.
	 * 
	 * @param players the players that will take part in the tournament.
	 * @return the tournament that can be executed.
	 * @throws TournamentException if an error occurs.
	 */
	public static Tournament newTournament(List<Player> players)
			throws TournamentException {
		return new TournamentImpl(players);
	}
}