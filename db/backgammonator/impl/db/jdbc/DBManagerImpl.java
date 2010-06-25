package backgammonator.impl.db.jdbc;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import backgammonator.lib.db.AccountsManager;
import backgammonator.lib.db.DBManager;
import backgammonator.util.Debug;

/**
 * Manages basic actions on the Backgammonator database.
 * 
 * @see DBManager
 */
public final class DBManagerImpl implements DBManager {

	private static String currentDB = BACKGAMMONATOR_DB;

	private static DBManager instance = new DBManagerImpl();

	private DBManagerImpl() {
	}

	/**
	 * Retrieves the instance of the {@link DBManager} implementation.
	 */
	public static DBManager getInstance() {
		return instance;
	}

	/**
	 * Makes connection to the MySQL server and selects the specified data base.
	 * Always close the retrieved connection after you are done with using it!
	 * 
	 * @return the {@link Connection} object or <code>null</code> if the
	 *         database is not available.
	 */
	static Connection getDBConnection(String name, boolean select) {
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
			if (select) {
				statement = connection.createStatement();
				statement.execute("USE " + name);
			}
			return connection;
		} catch (Throwable t) {
			Debug.getInstance().error("Error connecting to database",
					Debug.DATABASE, t);
			if (connection != null) try {
				if (!connection.isClosed()) connection.close();
			} catch (SQLException ignored) {
			}
			return null;
		} finally {
			if (statement != null) try {
				statement.close();
			} catch (SQLException ignored) {
			}
		}

	}

	/**
	 * Makes connection to the MySQL server and selects the current
	 * backgammonator data base.
	 * 
	 * @see #getDBConnection(String, boolean)
	 */
	static Connection getDBConnection() {
		return getDBConnection(currentDB, true);
	}

	/**
	 * Creates the Backgammonator database.
	 * 
	 * @param name the name of the database name.
	 * @throws IllegalStateException if database access error occurs.
	 */
	public void createDB(String name) {
		Connection connection = getDBConnection(name, false);
		if (connection == null) {
			throw new IllegalStateException("Cannot connect to DB!");
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
			// try to drop the database if it exists
			try {
				statement.execute("DROP DATABASE " + name);
			} catch (Exception e) {
				// database does not exist
			}
			statement.executeUpdate("CREATE DATABASE " + name);
			statement.execute("USE " + name);
			currentDB = name; // set the current database
			statement.executeUpdate("CREATE TABLE Account ( "
					+ "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
					+ "username varchar(32) NOT NULL UNIQUE, "
					+ "password varchar(32) NOT NULL, "
					+ "email varchar(64) NOT NULL, "
					+ "isadmin bool NOT NULL, " + "first varchar(32), "
					+ "last varchar(32))");
			statement
					.execute("INSERT INTO Account (username, password, isadmin, email, first, last) "
							+ "VALUES ('admin', '"
							+ MD5("admin")
							+ "', 1, 'admin@admin', 'admin', 'admin')");
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

	private static String MD5(String pass) {
		try {
			MessageDigest m = null;
			m = MessageDigest.getInstance("MD5");
			byte[] data = pass.getBytes();
			m.update(data, 0, data.length);
			BigInteger i = new BigInteger(1, m.digest());
			return String.format("%1$032X", i);
		} catch (NoSuchAlgorithmException e) {
			return pass;
		}
	}

	/**
	 * Creates the default data base using {@link #BACKGAMMONATOR_DB} as name.
	 * 
	 * @see #createDB(String)
	 */
	public void createDB() {
		createDB(BACKGAMMONATOR_DB);
	}

	/**
	 * Drops the database with the specified name.
	 * 
	 * @param name of the database name.
	 * @throws IllegalStateException if database access error occurs.
	 */
	public void dropDB(String name) {
		Connection connection = getDBConnection(name, false);
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
	 * Drops the current backammonator data base.
	 * 
	 * @see #dropDB(String)
	 */
	public void dropDB() {
		dropDB(currentDB);
	}

	/**
	 * @see DBManager#getAccountsManager()
	 */
	@Override
	public AccountsManager getAccountsManager() {
		return AccountsManagerImpl.getInstance();
	}

}
