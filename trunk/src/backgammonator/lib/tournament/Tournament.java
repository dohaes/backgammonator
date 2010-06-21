package backgammonator.lib.tournament;


/**
 * Realizes starts a tournament realizing the tournaments schemas.
 */
public interface Tournament {

	/**
	 * Executes a tournament with the given configuration.
	 * 
	 * @param config tournament configuration.
	 * @return the winner of the tournament.
	 * @throws TournamentException if an error occurs.
	 */
	public TournamentResult start(TournamentConfiguration config) throws TournamentException;
}