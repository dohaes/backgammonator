package backgammonator.impl.game;

import java.util.List;

import backgammonator.core.Player;
import backgammonator.core.Tournament;

public class TournamentManager {
	public static Tournament newTournament(List<Player> players) {
		return new TournamentImpl(players);
	}
}