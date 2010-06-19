package backgammonator.impl.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import backgammonator.lib.db.Account;
import backgammonator.util.Debug;

/**
 * Implementation of the {@link Account} interface.
 */
final class AccountImpl implements Account {

	private boolean exists = false;
	private boolean isAdmin = false;

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;

	/**
	 * Mask to keep track of which are the the fields to update when
	 * {@link Account#store()} method is called.
	 */
	private int updateMask = 0;

	private static final int PASSWORD = 8; // first bit
	private static final int FIRST = 4; // second bit
	private static final int LAST = 2; // third bit
	private static final int EMAIL = 1; // fourth bit

	private static final int UPDATE = PASSWORD | FIRST | LAST | EMAIL;

	/**
	 * Constructs new Account object corresponding to a non existing account.
	 */
	AccountImpl(String username) {
		this.username = username;
		this.isAdmin = false;
		// mark this account as not found in the database
		this.exists = false;
	}

	/**
	 * Constructs new Account object corresponding to an existing account.
	 */
	AccountImpl(String username, String password, String firstName,
			String lastName, String email, boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.isAdmin = isAdmin;
		// mark this account as found in the database
		this.exists = true;
	}

	/**
	 * @see Account#exists()
	 */
	@Override
	public boolean exists() {
		return exists;
	}

	/**
	 * @see Account#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/**
	 * @see Account#getFirstName()
	 */
	@Override
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @see Account#getLastname()
	 */
	@Override
	public String getLastname() {
		return lastName;
	}

	/**
	 * @see Account#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * @see Account#getUsername()
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * @see Account#setEmail(String)
	 */
	@Override
	public void setEmail(String email) {
		if (email == null) {
			throw new NullPointerException("Given email is null!");
		}

		this.email = email;
		updateMask |= EMAIL;
	}

	/**
	 * @see Account#setFirstName(String)
	 */
	@Override
	public void setFirstName(String firstName) {
		if (firstName == null) {
			throw new NullPointerException("Given first name is null!");
		}

		this.firstName = firstName;
		updateMask |= FIRST;
	}

	/**
	 * @see Account#setLastname(String)
	 */
	@Override
	public void setLastname(String lastName) {
		if (lastName == null) {
			throw new NullPointerException("Given last name is null!");
		}

		this.lastName = lastName;
		updateMask |= LAST;
	}

	/**
	 * @see Account#setPassword(String)
	 */
	@Override
	public void setPassword(String password) {
		if (password == null) {
			throw new NullPointerException("Given password is null!");
		}

		this.password = password;
		updateMask |= PASSWORD;
	}

	/**
	 * @see Account#store()
	 */
	@Override
	public void store() {
		Connection connection = DBManager.getDBConnection();
		if (connection == null) {
			throw new IllegalStateException("Cannot connect to DB!");
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();

			if (exists) {
				// edit the existing account
				if ((updateMask & UPDATE) == 0) return; // nothing to update

				if ((updateMask & PASSWORD) != 0) {
					statement.executeUpdate("UPDATE Account SET password='"
							+ password + "'" + " WHERE username='" + username
							+ "'");
				}
				if ((updateMask & FIRST) != 0) {
					statement.executeUpdate("UPDATE Account SET first='"
							+ firstName + "'" + " WHERE username='" + username
							+ "'");
				}
				if ((updateMask & LAST) != 0) {
					statement.executeUpdate("UPDATE Account SET last='"
							+ lastName + "'" + " WHERE username='" + username
							+ "'");
				}
				if ((updateMask & EMAIL) != 0) {
					statement.executeUpdate("UPDATE Account SET email='"
							+ email + "'" + " WHERE username='" + username
							+ "'");
				}
			} else {
				// create new account in the data base
				StringBuffer keys = new StringBuffer(
						"(username, password, isadmin");
				StringBuffer values = new StringBuffer("('" + username + "', "
						+ "'" + password + "', '0'");

				if ((updateMask & FIRST) != 0) {
					keys.append(", first");
					values.append(", '" + firstName + "'");
				}
				if ((updateMask & LAST) != 0) {
					keys.append(", last");
					values.append(", '" + lastName + "'");
				}
				if ((updateMask & EMAIL) != 0) {
					keys.append(", email");
					values.append(", '" + email + "'");
				}

				keys.append(')');
				values.append(')');

				statement.executeUpdate("INSERT INTO Account "
						+ keys.toString() + " VALUES " + values.toString());
				this.exists = true;
			}

		} catch (SQLException e) {
			Debug.getInstance().error("Unexpected Exception was thrown",
					Debug.DATABASE, e);
			throw new IllegalStateException(e.getMessage());
		} finally {
			updateMask = 0; // reset the mask
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
	 * @see Account#delete()
	 */
	@Override
	public void delete() {
		if (!exists) throw new IllegalStateException("Account for user "
				+ username + " does not exist!");
		Connection connection = DBManager.getDBConnection();
		if (connection == null) {
			throw new IllegalStateException("Cannot connect to DB!");
		}
		Statement statement = null;
		try {

			statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM Account WHERE username='"
					+ username + "'");

		} catch (SQLException e) {
			Debug.getInstance().error("Unexpected Exception was thrown",
					Debug.DATABASE, e);
			throw new IllegalStateException(e.getMessage());
		} finally {
			this.exists = false; // mark as deleted anyway
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
	 * @see Account#isAdmin()
	 */
	@Override
	public boolean isAdmin() {
		return isAdmin;
	}
}