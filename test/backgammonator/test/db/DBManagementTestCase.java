package backgammonator.test.db;

import backgammonator.impl.db.AccountsManager;
import backgammonator.impl.db.DBManager;
import backgammonator.lib.db.Account;
import junit.framework.TestCase;

/**
 * Test the API for manipulating the user accounts.
 */
public class DBManagementTestCase extends TestCase {

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// create the database
		DBManager.createDB("BackgammonatorTEST");
		super.setUp();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		// drop the database
		DBManager.createDB("BackgammonatorTEST");
		super.tearDown();
	}

	/**
	 * Tests creating of user accounts.
	 */
	public void testCreatingAccount() {
		Account testAccount = AccountsManager.getAccount("testcreate");
		assertNotNull(testAccount);
		assertFalse(testAccount.exists());
		assertFalse(testAccount.isAdmin());

		assertEquals("testcreate", testAccount.getUsername());
		
		testAccount.setPassword("password");
		testAccount.setFirstName("firstName");
		testAccount.setLastname("lastName");
		testAccount.setEmail("email");

		testAccount.store();

		testAccount = AccountsManager.getAccount("testcreate");
		assertNotNull(testAccount);
		assertTrue(testAccount.exists());

		assertEquals("testcreate", testAccount.getUsername());
		assertEquals("password", testAccount.getPassword());
		assertEquals("firstName", testAccount.getFirstName());
		assertEquals("lastName", testAccount.getLastName());
		assertEquals("email", testAccount.getEmail());

	}

	/**
	 * Tests updating of user accounts.
	 */
	public void testUpdatingAccount() {
		Account testAccount = AccountsManager.getAccount("testupdate");
		assertNotNull(testAccount);
		assertFalse(testAccount.exists());
		assertFalse(testAccount.isAdmin());
		
		assertEquals("testupdate", testAccount.getUsername());
		
		testAccount.setPassword("password");
		testAccount.setFirstName("firstName");
		testAccount.setLastname("lastName");
		testAccount.setEmail("email");

		testAccount.store();
		
		testAccount = AccountsManager.getAccount("testupdate");
		assertNotNull(testAccount);
		assertTrue(testAccount.exists());
		
		testAccount.setPassword("password2");
		testAccount.setFirstName("firstName2");
		testAccount.setLastname("lastName2");
		testAccount.setEmail("email2");

		testAccount.store();
		
		testAccount = AccountsManager.getAccount("testupdate");
		assertNotNull(testAccount);
		assertTrue(testAccount.exists());
		
		assertEquals("testupdate", testAccount.getUsername());
		assertEquals("password2", testAccount.getPassword());
		assertEquals("firstName2", testAccount.getFirstName());
		assertEquals("lastName2", testAccount.getLastName());
		assertEquals("email2", testAccount.getEmail());
	}

	/**
	 * Tests deleting of user accounts.
	 */
	public void testDeletingAccount() {
		Account testAccount = AccountsManager.getAccount("testdelete");
		assertNotNull(testAccount);
		assertFalse(testAccount.exists());
		assertFalse(testAccount.isAdmin());
		
		try {
			testAccount.delete();
			fail("Expected IllegalStateException to be thrown");
		} catch (IllegalStateException ise) {
			// OK
		} catch (Throwable t) {
			fail("Unexpected exception wat thrown: " + t.getMessage());
		}
		
		testAccount.setPassword("password");
		testAccount.setFirstName("firstName");
		testAccount.setLastname("lastName");
		testAccount.setEmail("email");
		
		testAccount.store();
		testAccount = AccountsManager.getAccount("testdelete");
		assertNotNull(testAccount);
		assertTrue(testAccount.exists());
		
		try {
			testAccount.delete();
		} catch (Throwable t) {
			fail("Unexpected exception wat thrown: " + t.getMessage());
		}
		
		assertFalse(testAccount.exists());
		//check in the db
		testAccount = AccountsManager.getAccount("testdelete");
		assertNotNull(testAccount);
		assertFalse(testAccount.exists());
		
	}

	/**
	 * Tests setting null values.
	 */
	public void testNullValues() {
		Account testAccount = AccountsManager.getAccount("testuser");
		assertNotNull(testAccount);
		assertFalse(testAccount.exists());

		try {
			testAccount.setEmail(null);
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException npe) {
			// OK
		} catch (Throwable t) {
			fail("Unexpected exception wat thrown: " + t.getMessage());
		}

		try {
			testAccount.setPassword(null);
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException npe) {
			// OK
		} catch (Throwable t) {
			fail("Unexpected exception wat thrown: " + t.getMessage());
		}

		try {
			testAccount.setFirstName(null);
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException npe) {
			// OK
		} catch (Throwable t) {
			fail("Unexpected exception wat thrown: " + t.getMessage());
		}

		try {
			testAccount.setLastname(null);
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException npe) {
			// OK
		} catch (Throwable t) {
			fail("Unexpected exception wat thrown: " + t.getMessage());
		}
	}
}
