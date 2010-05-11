package backgammonator.lib.tournament;

import backgammonator.lib.game.Player;


public interface Tournament {
	public Player start(TournamentConfiguration config);
}