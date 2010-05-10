package backgammonator.core;

import backgammonator.impl.tournament.TournamentConfiguration;

public interface Tournament {
	public Player start(TournamentConfiguration config);
}