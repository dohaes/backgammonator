package backgammonator.lib.db;

/**
 * Interface for manipulating user accounts.
 */
public interface Account {

	/**
	 * Gets the username for this account.
	 */
	String getUsername();

	/**
	 * Gets the password for this account. The password is MD5 encoded.
	 */
	String getPassword();

	/**
	 * Gets the first name for this account.
	 */
	String getFirstName();

	/**
	 * Gets the last name for this account.
	 */
	String getLastName();

	/**
	 * Gets the email for this account.
	 */
	String getEmail();

	/**
	 * Sets the password for this account. The password should be MD5 encoded.
	 * 
	 * @throws NullPointerException if <code>password</code> is null
	 */
	void setPassword(String password);

	/**
	 * Sets the first name for this account.
	 * 
	 * @throws NullPointerException if <code>password</code> is null
	 */
	void setFirstName(String firstName);

	/**
	 * Sets the last name for this account.
	 * 
	 * @throws NullPointerException if <code>password</code> is null
	 */
	void setLastname(String lastName);

	/**
	 * Sets the email for this account. The email is expected to be valid.
	 * 
	 * @throws NullPointerException if <code>password</code> is null
	 */
	void setEmail(String email);

	/**
	 * Indicates if the account is stored in the database.
	 * 
	 * @return <code>true</code> if the account is present in the database or
	 *         <code>false</code> otherwise.
	 */
	boolean exists();

	/**
	 * Stores this account in the database. If the account exists and some of
	 * its fields are modified using the setters then the account will be
	 * updated. If the account does not exist in the database then a new record
	 * with this account will be made in the database.
	 * 
	 * @throws IllegalStateException if database access error occurs.
	 */
	void store();

	/**
	 * Deletes this account from the database.
	 * 
	 * @throws IllegalStateException if database access error occurs or the
	 *             account is not present in the database.
	 */
	void delete();

	/**
	 * Indicates if this account is the administrator's account.
	 * 
	 * @return <code>true</code> if this account is the administrator's account
	 *         or <code>false</code> otherwise.
	 */
	boolean isAdmin();

}
