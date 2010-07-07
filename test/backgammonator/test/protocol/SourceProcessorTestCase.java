package backgammonator.test.protocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import backgammonator.impl.processor.SourceProcessor;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;
import backgammonator.lib.processor.PlayerBuilderException;
import junit.framework.TestCase;

/**
 * Tests class {@link SourceProcessor}.
 */
public class SourceProcessorTestCase extends TestCase {

	/** path to java samples **/
	private static String samplesJava = "sample/backgammonator/sample/player/protocol/java";
	private static String samplesCPP = "sample/backgammonator/sample/player/protocol/cpp";
	private static File testDir = new File("testGameWithProtocol");
	private String fileName1;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() {
		if (!testDir.exists()) testDir.mkdirs();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		cleanup();
		super.tearDown();
	}

	/**
	 * Tests processing source with compilation error.
	 * The test player is implemented in Java.
	 */
	public void testProcessSourceCompilationErrorJava() {
		testProcessSourceCompilationError(true);
	}

	/**
	 * Tests processing source with compilation error.
	 * The test player is implemented in C++.
	 */
	public void testProcessSourceCompilationErrorCPP() {
		testProcessSourceCompilationError(false);
	}

	private void testProcessSourceCompilationError(boolean java) {
		copy("CompilationErrorPlayer", java);
		try {
			SourceProcessor.processSource(fileName1);
			fail("Excpected ProcessingException to be thrown");
		} catch (PlayerBuilderException e) {
			// OK
		} catch (Throwable t) {
			fail("Unexpected exception was thrown : " + t.getMessage());
		}
	}

	/**
	 * Tests processing source which compilation is successful
	 * The test player is implemented in Java.
	 */
	public void testProcessSourceCompilationSuccessfulJava() {
		testProcessSourceCompilationSuccessful(true);
	}
	
	/**
	 * Tests processing source which compilation is successful
	 * The test player is implemented in C++.
	 */
	public void testProcessSourceCompilationSuccessfulCPP() {
		testProcessSourceCompilationSuccessful(false);
	}
	
	private void testProcessSourceCompilationSuccessful(boolean java) {
		copy("SamplePlayer", java);
		Player processed = null;
		try {
			processed = SourceProcessor.processSource(fileName1);
		} catch (Throwable t) {
			fail("Unexcpected exception was thrown : " + t.getMessage());
		}
		assertNotNull(processed);
	}

	/**
	 * Tests return compilation error in the message.
	 * The test player is implemented in Java.
	 */
	public void testValidateSourceWithCompilationErrorJava() {
		testValidateSourceWithCompilationError(true);
	}
	
	/**
	 * Tests return compilation error in the message.
	 * The test player is implemented in C++.
	 */
	public void testValidateSourceWithCompilationErrorCPP() {
		testValidateSourceWithCompilationError(false);
	}
	
