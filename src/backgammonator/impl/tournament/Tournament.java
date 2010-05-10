package backgammonator.impl.tournament;

import backgammonator.core.Player;

public interface Tournament {
	public Player start(TournamentConfiguration config);
}