package backgammonator.impl.tournament;

import java.util.List;

import backgammonator.game.Player;
import backgammonator.tournament.Tournament;

public class TournamentManager {
	public static Tournament newTournament(List<Player> players) {
		return new TournamentImpl(players);
	}
}