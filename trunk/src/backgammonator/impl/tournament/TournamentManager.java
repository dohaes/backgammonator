package backgammonator.impl.tournament;

import java.util.List;

import backgammonator.core.Player;

public class TournamentManager {
	public static Tournament newTournament(List<Player> players) {
		return new TournamentImpl(players);
	}
}