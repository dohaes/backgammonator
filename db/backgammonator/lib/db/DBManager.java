package backgammonator.lib.db;

/**
 * Manages basic actions on the Backgammonator database.
 */
public interface DBManager {

	/**
	 * Default name for the backgammonator system database.
	 */
	public final static String BACKGAMMONATOR_DB = "backgammonator";

	/**
	 * Retrieves the {@link AccountsManager} instance.
	 */
	AccountsManager getAccountsManager();

	/**
	 * Creates the Backgammonator database.
	 * 
	 * @param name the name of the database name.
	 * @throws IllegalStateException if database access error occurs.
	 */
	void createDB(String name);

	/**
	 * Creates the default data base using {@link #BACKGAMMONATOR_DB} as name.
	 * 
	 * @see #createDB(String)
	 */
	void createDB();

	/**
	 * Drops the database with the specified name.
	 * 
	 * @param name of the database name.
	 * @throws IllegalStateException if database access error occurs.
	 */
	void dropDB(String name);

	/**
	 * Drops the current backammonator data base.
	 * 
	 * @see #dropDB(String)
	 */
	void dropDB();

}