	private void testValidateSourceWithCompilationError(boolean java) {
		copy("CompilationErrorPlayer", java);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.startsWith("Compilation error!"));
	}

	/**
	 * Tests return exception in the message.
	 * The test player is implemented in Java.
	 */
	public void testValidateSourceWithExceptionJava() {
		testValidateSourceWithException(true);
	}
	
	/**
	 * Tests return exception in the message.
	 * The test player is implemented in C++.
	 */
	public void testValidateSourceWithExceptionCPP() {
		testValidateSourceWithException(false);
	}
	
	private void testValidateSourceWithException(boolean java) {
		copy("ExceptionPlayer", java);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.startsWith("Problems with the implemented protocol!"));
		assertTrue(res.indexOf(GameOverStatus.EXCEPTION.toString()) != -1);
	}

	/**
	 * Tests return time out in the message.
	 * The test player is implemented in Java.
	 */
	public void testValidateSourceWithTimeOutJava() {
		testValidateSourceWithTimeOut(true);
	}
	
	/**
	 * Tests return time out in the message.
	 * The test player is implemented in C++.
	 */
	public void testValidateSourceWithTimeOutCPP() {
		testValidateSourceWithTimeOut(false);
	}
	
	private void testValidateSourceWithTimeOut(boolean java) {
		copy("TimedoutMovePlayer", java);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.startsWith("Problems with the implemented protocol!"));
		assertTrue(res.indexOf(GameOverStatus.TIMEDOUT.toString()) != -1);
	}

	/**
	 * Tests return exception in the message.
	 * The test player is implemented in Java.
	 */
	public void testValidateSourceWithPrematureExitJava() {
		testValidateSourceWithPrematureExit(true);
	}
	
	/**
	 * Tests return exception in the message.
	 * The test player is implemented in C++.
	 */
	public void testValidateSourceWithPrematureExitCPP() {
		testValidateSourceWithPrematureExit(false);
	}
	
	private void testValidateSourceWithPrematureExit(boolean java) {
		copy("PrematureExitPlayer", java);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.startsWith("Problems with the implemented protocol!"));
		assertTrue(res.indexOf(GameOverStatus.EXCEPTION.toString()) != -1);
	}

	/**
	 * Tests return invalid move in the message.
	 * The test player is implemented in Java.
	 */
	public void testValidateSourceWithEmptyMoveJava() {
		testValidateSourceWithEmptyMove(true);
	}
	
	/**
	 * Tests return invalid move in the message.
	 * The test player is implemented in C++.
	 */
	public void testValidateSourceWithEmptyMoveCPP() {
		testValidateSourceWithEmptyMove(false);
	}
	
	private void testValidateSourceWithEmptyMove(boolean java) {
		copy("EmptyMovePlayer", java);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.startsWith("Problems with the implemented protocol!"));
		assertTrue(res.indexOf(GameOverStatus.INVALID_MOVE.toString()) != -1);
	}

	/**
	 * Tests return invalid move in the message.
	 * The test player is implemented in Java.
	 */
	public void testValidateSourceWithInvalidMoveJava() {
		testValidateSourceWithInvalidMove(true);
	}
	
	/**
	 * Tests return invalid move in the message.
	 * The test player is implemented in C++.
	 */
	public void testValidateSourceWithInvalidMoveCPP() {
		testValidateSourceWithInvalidMove(false);
	}
	
	private void testValidateSourceWithInvalidMove(boolean java) {
		copy("InvalidMovePlayer", java);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.startsWith("Problems with the implemented protocol!"));
		assertTrue(res.indexOf(GameOverStatus.INVALID_MOVE.toString()) != -1);
	}

	/**
	 * Tests return successfully in the message.
	 * The test player is implemented in Java.
	 */
	public void testValidateSourceWithNormalJava() {
		testValidateSourceWithNormal(true);
	}
	
	/**
	 * Tests return successfully in the message.
	 * The test player is implemented in C++.
	 */
	public void testValidateSourceWithNormalCPP() {
		testValidateSourceWithNormal(false);
	}
	
	private void testValidateSourceWithNormal(boolean java) {
		copy("SamplePlayer", java);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.startsWith("Validation successful!"));
	}

	private void copy(String name1, boolean java) {
		String dirName1 = testDir.getAbsolutePath() + File.separatorChar
				+ name1.toLowerCase();

		File dir1 = new File(dirName1);
		dir1.mkdirs();

		fileName1 = dir1.getAbsolutePath() + File.separatorChar + name1
				+ (java ? ".java" : ".cpp");

		String samplesPath = new File((java ? samplesJava : samplesCPP).replace(
				'/', File.separatorChar)).getAbsolutePath();
		try {
			copyFile(samplesPath + File.separatorChar + name1
					+ (java ? ".java" : ".cpp"), fileName1);
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
				if (line.startsWith("//")) line = line.substring(2);
				fw.write(line + "\n");
			}
		} finally {
			fr.close();
			fw.flush();
			fw.close();
		}

	}

	private void cleanup() {
		if (testDir.exists()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ignored) {
			}
			assertTrue("Could not delete files", delete(testDir));
		}

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
