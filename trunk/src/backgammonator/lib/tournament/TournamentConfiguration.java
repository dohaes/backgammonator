package backgammonator.lib.tournament;

/**
 * @author georgi.b.andreev
 */
public class TournamentConfiguration {
	private TournamentType type;
	private boolean logMoves;
	private boolean plainRate;
	private int groupsCount;
	private int gamesCount;
	private int invalidGamePoints;
	private int moveTimeout;

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
		this.setUsePlainRate(true);
		this.setGroupsCount(2);
		this.setGamesCount(3);
		this.setInvalidGamePoints(1);
		this.setMoveTimeout(2);
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
	 * @param usePlainRate if it is plane rate.
	 */
	public void setUsePlainRate(boolean usePlainRate) {
		this.plainRate = usePlainRate;
	}

	/**
	 * @return if it is plane rate.
	 */
	public boolean usePlainRate() {
		return plainRate;
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
	 * @param invalidGamePoints the points for invalid game.
	 * @throws TournamentException if invalid game points is invalid.
	 */
	public void setInvalidGamePoints(int invalidGamePoints)
			throws TournamentException {
		if (invalidGamePoints < 0) {
			throw new TournamentException(
					"Invalid game points must be a positive number, was : "
							+ invalidGamePoints);
		}
		this.invalidGamePoints = invalidGamePoints;
	}

	/**
	 * @return the points for invalid game.
	 */
	public int getInvalidGamePoints() {
		return invalidGamePoints;
	}

	/**
	 * @return the type.
	 */
	public TournamentType getTournamentType() {
		return type;
	}

	/**
	 * @return the move timeout.
	 */
	public int getMoveTimeout() {
		return moveTimeout;
	}

	/**
	 * @param moveTimeout the move timeout.
	 * @throws TournamentException if move timeout is invalid.
	 */
	public void setMoveTimeout(int moveTimeout) throws TournamentException {
		if (moveTimeout < 0) {
			throw new TournamentException(
					"Invalid move timeout must be a positive number, was : "
							+ moveTimeout);
		}
		this.moveTimeout = moveTimeout;
	}
}