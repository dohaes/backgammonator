package backgammonator.test.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import backgammonator.impl.game.GameManager;
import backgammonator.lib.game.Game;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;
import junit.framework.TestCase;

/**
 * Tests the implementation of interface {@link Game} and {@link Player} working
 * with the protocol.
 */
public class GameWithProtocolTestCase extends TestCase {

	private static File testDir = new File("testGameWithProtocol");
	private static String samplesJava = "sample/backgammonator/sample/player/protocol/java";
	private static String samplesC = "sample/backgammonator/sample/player/protocol/c";

	private String fileName1;
	private String fileName2;

	/**
	 * Tests in case of time out while getting move from player.
	 * The test player is implemented in Java.
	 */
	public void testTimedoutMoveJava() {
		testTimedoutMove(true);
	}
	
	/**
	 * Tests in case of time out while getting move from player.
	 * The test player is implemented in C/C++.
	 */
//	public void testTimedoutMoveC() { //TODO
//		testTimedoutMove(false);
//	}
	
	private void testTimedoutMove(boolean java) {
		try {
			copy("TimedoutMovePlayer", "SamplePlayer", java);
			Game game = GameManager.newGame(fileName1, fileName2, false);
			GameOverStatus status = null;

			try {
				status = game.start();
			} catch (Throwable t) {
				fail("Unexpected exception was thrown : " + t);
			}

			assertNotNull(status);
			assertEquals(GameOverStatus.TIMEDOUT, status);
			assertEquals("sampleplayer", game.getWinner().getName());

		} finally {
			cleanup();
		}
	}

	/**
	 * Tests in case of exception while getting move from player.
	 * The test player is implemented in Java.
	 */
	public void testExceptionInMoveJava() {
		testExceptionInMove(true);
	}
	
	/**
	 * Tests in case of exception while getting move from player.
	 * The test player is implemented in C/C++.
	 */
//	public void testExceptionInMoveC() { //TODO
//		testExceptionInMove(false);
//	}
	
	private void testExceptionInMove(boolean java) {
		try {
			copy("ExceptionPlayer", "SamplePlayer", java);
			Game game = GameManager.newGame(fileName1, fileName2, false);
			GameOverStatus status = null;

			try {
				status = game.start();
			} catch (Throwable t) {
				fail("Unexpected exception was thrown : " + t);
			}

			assertNotNull(status);
			assertEquals(GameOverStatus.EXCEPTION, status);
			assertEquals("sampleplayer", game.getWinner().getName());

		} finally {
			cleanup();
		}
	}
	
	/**
	 * Tests in case of premature process exit.
	 * The test player is implemented in Java.
	 */
	public void testPrematureProcessExitJava() {
		testPrematureProcessExit(true);
	}
	
	/**
	 * Tests in case of premature process exit.
	 * The test player is implemented in C/C++.
	 */
//	public void testPrematureProcessExitC() { //TODO
//		testPrematureProcessExit(false);
//	}
	
	private void testPrematureProcessExit(boolean java) {
		try {
			copy("PrematureExitPlayer", "SamplePlayer", java);
			Game game = GameManager.newGame(fileName1, fileName2, false);
			GameOverStatus status = null;

			try {
				status = game.start();
			} catch (Throwable t) {
				fail("Unexpected exception was thrown : " + t);
			}

			assertNotNull(status);
			assertEquals(GameOverStatus.EXCEPTION, status);
			assertEquals("sampleplayer", game.getWinner().getName());

		} finally {
			cleanup();
		}
	}

	/**
	 * Tests in case of empty string returned by player.
	 * The test player is implemented in Java.
	 */
	public void testEmptyStringReturnedJava() {
		testEmptyStringReturned(true);
	}
	
	/**
	 * Tests in case of empty string returned by player.
	 * The test player is implemented in C/C++.
	 */
//	public void testEmptyStringReturnedC() { //TODO
//		testEmptyStringReturned(false);
//	}
	
	private void testEmptyStringReturned(boolean java) {
		try {
			copy("EmptyMovePlayer", "SamplePlayer", java);
			Game game = GameManager.newGame(fileName1, fileName2, false);
			GameOverStatus status = null;

			try {
				status = game.start();
			} catch (Throwable t) {
				fail("Unexpected exception was thrown : " + t);
			}

			assertNotNull(status);
			assertEquals(GameOverStatus.INVALID_MOVE, status);
			assertEquals("sampleplayer", game.getWinner().getName());

		} finally {
			cleanup();
		}
	}

	/**
	 * Tests in case of invalid move returned by player.
	 * The test player is implemented in Java.
	 */
	public void testInvalidMoveReturnedJava() {
		testInvalidMoveReturned(true);
	}
	
	/**
	 * Tests in case of invalid move returned by player.
	 * The test player is implemented in C/C++.
	 */
//	public void testInvalidMoveReturnedC() { //TODO
//		testInvalidMoveReturned(false);
//	}
	
	private void testInvalidMoveReturned(boolean java) {
		try {
			copy("InvalidMovePlayer", "SamplePlayer", java);
			Game game = GameManager.newGame(fileName1, fileName2, false);
			GameOverStatus status = null;

			try {
				status = game.start();
			} catch (Throwable t) {
				fail("Unexpected exception was thrown : " + t);
			}

			assertNotNull(status);
			assertEquals(GameOverStatus.INVALID_MOVE, status);
			assertEquals("sampleplayer", game.getWinner().getName());

		} finally {
			cleanup();
		}
	}

