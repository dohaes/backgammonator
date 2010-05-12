package backgammonator.test.logger;

import java.io.File;

import backgammonator.impl.logger.TournamentLoggerFactory;
import backgammonator.lib.logger.TournamentLogger;
import junit.framework.TestCase;

/**
 * Tests classes which implement the {@link TournamentLogger} interface.
 */
public class TournamentLoggerTestCase extends TestCase {

	private TournamentLogger tournamentLogger;

	@Override
	protected void setUp() throws Exception {
		this.tournamentLogger = TournamentLoggerFactory
				.getLogger(TournamentLoggerFactory.HTML);
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		String fileName = System.getProperty("tournament.logger.outputdir")
				+ "\\" + this.tournamentLogger.getFilename();
		System.out.print(fileName + "\n");
		File file = new File(fileName);

		// Make sure the file or directory exists and isn't write protected
		if (!file.exists()) throw new IllegalArgumentException(
				"Delete: no such file or directory: " + fileName);

		if (!file.canWrite()) throw new IllegalArgumentException(
				"Delete: write protected: " + fileName);

		Thread.sleep(10);

		// Attempt to delete it
		boolean success = file.delete();

		if (!success) throw new IllegalArgumentException(
				"Delete: deletion failed");

		super.tearDown();
	}

}
