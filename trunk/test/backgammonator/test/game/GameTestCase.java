package backgammonator.test.game;

import backgammonator.core.Game;
import backgammonator.core.GameOverStatus;
import backgammonator.core.PlayerStatus;
import backgammonator.impl.game.GameManager;
import backgammonator.test.players.AbstractTestPlayer;
import backgammonator.test.players.DeadlockInMovePlayer;
import backgammonator.test.players.DummyPlayer;
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
		AbstractTestPlayer normal = new DummyPlayer();
		
		Game game = GameManager.newGame(timedout, normal, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertNotNull(status);
		assertEquals(GameOverStatus.TIMEDOUT, status);
		assertEquals(PlayerStatus.LOSE_TIMEDOUT, timedout.getStatus());
		assertEquals(PlayerStatus.WIN_TIMEDOUT, normal.getStatus());
	}
	
	public void testExceptionInMove() {
		AbstractTestPlayer exception = new ExceptionPlayer();
		AbstractTestPlayer normal = new DummyPlayer();
		
		Game game = GameManager.newGame(exception, normal, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertNotNull(status);
		assertEquals(GameOverStatus.EXCEPTION, status);
		assertEquals(PlayerStatus.LOSE_EXCEPTION, exception.getStatus());
		assertEquals(PlayerStatus.WIN_EXCEPTION, normal.getStatus());
	}

	public void testNullReturned() {
		AbstractTestPlayer nullmove = new NullMovePlayer();
		AbstractTestPlayer normal = new DummyPlayer();
		
		Game game = GameManager.newGame(nullmove, normal, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertNotNull(status);
		assertEquals(GameOverStatus.INVALID_MOVE, status);
		assertEquals(PlayerStatus.LOSE_INVALID_MOVE, nullmove.getStatus());
		assertEquals(PlayerStatus.WIN_INVALID_MOVE, normal.getStatus());
	}
	
	public void testInvalidMoveReturned() {
		AbstractTestPlayer invalid = new InvalidMovePlayer();
		AbstractTestPlayer normal = new DummyPlayer();
		
		Game game = GameManager.newGame(normal, invalid, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertNotNull(status);
		assertEquals(GameOverStatus.INVALID_MOVE, status);
		assertEquals(PlayerStatus.WIN_INVALID_MOVE, normal.getStatus());
		assertEquals(PlayerStatus.LOSE_INVALID_MOVE, invalid.getStatus());
	}
	
	public void testEndlessLoopInMoveReturned() {
    AbstractTestPlayer endlessloop = new EndlessLoopInMovePlayer();
    AbstractTestPlayer normal = new DummyPlayer();
    
    Game game = GameManager.newGame(endlessloop, normal, false);
    GameOverStatus status = null;
    
    try {
      status = game.start();
    } catch (Throwable t) {
      fail("Unexpected exception was thrown : " + t);
    }
    
    assertNotNull(status);
    assertEquals(GameOverStatus.TIMEDOUT, status);
    assertEquals(PlayerStatus.WIN_TIMEDOUT, normal.getStatus());
    assertEquals(PlayerStatus.LOSE_TIMEDOUT, endlessloop.getStatus());
  }
	
	public void testDeadlockInMoveReturned() {
    AbstractTestPlayer deadlock = new DeadlockInMovePlayer();
    AbstractTestPlayer normal = new DummyPlayer();
    
    Game game = GameManager.newGame(deadlock, normal, false);
    GameOverStatus status = null;
    
    try {
      status = game.start();
    } catch (Throwable t) {
      fail("Unexpected exception was thrown : " + t);
    }
    
    assertNotNull(status);
    assertEquals(GameOverStatus.TIMEDOUT, status);
    assertEquals(PlayerStatus.WIN_TIMEDOUT, normal.getStatus());
    assertEquals(PlayerStatus.LOSE_TIMEDOUT, deadlock.getStatus());
  }
	
	public void testNormal() {
		AbstractTestPlayer normal1 = new DummyPlayer();
		AbstractTestPlayer normal2 = new DummyPlayer();
		
		Game game = GameManager.newGame(normal1, normal2, false);
		GameOverStatus status = null;
		
		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}
		
		assertNotNull(status);
		assertEquals(GameOverStatus.NORMAL, status);
		PlayerStatus status1 = normal1.getStatus();
		assertTrue(status1 == PlayerStatus.WINS_NORMAL || status1 == PlayerStatus.LOSE_NORMAL);
		if (status1 == PlayerStatus.WINS_NORMAL) assertEquals(PlayerStatus.LOSE_NORMAL, normal2.getStatus());
		else assertEquals(PlayerStatus.WINS_NORMAL, normal2.getStatus());
	}

}