	/**
	 * Tests in case of endless loop while getting move from player.
	 * The test player is implemented in Java.
	 */
	public void testEndlessLoopInMoveReturnedJava() {
		testEndlessLoopInMoveReturned(true);
	}
	
	/**
	 * Tests in case of endless loop while getting move from player.
	 * The test player is implemented in C/C++.
	 */
//	public void testEndlessLoopInMoveReturnedC() { //TODO
//		testEndlessLoopInMoveReturned(false);
//	}
	
	private void testEndlessLoopInMoveReturned(boolean java) {
		try {
			copy("EndlessLoopInMovePlayer", "SamplePlayer", java);
			Game game = GameManager.newGame(fileName1, fileName2, false);
			GameOverStatus status = null;

			try {
				status = game.start();
			} catch (Throwable t) {
				fail("Unexpected exception was thrown : " + t);
			}

			assertNotNull(status);
			assertEquals(GameOverStatus.TIMEDOUT, status);
			assertEquals("sampleplayer", game.getWinner().getName());

		} finally {
			cleanup();
		}
	}

	/**
	 * Tests in case of deadlock while getting move from player.
	 * The test player is implemented in Java.
	 */
//	public void testDeadlockInMoveJava() { 
//		testDeadlockInMove(true);
//	}
	
	/**
	 * Tests in case of deadlock while getting move from player.
	 * The test player is implemented in C/C++.
	 */
//	public void testDeadlockInMoveC() { 
//		testDeadlockInMove(false);
//	}
	
	@SuppressWarnings("unused") //TODO remove
	private void testDeadlockInMove(boolean java) { //FIXME 
		try {
			copy("DeadlockInMovePlayer", "SamplePlayer", java);
			Game game = GameManager.newGame(fileName1, fileName2, false);
			GameOverStatus status = null;

			try {
				status = game.start();
			} catch (Throwable t) {
				fail("Unexpected exception was thrown : " + t);
			}

			assertNotNull(status);
			assertEquals(GameOverStatus.TIMEDOUT, status);
			assertEquals("sampleplayer", game.getWinner().getName());

		} finally {
			cleanup();
		}
	}

	/**
	 * Tests in case of normal execution of the game.
	 * The test player is implemented in Java.
	 */
	public void testNormalJava() {
		testNormal(true);
	}
	
	/**
	 * Tests in case of normal execution of the game.
	 * The test player is implemented in C/C++.
	 */
//	public void testNormalC() { //TODO
//		testNormal(false);
//	}
	
	@SuppressWarnings("null")
	private void testNormal(boolean java) {
		try {
			copy("SamplePlayer", "SamplePlayer", java);
			Game game = GameManager.newGame(fileName1, fileName2, false);
			GameOverStatus status = null;

			try {
				status = game.start();
			} catch (Throwable t) {
				fail("Unexpected exception was thrown : " + t);
			}

			assertNotNull(status);
			assertTrue(status.isNormal());
			assertTrue("sampleplayer1".equals(game.getWinner().getName())
					|| "sampleplayer2".equals(game.getWinner().getName()));

		} finally {
			cleanup();
		}
	}

	private void copy(String name1, String name2, boolean java) {
		if (!testDir.exists()) testDir.mkdirs();
		boolean index = name1.equals(name2);
		String dirName1 = testDir.getAbsolutePath() + File.separatorChar
				+ name1.toLowerCase() + (index ? "1" : "");
		String dirName2 = testDir.getAbsolutePath() + File.separatorChar
				+ name2.toLowerCase() + (index ? "2" : "");

		File dir1 = new File(dirName1);
		File dir2 = new File(dirName2);
		dir1.mkdirs();
		dir2.mkdirs();

		fileName1 = dir1.getAbsolutePath() + File.separatorChar + name1
				+ ".java";
		fileName2 = dir2.getAbsolutePath() + File.separatorChar + name2
				+ ".java";

		String samplesPath = new File((java ? samplesJava : samplesC).replace('/', File.separatorChar))
				.getAbsolutePath();
		try {
			copyFile(samplesPath + File.separatorChar + name1 + (java ? ".java" : ".c"),
					fileName1);
			copyFile(samplesPath + File.separatorChar + name2 + (java ? ".java" : ".c"),
					fileName2);
		} catch (IOException e) {
			fail("Unexpected exception while cpopying files : "
					+ e.getMessage());
		}

	}

	private void copyFile(String from, String to) throws IOException {
		
		BufferedReader fr = new BufferedReader(new FileReader(new File(from)));
		BufferedWriter fw = new BufferedWriter(new FileWriter(new File(to)));
		String line = null;
		try {
			while ((line = fr.readLine()) != null) {
				if (line.startsWith("package")) continue;
				fw.write(line + "\n");
			}
		} finally {
			fr.close();
			fw.flush();
			fw.close();
		}

	}

	private void cleanup() {
		if (testDir.exists()) assertTrue("Could not delete files",
				delete(testDir));
	}

	private boolean delete(File file) {
		if (file.isFile()) return file.delete();
		File[] files = file.listFiles();
		if (files == null || files.length == 0) return file.delete();
		for (int i = 0; i < files.length; i++) {
			delete(files[i]);
		}
		return file.delete();
	}
}
