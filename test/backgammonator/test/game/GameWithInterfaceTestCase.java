package backgammonator.test.game;

import backgammonator.impl.game.GameManager;
import backgammonator.lib.game.Game;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;
import backgammonator.sample.players.interfacce.AbstractSamplePlayer;
import backgammonator.sample.players.interfacce.DeadlockInMovePlayer;
import backgammonator.sample.players.interfacce.EndlessLoopInMovePlayer;
import backgammonator.sample.players.interfacce.ExceptionPlayer;
import backgammonator.sample.players.interfacce.InvalidMovePlayer;
import backgammonator.sample.players.interfacce.NullMovePlayer;
import backgammonator.sample.players.interfacce.SamplePlayer;
import backgammonator.sample.players.interfacce.TimedoutMovePlayer;
import junit.framework.TestCase;

/**
 * Tests the implementation of interface {@link Game}.
 */
public class GameWithInterfaceTestCase extends TestCase {

	/**
	 * Tests in case of time out in
	 * {@link Player#getMove(backgammonator.lib.game.BackgammonBoard, backgammonator.lib.game.Dice)}
	 */
	public void testTimedoutMove() {
		AbstractSamplePlayer timedout = new TimedoutMovePlayer();
		AbstractSamplePlayer normal = new SamplePlayer();

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

	/**
	 * Tests in case of exception in
	 * {@link Player#getMove(backgammonator.lib.game.BackgammonBoard, backgammonator.lib.game.Dice)}
	 */
	public void testExceptionInMove() {
		AbstractSamplePlayer exception = new ExceptionPlayer();
		AbstractSamplePlayer normal = new SamplePlayer();

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

	/**
	 * Tests in case of <code></code> returned in
	 * {@link Player#getMove(backgammonator.lib.game.BackgammonBoard, backgammonator.lib.game.Dice)}
	 */
	public void testNullReturned() {
		AbstractSamplePlayer nullmove = new NullMovePlayer();
		AbstractSamplePlayer normal = new SamplePlayer();

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

	/**
	 * Tests in case of invalid move returned in
	 * {@link Player#getMove(backgammonator.lib.game.BackgammonBoard, backgammonator.lib.game.Dice)}
	 */
	public void testInvalidMoveReturned() {
		AbstractSamplePlayer invalid = new InvalidMovePlayer();
		AbstractSamplePlayer normal = new SamplePlayer();

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

	/**
	 * Tests in case of endless loop in
	 * {@link Player#getMove(backgammonator.lib.game.BackgammonBoard, backgammonator.lib.game.Dice)}
	 */
	public void testEndlessLoopInMoveReturned() {
		AbstractSamplePlayer endlessloop = new EndlessLoopInMovePlayer();
		AbstractSamplePlayer normal = new SamplePlayer();

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

	/**
	 * Tests in case of deadlock in
	 * {@link Player#getMove(backgammonator.lib.game.BackgammonBoard, backgammonator.lib.game.Dice)}
	 */
	public void testDeadlockInMoveReturned() {
		AbstractSamplePlayer deadlock = new DeadlockInMovePlayer();
		AbstractSamplePlayer normal = new SamplePlayer();

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

	/**
	 * Tests in case of normal execution of the game.
	 */
	public void testNormal() {
		AbstractSamplePlayer normal1 = new SamplePlayer();
		AbstractSamplePlayer normal2 = new SamplePlayer();

		Game game = GameManager.newGame(normal1, normal2, false);
		GameOverStatus status = null;

		try {
			status = game.start();
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t);
		}

		assertNotNull(status);
		assertEquals(GameOverStatus.NORMAL, status);
		GameOverStatus status1 = normal1.getStatus(), status2 = normal2
				.getStatus();
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
