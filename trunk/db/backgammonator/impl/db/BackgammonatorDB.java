package backgammonator.impl.db;

/**
 * Class, containing the necessary methods for communication with db
 */
public class BackgammonatorDB {

	/**
	 * Sets an account to the db.
	 * 
	 * @param user username of the account that will be set to db
	 * @param pass password of the account that will be set to db
	 * @param email email of the account that will be set to db
	 * @param first first name of the account that will be set to db
	 * @param last last name of the account that will be set to db
	 * @return true if the account was set to db, otherwise - false
	 */
	public boolean setAccount(String user, String pass, String email,
			String first, String last) {
		return true;
	}

	/**
	 * Checks if account with a specified username exists in the db.
	 * 
	 * @param user username of the account that will be checked
	 * @return true if the user exists
	 */
	public boolean isUserExists(String user) {
		return false;
	}

	/**
	 * Determine if the user with given username and password can login and
	 * perform the login
	 * 
	 * @param user username of the user
	 * @param pass password for the user
	 * @return true if the login is successful
	 */
	public boolean login(String user, String pass) {
		return true;
	}
}
