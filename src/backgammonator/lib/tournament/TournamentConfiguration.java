package backgammonator.lib.tournament;

public class TournamentConfiguration {
	private TournamentType type;
	private boolean logMoves;
	private boolean plainRate;
	private int groupsCount;
	private int gamesCount;
	private int invalidGamePoints;

	public TournamentConfiguration(TournamentType type) {
		this.type = type;
		this.setLogMoves(true);
		this.setPlainRate(true);
		this.setGroupsCount(2);
		this.setGamesCount(5);
		this.setInvalidGamePoints(1);
	}

	public void setLogMoves(boolean logMoves) {
		this.logMoves = logMoves;
	}

	public boolean isLogMoves() {
		return logMoves;
	}

	public void setPlainRate(boolean plainRate) {
		this.plainRate = plainRate;
	}

	public boolean isPlainRate() {
		return plainRate;
	}

	public void setGroupsCount(int groupsCount) {
		if (groupsCount <= 1) {
			throw new IllegalArgumentException(
					"Groups count must be 2 or more, was : " + groupsCount);
		}
		this.groupsCount = groupsCount;
	}

	public int getGroupsCount() {
		return groupsCount;
	}

	public void setGamesCount(int gamesCount) {
		if (gamesCount <= 0) {
			throw new IllegalArgumentException(
					"Games count must be 1 or more, was : " + gamesCount);
		}
		this.gamesCount = gamesCount;
	}

	public int getGamesCount() {
		return gamesCount;
	}

	public void setInvalidGamePoints(int invalidGamePoints) {
		if (invalidGamePoints < 0) {
			throw new IllegalArgumentException(
					"Invalid game points must be positive number, was : "
							+ invalidGamePoints);
		}
		this.invalidGamePoints = invalidGamePoints;
	}

	public int getInvalidGamePoints() {
		return invalidGamePoints;
	}

	public TournamentType getTournamentType() {
		return type;
	}
}