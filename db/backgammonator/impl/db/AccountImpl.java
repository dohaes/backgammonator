package backgammonator.impl.db;

import backgammonator.lib.db.Account;

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
	 * Constructs new Account object corresponding to a non existing account.
	 */
	AccountImpl(String username) {
		this.username = username;
		this.isAdmin = false;
		//mark this account as not found in the database
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
		//mark this account as found in the database
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
		this.email = email;
	}

	/**
	 * @see Account#setFirstName(String)
	 */
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @see Account#setLastname(String)
	 */
	@Override
	public void setLastname(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @see Account#setPassword(String)
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @see Account#store()
	 */
	@Override
	public boolean store() {
		// TODO implement
		if (exists) {
			// edit the existing account
		} else {
			// create new account in the data base
			// .....
			this.exists = false;
		}
		return false;
	}

	/**
	 * @see Account#delete()
	 */
	@Override
	public boolean delete() {
		if (!exists) throw new IllegalStateException("Account for user "
				+ username + " does not exist!");
		// TODO implement
		// ......
		this.exists = false;
		return false;
	}

	/**
	 * @see Account#isAdmin()
	 */
	@Override
	public boolean isAdmin() {
		return isAdmin;
	}

}
