package backgammonator.impl.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import backgammonator.util.Debug;

/**
 * Manages connecting to the Backgammonator database.
 */
public final class DBManager {

	/**
	 * Makes connection to the database. Always close the retrieved connection
	 * after you are done with using it!
	 * 
	 * @return the {@link Connection} object or <code>null</code> if the
	 *         database is not available.
	 */
	public static Connection getDBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306", "root", "root");
			if (connection.isClosed()) {
				Debug.getInstance().error(
						"Retrieved connection is closed : " + connection,
						Debug.DATABASE, null);
				return null;
			}
			return connection;
		} catch (Throwable t) {
			Debug.getInstance().error("Error connecting to database",
					Debug.DATABASE, t);
			return null;
		}

	}

	/**
	 * Creates the Backgammonator database.
	 * 
	 * @param name of the database name
	 * @throws IllegalStateException if database access error occurs.
	 */
	public static void createDB(String name) {
		Connection connection = DBManager.getDBConnection();
		if (connection == null) {
			throw new IllegalStateException("Cannot connect to DB!");
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute("DROP DATABASE " + name);
			statement.execute("CREATE DATABASE " + name);
			statement.execute("USE " + name);
			statement.execute("CREATE TABLE Account ( "
					+ "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
					+ "username varchar(32) NOT NULL UNIQUE, "
					+ "password varchar(32) NOT NULL, "
					+ "email varchar(64) NOT NULL, "
					+ "isadmin bool NOT NULL, " + "first varchar(32), "
					+ "last varchar(32))");
		} catch (SQLException e) {
			Debug.getInstance().error("Unexpected Exception was thrown",
					Debug.DATABASE, e);
			throw new IllegalStateException(e.getMessage());
		} finally {
			try {
				if (statement != null) statement.close();
				connection.close();
			} catch (SQLException e) {
				Debug.getInstance().error("Error closing connection",
						Debug.DATABASE, e);
			}
		}
	}

}
