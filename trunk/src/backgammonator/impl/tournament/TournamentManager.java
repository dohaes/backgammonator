package backgammonator.impl.tournament;

import java.util.List;

import backgammonator.lib.game.Player;
import backgammonator.lib.tournament.Tournament;

public class TournamentManager {
	public static Tournament newTournament(List<Player> players) {
		return new TournamentImpl(players);
	}
}