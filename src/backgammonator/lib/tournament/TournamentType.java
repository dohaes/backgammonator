package backgammonator.lib.tournament;

/**
 * @author georgi.b.andreev
 */
public enum TournamentType {
	/**
	 * Direct eliminations. Must have 2^n number of players.
	 */
	ELIMINATIONS,
	/**
	 * First creates groups and then makes eliminations from the winners. Must
	 * have 2^n groups.
	 */
	GROUPS,
	/**
	 * All players fight each other.
	 */
	BATTLE;
}
