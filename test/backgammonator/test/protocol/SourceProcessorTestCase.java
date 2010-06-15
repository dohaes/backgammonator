package backgammonator.test.protocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import backgammonator.impl.protocol.SourceProcessor;
import backgammonator.lib.game.GameOverStatus;
import junit.framework.TestCase;

/**
 * Tests class {@link SourceProcessor}.
 */
public class SourceProcessorTestCase extends TestCase {

	/** path to java samples **/
	private static String samplesJava = "sample/backgammonator/sample/player/protocol/java";
	private static String samplesC = "sample/backgammonator/sample/player/protocol/c";
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
	 * Test return exception in the message.
	 */
	public void testValidateSourceWithExc() {
		copy("ExceptionPlayer", true);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.indexOf(GameOverStatus.EXCEPTION.toString()) != -1);
	}

	/**
	 * Test return time out in the message.
	 */
	public void testValidateSourceWithTimeOut() {
		copy("TimedoutMovePlayer", true);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.indexOf(GameOverStatus.TIMEDOUT.toString()) != -1);
	}

	/**
	 * Test return exception in the message.
	 */
	public void testValidateSourceWithPremature() {
		copy("PrematureExitPlayer", true);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.indexOf(GameOverStatus.EXCEPTION.toString()) != -1);
	}

	/**
	 * Test return invalid move in the message.
	 */
	public void testValidateSourceWithEmptyMove() {
		copy("EmptyMovePlayer", true);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.indexOf(GameOverStatus.INVALID_MOVE.toString()) != -1);
	}

	/**
	 * Test return invalid move in the message.
	 */
	public void testValidateSourceWithInvalidMove() {
		copy("InvalidMovePlayer", true);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.indexOf(GameOverStatus.INVALID_MOVE.toString()) != -1);
	}

	/**
	 * Test return successfully in the message.
	 */
	public void testValidateSourceWithNormal() {
		copy("SamplePlayer", true);
		String res = SourceProcessor.validateSource(fileName1);
		assertTrue(res.indexOf("success") != -1);
	}

	private void copy(String name1, boolean java) {
		String dirName1 = testDir.getAbsolutePath() + File.separatorChar
				+ name1.toLowerCase();

		File dir1 = new File(dirName1);
		dir1.mkdirs();

		fileName1 = dir1.getAbsolutePath() + File.separatorChar + name1
				+ ".java";

		String samplesPath = new File((java ? samplesJava : samplesC).replace(
				'/', File.separatorChar)).getAbsolutePath();
		try {
			copyFile(samplesPath + File.separatorChar + name1
					+ (java ? ".java" : ".c"), fileName1);
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
