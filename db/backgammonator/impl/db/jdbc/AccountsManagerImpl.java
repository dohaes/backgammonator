package backgammonator.impl.db.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import backgammonator.lib.db.Account;
import backgammonator.lib.db.AccountsManager;
import backgammonator.util.Debug;

/**
 * Accesses user accounts data stored in the database.
 * 
 * @see AccountsManager
 */
public final class AccountsManagerImpl implements AccountsManager {

	private static AccountsManager instance = new AccountsManagerImpl();

	private AccountsManagerImpl() {
	}

	/**
	 * Retrieves the {@link AccountsManager} implementation instance.
	 */
	public static AccountsManager getInstance() {
		return instance;
	}

	/**
	 * Gets an {@link Account} object for the specified username. If there is no
	 * record in the database for the specified username then the
	 * {@link Account#exists()} method will return false when invoked on the
	 * returned from this method account object.
	 * 
	 * @throws IllegalStateException if database access error occurs.
	 */
	public Account getAccount(String username) {
		Connection connection = DBManagerImpl.getDBConnection();
		if (connection == null) {
			throw new IllegalStateException("Cannot connect to DB!");
		}
		Statement statement = null;
		ResultSet result = null;
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT * FROM Account WHERE username=" + "'"
							+ username + "'");

			if (!result.next()) {
				// we have no record for this username in the database
				return new AccountImpl(username);
			}
			// so we have record in the database
			return new AccountImpl(username, result.getString("password"),
					result.getString("first"), result.getString("last"), result
							.getString("email"), result.getBoolean("isadmin"));
		} catch (SQLException e) {
			Debug.getInstance().error("Unexpected Exception was thrown",
					Debug.DATABASE, e);
			throw new IllegalStateException(e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				connection.close();
			} catch (SQLException e) {
				Debug.getInstance().error("Error closing connection",
						Debug.DATABASE, e);
			}
		}
	}

	/**
	 * @see AccountsManager#getAllAccounts()
	 */
	@Override
	public List<Account> getAllAccounts() {
		Connection connection = DBManagerImpl.getDBConnection();
		if (connection == null) {
			throw new IllegalStateException("Cannot connect to DB!");
		}
		Statement statement = null;
		ResultSet result = null;
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT * FROM Account");

			List<Account> all = new ArrayList<Account>(10);
			while (result.next()) {
				// add each record from the database
				all.add(new AccountImpl(result.getString("username"), result.getString("password"),
						result.getString("first"), result.getString("last"), result
								.getString("email"), result.getBoolean("isadmin")));
			}
			return all;
		} catch (SQLException e) {
			Debug.getInstance().error("Unexpected Exception was thrown",
					Debug.DATABASE, e);
			throw new IllegalStateException(e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				connection.close();
			} catch (SQLException e) {
				Debug.getInstance().error("Error closing connection",
						Debug.DATABASE, e);
			}
		}
	}

	/**
	 * @see AccountsManager#isUnique(String)
	 */
	@Override
	public boolean isUnique(String email) {
		Connection connection = DBManagerImpl.getDBConnection();
		if (connection == null) {
			throw new IllegalStateException("Cannot connect to DB!");
		}
		Statement statement = null;
		ResultSet result = null;
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT * FROM Account WHERE email=" + "'"
							+ email + "'");

			if (!result.next()) {
				// we have no record for this email in the database
				return true;
			}
			// so we have record in the database with this email
			return false;
		} catch (SQLException e) {
			Debug.getInstance().error("Unexpected Exception was thrown",
					Debug.DATABASE, e);
			throw new IllegalStateException(e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				connection.close();
			} catch (SQLException e) {
				Debug.getInstance().error("Error closing connection",
						Debug.DATABASE, e);
			}
		}
	}

}
