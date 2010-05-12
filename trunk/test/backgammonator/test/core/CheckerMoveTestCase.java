package backgammonator.test.core;

import junit.framework.TestCase;
import backgammonator.lib.game.CheckerMove;

/**
 * Tests class {@link CheckerMove}
 */
public class CheckerMoveTestCase extends TestCase {

	/**
	 * Tests creation of a valid {@link CheckerMove} object.
	 */
	public void testCreateValidMove() {
		CheckerMove move = null;
		try {
			move = new CheckerMove(2, 3);
			assertEquals(2, move.getStartPoint());
			assertEquals(3, move.getDie());
		} catch (IllegalArgumentException t) {
			fail("Unexpected exception : " + t.getMessage());
		}

		try {
			move = new CheckerMove(3, 1);
			assertEquals(1, move.getDie());
		} catch (IllegalArgumentException t) {
			fail("Unexpected exception : " + t.getMessage());
		}

		try {
			move = new CheckerMove(2, 6);
			assertEquals(6, move.getDie());
		} catch (IllegalArgumentException t) {
			fail("Unexpected exception : " + t.getMessage());
		}

		try {
			move = new CheckerMove(1, 3);
			assertEquals(1, move.getStartPoint());
		} catch (IllegalArgumentException t) {
			fail("Unexpected exception : " + t.getMessage());
		}

		try {
			move = new CheckerMove(24, 3);
			assertEquals(24, move.getStartPoint());
		} catch (IllegalArgumentException t) {
			fail("Unexpected exception : " + t.getMessage());
		}

	}

	/**
	 * Tests creation of a {@link CheckerMove} object with invalid length.
	 */
	public void testInvalidLength() {
		try {
			new CheckerMove(26, 5);
			fail("Expected IIllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			// ok
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}

		try {
			new CheckerMove(-1, 5);
			fail("Expected IIllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			// ok
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}

	}

	/**
	 * Tests creation of a {@link CheckerMove} object with invalid point.
	 */
	public void testInvalidPoint() {

		try {
			new CheckerMove(3, 8);
			fail("Expected IIllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			// ok
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}

		try {
			new CheckerMove(3, 0);
			fail("Expected IIllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			// ok
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
	}

}
