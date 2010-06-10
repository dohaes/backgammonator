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

	/**
	 * @param type the type.
	 */
	public TournamentConfiguration(TournamentType type) {
		this.type = type;
		this.setLogMoves(true);
		this.setPlainRate(true);
		this.setGroupsCount(2);
		this.setGamesCount(5);
		this.setInvalidGamePoints(1);
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
	 * @param plainRate if it is plane rate.
	 */
	public void setPlainRate(boolean plainRate) {
		this.plainRate = plainRate;
	}

	/**
	 * @return if it is plane rate.
	 */
	public boolean isPlainRate() {
		return plainRate;
	}

	/**
	 * @param groupsCount the groups count.
	 */
	public void setGroupsCount(int groupsCount) {
		if (groupsCount <= 1) {
			throw new IllegalArgumentException(
					"Groups count must be 2 or more, was : " + groupsCount);
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
	 */
	public void setGamesCount(int gamesCount) {
		if (gamesCount <= 0) {
			throw new IllegalArgumentException(
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
	 */
	public void setInvalidGamePoints(int invalidGamePoints) {
		if (invalidGamePoints < 0) {
			throw new IllegalArgumentException(
					"Invalid game points must be positive number, was : "
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
}