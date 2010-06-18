package backgammonator.impl.db;

import java.sql.Connection;
import java.sql.DriverManager;

import backgammonator.util.Debug;

/**
 * Manages connecting to the Backgammonator database.
 */
public final class DBUtils {

	/**
	 * Makes connection to the database.
	 * Always close the retrieved connection after you are done with using it!
	 * @return the {@link Connection} object or <code>null</code> if the
	 *         database is not available.
	 */
	static Connection getDBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306", "root", "root");
			if (connection.isClosed()) {
				Debug.getInstance().error("Retrieved connection is closed : " + connection, Debug.DATABASE, null);
				return null;
			}
			return connection;
		} catch (Throwable t) {
			Debug.getInstance().error("Error connecting to database", Debug.DATABASE, t);
			return null;
		}
		
	}

}
