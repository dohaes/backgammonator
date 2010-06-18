package backgammonator.impl.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import backgammonator.lib.db.Account;
import backgammonator.util.Debug;

/**
 * Accesses user accounts data stored in the database.
 */
public final class AccountsManager {

	/**
	 * Gets an {@link Account} object for the specified username. If there is no
	 * record in the database for the specified username then the
	 * {@link Account#exists()} method will return false when invoked on the
	 * returned from this method account object.
	 * 
	 * @throws IllegalStateException if database access error occurs.
	 */
	public static Account getAccount(String username) {
		Connection connection = DBManager.getDBConnection();
		if (connection == null) {
			throw new IllegalStateException("Cannot connect to DB!");
		}
		Statement statement = null;
		ResultSet result = null;
		try {
			statement = connection.createStatement();
			result = statement
					.executeQuery("SELECT * FROM Account WHERE username="
							+ username);
			
			if (!result.next()) {
				// we have no record for this username in the database
				return new AccountImpl(username);
			}
			// so we have record in the database
			return new AccountImpl(username, result.getString("password"),
					result.getString("firs"), result.getString("last"), result
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

}
