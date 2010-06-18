package backgammonator.lib.db;

/**
 * Interface for manipulating user accounts.
 */
public interface Account {

	String getUsername();

	String getPassword();

	String getFirstName();

	String getLastname();

	String getEmail();

	void setPassword(String password);

	void setFirstName(String firstName);

	void setLastname(String lastName);

	void setEmail(String email);

	boolean exists();

	boolean store();
	
	boolean isAdmin();

	/**
	 * @throws IllegalStateException if the account does not exist in the
	 *             database.
	 * @return
	 */
	boolean delete();

}
