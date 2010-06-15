package backgammonator.lib.tournament;


/**
 * @author georgi.b.andreev
 */
public class TournamentConfiguration {
	private TournamentType type;
	private boolean logMoves;
	private int groupsCount;
	private int gamesCount;

	/**
	 * Creates default tournament configuration of the given type.
	 * 
	 * @param type the type of the tournament.
	 * @throws TournamentException if the configuration is invalid.
	 */
	public TournamentConfiguration(TournamentType type)
			throws TournamentException {
		this.type = type;
		this.setLogMoves(true);
		this.setGroupsCount(2);
		this.setGamesCount(3);
	}

	/**
	 * @param logMoves if logging should be activated.
	 */
	public void setLogMoves(boolean logMoves) {
		this.logMoves = logMoves;
	}

	/**
	 * @return if logging should be activated.
	 */
	public boolean isLogMoves() {
		return logMoves;
	}

	/**
	 * @param groupsCount the groups count.
	 * @throws TournamentException if groups count is invalid.
	 */
	public void setGroupsCount(int groupsCount) throws TournamentException {
		if (groupsCount <= 1 || (groupsCount & (groupsCount - 1)) != 0) {
			throw new TournamentException(
					"Groups count must be power of 2, was : " + groupsCount);
		}
		this.groupsCount = groupsCount;
	}

	/**
	 * @return the groups count.
	 */
	public int getGroupsCount() {
		return groupsCount;
	}

	/**
	 * @param gamesCount the games count.
	 * @throws TournamentException if games count is invalid.
	 */
	public void setGamesCount(int gamesCount) throws TournamentException {
		if (gamesCount <= 0) {
			throw new TournamentException(
					"Games count must be 1 or more, was : " + gamesCount);
		}
		this.gamesCount = gamesCount;
	}

	/**
	 * @return the games count.
	 */
	public int getGamesCount() {
		return gamesCount;
	}

	/**
	 * @return the type.
	 */
	public TournamentType getTournamentType() {
		return type;
	}
}