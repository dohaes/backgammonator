package backgammonator.test.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import backgammonator.core.CheckerMove;
import backgammonator.core.GameLogger;
import backgammonator.core.GameOverStatus;
import backgammonator.core.PlayerColor;
import backgammonator.core.PlayerMove;
import backgammonator.impl.game.DiceImpl;
import backgammonator.impl.logger.GameLoggerFactory;
import backgammonator.sample.players.interfacce.SamplePlayer;
import junit.framework.TestCase;

/**
 * Tests classes which implement the {@link GameLogger} interface.
 */
public class GameLoggerTestCase extends TestCase {

	private GameLogger gameLogger;

	@Override
	protected void setUp() throws Exception {
		this.gameLogger = GameLoggerFactory.getLogger(GameLoggerFactory.HTML);
		SamplePlayer whitePlayer = new SamplePlayer(1);
		SamplePlayer blackPlayer = new SamplePlayer(2);
		this.gameLogger.startGame(whitePlayer, blackPlayer);

		super.setUp();
	}

	public void testRegularEndGame() {
		this.gameLogger.logMove(new PlayerMove(new CheckerMove[] {
				new CheckerMove(1, 5), new CheckerMove(1, 2) }),
				PlayerColor.WHITE, new DiceImpl(5, 2), 0, 0, false);
		this.gameLogger.logMove(new PlayerMove(new CheckerMove[] {
				new CheckerMove(4, 2), new CheckerMove(15, 2),
				new CheckerMove(15, 2), new CheckerMove(15, 2) }),
				PlayerColor.BLACK, new DiceImpl(2, 2), 1, 15, false);
		this.gameLogger.endGame(GameOverStatus.NORMAL, PlayerColor.BLACK);

		String filename = System.getProperty("game.logger.outputdir") + "\\"
				+ this.gameLogger.getFilename();
		String resultFileContent = "";
		try {
			int len = (int) (new File(filename).length());
			FileInputStream fis = new FileInputStream(filename);
			byte buf[] = new byte[len];
			fis.read(buf);
			fis.close();
			resultFileContent = new String(buf, Charset.defaultCharset());
		} catch (IOException e) {
			System.err.println(e);
		}
		assertEquals(
				resultFileContent,
				"<html>\n<body>\n"
						+ "<h1 style=\"color:#0000FF\">Dummy G 1(white) vs. Dummy G 2(black)</h1>\n"
						+ "<h3>"
						+ this.gameLogger.getGameTimestamp()
						+ "</h3>\n"
						+ "<table border=\"1\">"
						+ "<tr><td rowspan=2><b>#</b></td><td rowspan=2><b>Player</b></td><td rowspan=2><b>Die 1</b></td><td rowspan=2><b>Die 2</b></td><td colspan=2><b>Checker Move 1</b></td><td colspan=2><b>Checker Move 2</b></td><td rowspan=2><b>Hit checkers</b></td><td rowspan=2><b>Born off checkers</b></td></tr>"
						+ "<tr><td><b>Start point</b></td><td><b>Move length</b></td><td><b>Start point</b></td><td><b>Move length</b></td></tr>\n"
						+ "<tr style=\"color:#000000\"><td>1</td><td>white</td><td>5</td><td>2</td><td>1</td><td>5</td><td>1</td><td>2</td><td rowspan=1>0</td><td rowspan=1>0</td></tr>\n"
						+ "<tr style=\"color:#000000\"><td>2</td><td>black</td><td>2</td><td>2</td><td>4</td><td>2</td><td>15</td><td>2</td><td rowspan=2>1</td><td rowspan=2>15</td></tr>\n"
						+ "<tr><td>2</td><td>black</td><td>2</td><td>2</td><td>15</td><td>2</td><td>15</td><td>2</td></tr>\n"
						+ "<tr style=\"color:#0B3B0B\"><td colspan=10>black player wins the game - he born off all checkers.</td></tr></table></body></html>\n");
	}

