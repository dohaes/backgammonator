package backgammonator.impl.db;

import backgammonator.impl.db.jdbc.DBManagerImpl;
import backgammonator.lib.db.DBManager;

/**
 * Accesses the database related API implementation for the currently used java
 * persistence API.
 */
public final class DB {

	/**
	 * Retrieves the {@link DBManager} instance.
	 */
	public static DBManager getDBManager() {
		//currently only jdbc is used
		return DBManagerImpl.getInstance();
	}
}
