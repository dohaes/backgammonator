package backgammonator.lib.db;

import java.util.List;

/**
 * Accesses user accounts data stored in the database.
 */
public interface AccountsManager {

	/**
	 * Gets an {@link Account} object for the specified username. If there is no
	 * record in the database for the specified username then the
	 * {@link Account#exists()} method will return false when invoked on the
	 * returned from this method account object.
	 * 
	 * @throws IllegalStateException if database access error occurs.
	 */
	Account getAccount(String username);

	/**
	 * Gets all accounts stored in the database.
	 * 
	 * @return list of all accounts stored in the database. If there are no
	 *         registered users then an empty list will be returned.
	 * @throws IllegalStateException if database access error occurs.
	 */
	List<Account> getAllAccounts();

	/**
	 * Checks if the specified email is unique.
	 * 
	 * @param email the email to check
	 * @return <code>true</code> if the given email is unique or
	 *         <code>false</code> otherwise.
	 */
	boolean isUnique(String email);

}
