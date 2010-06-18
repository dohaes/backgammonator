package backgammonator.impl.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import backgammonator.util.Debug;

/**
 * Manages basic actions on the Backgammonator database.
 */
public final class DBManager {
	
	/**
	 * Default name for the backgammonator system database.
	 */
	public final static String BACKGAMMONATOR_DB = "Backgammonator";

	/**
	 * Makes connection to the database. Always close the retrieved connection
	 * after you are done with using it!
	 * 
	 * @return the {@link Connection} object or <code>null</code> if the
	 *         database is not available.
	 */
	public static Connection getDBConnection(String name) {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306", "root", "root");
			if (connection.isClosed()) {
				Debug.getInstance().error(
						"Retrieved connection is closed : " + connection,
						Debug.DATABASE, null);
				return null;
			}
			statement = connection.createStatement();
			statement.execute("USE " + name);
			return connection;
		} catch (Throwable t) {
			Debug.getInstance().error("Error connecting to database",
					Debug.DATABASE, t);
			if (connection != null) try {
				if (!connection.isClosed()) connection.close();
			} catch (SQLException ignored) {}
			return null;
		} finally {
			if (statement != null) try {
				statement.close();
			} catch (SQLException ignored) {}
		}

	}
	
	/**
	 * Connects to the default data base using {@link #BACKGAMMONATOR_DB} as name.
	 * @see #getDBConnection(String)
	 */
	public static Connection getDBConnection() {
		return getDBConnection(BACKGAMMONATOR_DB);
	}

	/**
	 * Creates the Backgammonator database.
	 * 
	 * @param name the name of the database name.
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
			statement.executeUpdate("DROP DATABASE " + name);
			statement.executeUpdate("CREATE DATABASE " + name);
			statement.execute("USE " + name);
			statement.executeUpdate("CREATE TABLE Account ( "
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
	
	/**
	 * Creates the default data base using {@link #BACKGAMMONATOR_DB} as name.
	 * @see #createDB(String)
	 */
	public static void createDB() {
		
	}
	
	/**
	 * Drops the Backgammonator database.
	 * 
	 * @param name of the database name.
	 * @throws IllegalStateException if database access error occurs.
	 */
	public static void dropDB(String name) {
		Connection connection = DBManager.getDBConnection();
		if (connection == null) {
			throw new IllegalStateException("Cannot connect to DB!");
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute("DROP DATABASE " + name);
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
	
	/**
	 * Drops the default data base using {@link #BACKGAMMONATOR_DB} as name.
	 * @see #dropDB(String)
	 */
	public static void dropDB() {
		
	}

}
