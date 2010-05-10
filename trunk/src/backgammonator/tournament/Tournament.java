package backgammonator.tournament;

import backgammonator.game.Player;


public interface Tournament {
	public Player start(TournamentConfiguration config);
}