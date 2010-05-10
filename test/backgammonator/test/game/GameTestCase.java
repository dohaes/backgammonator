package backgammonator.test.game;

import backgammonator.core.Game;
import backgammonator.core.GameOverStatus;
import backgammonator.impl.game.GameManager;
import backgammonator.test.players.AbstractTestPlayer;
import backgammonator.test.players.DeadlockInMovePlayer;
import backgammonator.test.players.SamplePlayer;
import backgammonator.test.players.EndlessLoopInMovePlayer;
import backgammonator.test.players.ExceptionPlayer;
import backgammonator.test.players.InvalidMovePlayer;
import backgammonator.test.players.NullMovePlayer;
import backgammonator.test.players.TimedoutMovePlayer;
import junit.framework.TestCase;

/**
 * Tests class {@link GameImpl}.
 */
public class GameTestCase extends TestCase {
	
	public void testTimedoutMove() {
		AbstractTestPlayer timedout = new TimedoutMovePlayer();
		AbstractTestPlayer normal = new SamplePlayer();
		
		Game game = GameManager.newGame(timedout, normal, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertEquals(normal, game.getWinner());
		assertNotNull(status);
		assertEquals(GameOverStatus.TIMEDOUT, status);
		assertEquals(GameOverStatus.TIMEDOUT, timedout.getStatus());
		assertEquals(GameOverStatus.TIMEDOUT, normal.getStatus());
		assertTrue(normal.wins());
		assertFalse(timedout.wins());
	}
	
	public void testExceptionInMove() {
		AbstractTestPlayer exception = new ExceptionPlayer();
		AbstractTestPlayer normal = new SamplePlayer();
		
		Game game = GameManager.newGame(exception, normal, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertEquals(normal, game.getWinner());
		assertNotNull(status);
		assertEquals(GameOverStatus.EXCEPTION, status);
		assertEquals(GameOverStatus.EXCEPTION, exception.getStatus());
		assertEquals(GameOverStatus.EXCEPTION, normal.getStatus());
	  assertTrue(normal.wins());
    assertFalse(exception.wins());
	}

	public void testNullReturned() {
		AbstractTestPlayer nullmove = new NullMovePlayer();
		AbstractTestPlayer normal = new SamplePlayer();
		
		Game game = GameManager.newGame(nullmove, normal, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertEquals(normal, game.getWinner());
		assertNotNull(status);
		assertEquals(GameOverStatus.INVALID_MOVE, status);
		assertEquals(GameOverStatus.INVALID_MOVE, nullmove.getStatus());
		assertEquals(GameOverStatus.INVALID_MOVE, normal.getStatus());
	  assertTrue(normal.wins());
    assertFalse(nullmove.wins());
	}
	
	public void testInvalidMoveReturned() {
		AbstractTestPlayer invalid = new InvalidMovePlayer();
		AbstractTestPlayer normal = new SamplePlayer();
		
		Game game = GameManager.newGame(normal, invalid, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertEquals(normal, game.getWinner());
		assertNotNull(status);
		assertEquals(GameOverStatus.INVALID_MOVE, status);
		assertEquals(GameOverStatus.INVALID_MOVE, normal.getStatus());
		assertEquals(GameOverStatus.INVALID_MOVE, invalid.getStatus());
	  assertTrue(normal.wins());
    assertFalse(invalid.wins());
	}
	
	public void testEndlessLoopInMoveReturned() {
    AbstractTestPlayer endlessloop = new EndlessLoopInMovePlayer();
    AbstractTestPlayer normal = new SamplePlayer();
    
    Game game = GameManager.newGame(endlessloop, normal, false);
    GameOverStatus status = null;
    
    try {
      status = game.start();
    } catch (Throwable t) {
      fail("Unexpected exception was thrown : " + t);
    }
    
    assertEquals(normal, game.getWinner());
    assertNotNull(status);
    assertEquals(GameOverStatus.TIMEDOUT, status);
    assertEquals(GameOverStatus.TIMEDOUT, normal.getStatus());
    assertEquals(GameOverStatus.TIMEDOUT, endlessloop.getStatus());
    assertTrue(normal.wins());
    assertFalse(endlessloop.wins());
  }
	
	public void testDeadlockInMoveReturned() {
    AbstractTestPlayer deadlock = new DeadlockInMovePlayer();
    AbstractTestPlayer normal = new SamplePlayer();
    
    Game game = GameManager.newGame(deadlock, normal, false);
    GameOverStatus status = null;
    
    try {
      status = game.start();
    } catch (Throwable t) {
      fail("Unexpected exception was thrown : " + t);
    }
    
    assertEquals(normal, game.getWinner());
    assertNotNull(status);
    assertEquals(GameOverStatus.TIMEDOUT, status);
    assertEquals(GameOverStatus.TIMEDOUT, normal.getStatus());
    assertEquals(GameOverStatus.TIMEDOUT, deadlock.getStatus());
    assertTrue(normal.wins());
    assertFalse(deadlock.wins());
  }
	
	public void testNormal() {
		AbstractTestPlayer normal1 = new SamplePlayer();
		AbstractTestPlayer normal2 = new SamplePlayer();
		
		Game game = GameManager.newGame(normal1, normal2, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertNotNull(status);
		assertEquals(GameOverStatus.NORMAL, status);
		GameOverStatus status1 = normal1.getStatus(), status2 = normal2.getStatus();
		assertEquals(GameOverStatus.NORMAL, status1);
		assertEquals(GameOverStatus.NORMAL, status2);
		if (normal1.wins()) {
		  assertEquals(normal1, game.getWinner());
		  assertFalse(normal2.wins());
		} else {
		  assertEquals(normal2, game.getWinner());
		  assertTrue(normal2.wins());
		}
	}

}