	public void testInvalidMoveEndGame() {
		this.gameLogger.logMove(new PlayerMove(new CheckerMove[] {
				new CheckerMove(1, 5), new CheckerMove(1, 2) }),
				PlayerColor.WHITE, new DiceImpl(5, 2), 0, 0, false);
		this.gameLogger.logMove(new PlayerMove(new CheckerMove[] {
				new CheckerMove(4, 2), new CheckerMove(15, 2),
				new CheckerMove(15, 2), new CheckerMove(15, 2) }),
				PlayerColor.BLACK, new DiceImpl(2, 2), 1, 15, true);
		this.gameLogger.endGame(GameOverStatus.INVALID_MOVE, PlayerColor.WHITE);

		String filename = System.getProperty("game.logger.outputdir") + "\\"
				+ this.gameLogger.getFilename();
		String resultFileContent = "";
		try {
			int len = (int) (new File(filename).length());
			FileInputStream fis = new FileInputStream(filename);
			byte buf[] = new byte[len];
			fis.read(buf);
			fis.close();
			resultFileContent = new String(buf, Charset.defaultCharset());
		} catch (IOException e) {
			System.err.println(e);
		}
		assertEquals(
				resultFileContent,
				"<html>\n<body>\n"
						+ "<h1 style=\"color:#0000FF\">Dummy G 1(white) vs. Dummy G 2(black)</h1>\n"
						+ "<h3>"
						+ this.gameLogger.getGameTimestamp()
						+ "</h3>\n"
						+ "<table border=\"1\">"
						+ "<tr><td rowspan=2><b>#</b></td><td rowspan=2><b>Player</b></td><td rowspan=2><b>Die 1</b></td><td rowspan=2><b>Die 2</b></td><td colspan=2><b>Checker Move 1</b></td><td colspan=2><b>Checker Move 2</b></td><td rowspan=2><b>Hit checkers</b></td><td rowspan=2><b>Born off checkers</b></td></tr>"
						+ "<tr><td><b>Start point</b></td><td><b>Move length</b></td><td><b>Start point</b></td><td><b>Move length</b></td></tr>\n"
						+ "<tr style=\"color:#000000\"><td>1</td><td>white</td><td>5</td><td>2</td><td>1</td><td>5</td><td>1</td><td>2</td><td rowspan=1>0</td><td rowspan=1>0</td></tr>\n"
						+ "<tr style=\"color:#FF0000\"><td>2</td><td>black</td><td>2</td><td>2</td><td>4</td><td>2</td><td>15</td><td>2</td><td rowspan=2>1</td><td rowspan=2>15</td></tr>\n"
						+ "<tr><td>2</td><td>black</td><td>2</td><td>2</td><td>15</td><td>2</td><td>15</td><td>2</td></tr>\n"
						+ "<tr style=\"color:#FF0000\"><td colspan=10>white player wins the game - invalid move on other player.</td></tr></table></body></html>\n");
	}

	public void testExceptionEndGame() {
		this.gameLogger.logMove(new PlayerMove(new CheckerMove[] {
				new CheckerMove(1, 5), new CheckerMove(1, 2) }),
				PlayerColor.WHITE, new DiceImpl(5, 2), 0, 0, false);
		this.gameLogger.logMove(new PlayerMove(new CheckerMove[] {
				new CheckerMove(4, 2), new CheckerMove(15, 2),
				new CheckerMove(15, 2), new CheckerMove(15, 2) }),
				PlayerColor.BLACK, new DiceImpl(2, 2), 1, 15, false);
		this.gameLogger.endGame(GameOverStatus.EXCEPTION, PlayerColor.WHITE);

		String filename = System.getProperty("game.logger.outputdir") + "\\"
				+ this.gameLogger.getFilename();
		String resultFileContent = "";
		try {
			int len = (int) (new File(filename).length());
			FileInputStream fis = new FileInputStream(filename);
			byte buf[] = new byte[len];
			fis.read(buf);
			fis.close();
			resultFileContent = new String(buf, Charset.defaultCharset());
		} catch (IOException e) {
			System.err.println(e);
		}
		assertEquals(
				resultFileContent,
				"<html>\n<body>\n"
						+ "<h1 style=\"color:#0000FF\">Dummy G 1(white) vs. Dummy G 2(black)</h1>\n"
						+ "<h3>"
						+ this.gameLogger.getGameTimestamp()
						+ "</h3>\n"
						+ "<table border=\"1\">"
						+ "<tr><td rowspan=2><b>#</b></td><td rowspan=2><b>Player</b></td><td rowspan=2><b>Die 1</b></td><td rowspan=2><b>Die 2</b></td><td colspan=2><b>Checker Move 1</b></td><td colspan=2><b>Checker Move 2</b></td><td rowspan=2><b>Hit checkers</b></td><td rowspan=2><b>Born off checkers</b></td></tr>"
						+ "<tr><td><b>Start point</b></td><td><b>Move length</b></td><td><b>Start point</b></td><td><b>Move length</b></td></tr>\n"
						+ "<tr style=\"color:#000000\"><td>1</td><td>white</td><td>5</td><td>2</td><td>1</td><td>5</td><td>1</td><td>2</td><td rowspan=1>0</td><td rowspan=1>0</td></tr>\n"
						+ "<tr style=\"color:#000000\"><td>2</td><td>black</td><td>2</td><td>2</td><td>4</td><td>2</td><td>15</td><td>2</td><td rowspan=2>1</td><td rowspan=2>15</td></tr>\n"
						+ "<tr><td>2</td><td>black</td><td>2</td><td>2</td><td>15</td><td>2</td><td>15</td><td>2</td></tr>\n"
						+ "<tr style=\"color:#FF0000\"><td colspan=10>white player wins the game - exeption on other player's move.</td></tr></table></body></html>\n");
	}

	@Override
	protected void tearDown() throws Exception {
		String fileName = System.getProperty("game.logger.outputdir") + "\\"
				+ this.gameLogger.getFilename();
		System.out.print(fileName + "\n");
		File file = new File(fileName);

		// Make sure the file or directory exists and isn't write protected
		if (!file.exists())
			throw new IllegalArgumentException(
					"Delete: no such file or directory: " + fileName);

		if (!file.canWrite())
			throw new IllegalArgumentException("Delete: write protected: "
					+ fileName);

		Thread.sleep(10);

		// Attempt to delete it
		boolean success = file.delete();

		if (!success)
			throw new IllegalArgumentException("Delete: deletion failed");

		super.tearDown();
	}
}
