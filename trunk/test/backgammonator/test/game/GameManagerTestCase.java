package backgammonator.test.game;

import backgammonator.core.GameOverStatus;
import backgammonator.impl.game.GameManager;
import backgammonator.test.players.AbstractTestPlayer;
import backgammonator.test.players.DummyPlayer;
import backgammonator.test.players.ExceptionPlayer;
import backgammonator.test.players.InvalidMovePlayer;
import backgammonator.test.players.NullMovePlayer;
import backgammonator.test.players.TimedoutMovePlayer;
import junit.framework.TestCase;

/**
 * Tests class {@link GameManager}.
 */
public class GameManagerTestCase extends TestCase {
	
	public void testTimedoutMove() {
		AbstractTestPlayer timedout = new TimedoutMovePlayer();
		AbstractTestPlayer normal = new DummyPlayer();
		
		GameManager game = new GameManager(timedout, normal, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertNotNull(status);
		assertEquals(GameOverStatus.TIMEDOUT, status);
		assertFalse(timedout.wins());
		assertTrue(normal.wins());
	}
	
	public void testExceptionInMove() {
		AbstractTestPlayer exception = new ExceptionPlayer();
		AbstractTestPlayer normal = new DummyPlayer();
		
		GameManager game = new GameManager(exception, normal, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertNotNull(status);
		assertEquals(GameOverStatus.EXCEPTION, status);
		assertFalse(exception.wins());
		assertTrue(normal.wins());
	}

	public void testNullReturned() {
		AbstractTestPlayer nullmove = new NullMovePlayer();
		AbstractTestPlayer normal = new DummyPlayer();
		
		GameManager game = new GameManager(nullmove, normal, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertNotNull(status);
		assertEquals(GameOverStatus.INVALID_MOVE, status);
		assertFalse(nullmove.wins());
		assertTrue(normal.wins());
	}
	
	public void testInvalidMoveReturned() {
		AbstractTestPlayer invalid = new InvalidMovePlayer();
		AbstractTestPlayer normal = new DummyPlayer();
		
		GameManager game = new GameManager(normal, invalid, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertNotNull(status);
		assertEquals(GameOverStatus.INVALID_MOVE, status);
		assertTrue(normal.wins());
		assertFalse(invalid.wins());
	}
	
	public void testNormal() {
		AbstractTestPlayer normal1 = new DummyPlayer();
		AbstractTestPlayer normal2 = new DummyPlayer();
		
		GameManager game = new GameManager(normal1, normal2, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertNotNull(status);
		assertEquals(GameOverStatus.OK, status);
		if (normal1.wins()) assertFalse(normal2.wins());
		else assertTrue(normal2.wins());
	}

}
