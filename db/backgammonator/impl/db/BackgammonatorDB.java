package backgammonator.impl.db;

public class BackgammonatorDB {

	/**
	 * @param user
	 * @param pass
	 * @param email
	 * @param first
	 * @param last
	 * 
	 * @return true if the account was set to db, otherwise - false
	 */
	public boolean setAccount(String user, String pass, String email,
			String first, String last) {
		return true;
	}
	
	/**
	 * 
	 */
	public boolean isUserExists(String user) {
		return false;
	}
	
	public boolean login(String user, String pass) {
		return true;
	}
}
