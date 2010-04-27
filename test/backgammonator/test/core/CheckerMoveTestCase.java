package backgammonator.test.core;

import junit.framework.TestCase;
import backgammonator.core.CheckerMove;

/**
 * Tests class {@link CheckerMove}
 */
public class CheckerMoveTestCase extends TestCase {

	public void testCreateValidMove() {
		CheckerMove move = null;
		try {
			move = new CheckerMove(2, 3);
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
		assertEquals(2, move.getStartPoint());
		assertEquals(3, move.getMoveLength());
		try {
			move = new CheckerMove(3, 1);
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
		assertEquals(1, move.getMoveLength());
		try {
			move = new CheckerMove(2, 6);
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
		assertEquals(6, move.getMoveLength());
		try {
			move = new CheckerMove(1, 3);
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
		assertEquals(1, move.getStartPoint());
		try {
			move = new CheckerMove(24, 3);
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
		assertEquals(24, move.getStartPoint());

	}

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
