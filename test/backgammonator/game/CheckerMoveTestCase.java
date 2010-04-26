package backgammonator.game;

import junit.framework.TestCase;
import backgammonator.core.CheckerMove;

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
			move.setMoveLength(1);
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
		assertEquals(1, move.getMoveLength());
		try {
			move.setMoveLength(6);
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
		assertEquals(6, move.getMoveLength());
		try {
			move.setStartPoint(1);
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
		assertEquals(1, move.getStartPoint());
		try {
			move.setStartPoint(24);
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
		assertEquals(24, move.getStartPoint());
		
	}
	
	public void testInvalidLength() {
		try {
			CheckerMove move = new CheckerMove(25, 5);
			fail("Expected IIllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			//ok
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
		
		try {
			CheckerMove move = new CheckerMove(0, 5);
			fail("Expected IIllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			//ok
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}

	}
	
	public void testInvalidPoint() {
		
		try {
			CheckerMove move = new CheckerMove(3, 8);
			fail("Expected IIllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			//ok
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
		
		try {
			CheckerMove move = new CheckerMove(3, 0);
			fail("Expected IIllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			//ok
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
	}


}
