package backgammonator.game;

import junit.framework.TestCase;
import backgammonator.core.CheckerMove;
import backgammonator.core.PlayerMove;

/**
 * Tests class {@link PlayerMove} 
 */
public class PlayerMoveTestCase extends TestCase {
	
	private CheckerMove move1 = new CheckerMove(11, 1);
	private CheckerMove move2d = new CheckerMove(22, 2);
	private CheckerMove move3d = new CheckerMove(3, 2);
	private CheckerMove move4d = new CheckerMove(4, 2);
	private CheckerMove move5d = new CheckerMove(5, 2);
	private CheckerMove[] moves1 = {move1, move2d};
	private CheckerMove[] moves3w = {move2d, move1, move5d};
	private CheckerMove[] moves2 = {move2d, move3d, move4d, move5d};
	private CheckerMove[] moves2w = {move2d, move3d, move1, move5d};
	
	public void testCreateValidMove() {
		try {
			new PlayerMove(moves1);
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
		
		try {
			new PlayerMove(moves2);
		} catch (Throwable t) {
			fail("Unexpected exception : " + t.getMessage());
		}
	}
	
	public void testCreateInvalidMove () {
		try {
			new PlayerMove(null);
			fail("Expected NullPointerException");
		} catch (NullPointerException npe) {
			//ok
		} catch (Throwable t) {
			fail("Expected NullPointerException");
		}
		
		try {
			new PlayerMove(moves3w);
			fail("Expected IIllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			//ok
		} catch (Throwable t) {
			fail("Expected IIllegalArgumentException");
		}
		
		try {
			new PlayerMove(moves2w);
			fail("Expected IIllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			//ok
		} catch (Throwable t) {
			t.printStackTrace();
			fail("Expected IIllegalArgumentException ");
		}
	}
	
	public void testIsDouble() {
		PlayerMove playerMove = new PlayerMove(moves2);
		assertTrue(playerMove.isDouble());
		playerMove = new PlayerMove(moves1);
		assertFalse(playerMove.isDouble());
	}
	
	public void testGetCheckerMove() {
		PlayerMove move = new PlayerMove(moves1);
		
		try {
			move.getCheckerMove(-1);
			fail("Expected IIllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			//ok
		} catch (Throwable t) {
			fail("Expected IIllegalArgumentException");
		}
		
		try {
			move.getCheckerMove(3);
			fail("Expected IIllegalArgumentException");
		} catch (IllegalArgumentException iae) {
			//ok
		} catch (Throwable t) {
			fail("Expected IIllegalArgumentException");
		}
		
		CheckerMove chmove = null;
		try {
			chmove = move.getCheckerMove(0);
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t.getMessage());
		}
		
		assertNotNull(chmove);
		assertEquals(1, chmove.getMoveLength());
		assertEquals(11, chmove.getStartPoint());
		
		try {
			chmove = move.getCheckerMove(1);
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t.getMessage());
		}
		
		assertNotNull(chmove);
		assertEquals(2, chmove.getMoveLength());
		assertEquals(22, chmove.getStartPoint());
	}
	
	public void testToString() {
		//TODO
	}

}
