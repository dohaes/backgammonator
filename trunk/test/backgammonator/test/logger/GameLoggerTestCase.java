package backgammonator.test.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import backgammonator.impl.game.BackgammonBoardImpl;
import backgammonator.impl.game.DiceImpl;
import backgammonator.impl.logger.GameLoggerFactory;
import backgammonator.lib.game.CheckerMove;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.PlayerColor;
import backgammonator.lib.game.PlayerMove;
import backgammonator.lib.logger.GameLogger;
import backgammonator.sample.players.interfacce.SamplePlayer;
import backgammonator.util.BackgammonatorConfig;
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
				new CheckerMove(1, 5), new CheckerMove(1, 2) }), new DiceImpl(
				5, 2), false, new BackgammonBoardImpl());
		this.gameLogger.logMove(new PlayerMove(new CheckerMove[] {
				new CheckerMove(4, 2), new CheckerMove(15, 2),
				new CheckerMove(15, 2), new CheckerMove(15, 2) }),
				new DiceImpl(2, 2), false, new BackgammonBoardImpl());
		this.gameLogger.endGame(GameOverStatus.NORMAL, PlayerColor.BLACK);

		String filename = BackgammonatorConfig.getProperty("backgammonator.game.loggerOutputDir")
				+ File.separator + this.gameLogger.getFilename();
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
						+ "<h1 style=\"color:#0000FF\">Sample G 1(white) vs. Sample G 2(black)</h1>\n"
						+ "<h3>"
						+ this.gameLogger.getGameTimestamp()
						+ "</h3>\n"
						+ "<table border=\"1\"><tr><td rowspan=2><b>#</b></td><td rowspan=2><b>Player</b></td><td rowspan=2><b>Die 1</b></td><td rowspan=2><b>Die 2</b></td><td colspan=2><b>Checker Move 1</b></td><td colspan=2><b>Checker Move 2</b></td><td rowspan=2><b>Hit checkers</b></td><td rowspan=2><b>Born off checkers</b></td><td rowspan=2><b>Details</b></td></tr><tr><td><b>Start point</b></td><td><b>Move length</b></td><td><b>Start point</b></td><td><b>Move length</b></td></tr>\n<tr style=\"color:#000000\"><td rowspan=1>1</td><td>black</td><td>5</td><td>2</td><td>1</td><td>5</td><td>1</td><td>2</td><td rowspan=1>0</td><td rowspan=1>0</td><td rowspan=1><a href=\"moves/1.html\" target=\"_blank\">View</a></td></tr>\n<tr style=\"color:#000000\"><td rowspan=2>2</td><td>black</td><td>2</td><td>2</td><td>4</td><td>2</td><td>15</td><td>2</td><td rowspan=2>0</td><td rowspan=2>0</td><td rowspan=2><a href=\"moves/2.html\" target=\"_blank\">View</a></td></tr>\n<tr><td>black</td><td>2</td><td>2</td><td>15</td><td>2</td><td>15</td><td>2</td></tr>\n<tr style=\"color:#0B3B0B\"><td colspan=11>black player wins the game - normal victory</td></tr></table></body></html>\n");
	}

	public void testInvalidMoveEndGame() {
		this.gameLogger.logMove(new PlayerMove(new CheckerMove[] {
				new CheckerMove(1, 5), new CheckerMove(1, 2) }), new DiceImpl(5, 2), false, new BackgammonBoardImpl());
		this.gameLogger.logMove(new PlayerMove(new CheckerMove[] {
				new CheckerMove(4, 2), new CheckerMove(15, 2),
				new CheckerMove(15, 2), new CheckerMove(15, 2) }), new DiceImpl(2, 2), true, new BackgammonBoardImpl());
		this.gameLogger.endGame(GameOverStatus.INVALID_MOVE, PlayerColor.WHITE);

		String filename = BackgammonatorConfig.getProperty("backgammonator.game.loggerOutputDir") + "\\"
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
						+ "<h1 style=\"color:#0000FF\">Sample G 1(white) vs. Sample G 2(black)</h1>\n"
						+ "<h3>"
						+ this.gameLogger.getGameTimestamp()
						+ "</h3>\n"
						+ "<table border=\"1\"><tr><td rowspan=2><b>#</b></td><td rowspan=2><b>Player</b></td><td rowspan=2><b>Die 1</b></td><td rowspan=2><b>Die 2</b></td><td colspan=2><b>Checker Move 1</b></td><td colspan=2><b>Checker Move 2</b></td><td rowspan=2><b>Hit checkers</b></td><td rowspan=2><b>Born off checkers</b></td><td rowspan=2><b>Details</b></td></tr><tr><td><b>Start point</b></td><td><b>Move length</b></td><td><b>Start point</b></td><td><b>Move length</b></td></tr>\n<tr style=\"color:#000000\"><td rowspan=1>1</td><td>black</td><td>5</td><td>2</td><td>1</td><td>5</td><td>1</td><td>2</td><td rowspan=1>0</td><td rowspan=1>0</td><td rowspan=1><a href=\"moves/1.html\" target=\"_blank\">View</a></td></tr>\n<tr style=\"color:#FF0000\"><td rowspan=2>2</td><td>black</td><td>2</td><td>2</td><td>4</td><td>2</td><td>15</td><td>2</td><td rowspan=2>0</td><td rowspan=2>0</td><td rowspan=2><a href=\"moves/2.html\" target=\"_blank\">View</a></td></tr>\n<tr><td>black</td><td>2</td><td>2</td><td>15</td><td>2</td><td>15</td><td>2</td></tr>\n<tr style=\"color:#FF0000\"><td colspan=11>white player wins the game - invalid move</td></tr></table></body></html>\n");
	}

	public void testExceptionEndGame() {
		this.gameLogger.logMove(new PlayerMove(new CheckerMove[] {
				new CheckerMove(1, 5), new CheckerMove(1, 2) }), new DiceImpl(5, 2), false, new BackgammonBoardImpl());
		this.gameLogger.logMove(new PlayerMove(new CheckerMove[] {
				new CheckerMove(4, 2), new CheckerMove(15, 2),
				new CheckerMove(15, 2), new CheckerMove(15, 2) }), new DiceImpl(2, 2), false, new BackgammonBoardImpl());
		this.gameLogger.endGame(GameOverStatus.EXCEPTION, PlayerColor.WHITE);

		String filename = BackgammonatorConfig.getProperty("backgammonator.game.loggerOutputDir") + "\\"
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
						+ "<h1 style=\"color:#0000FF\">Sample G 1(white) vs. Sample G 2(black)</h1>\n"
						+ "<h3>"
						+ this.gameLogger.getGameTimestamp()
						+ "</h3>\n"
						+ "<table border=\"1\"><tr><td rowspan=2><b>#</b></td><td rowspan=2><b>Player</b></td><td rowspan=2><b>Die 1</b></td><td rowspan=2><b>Die 2</b></td><td colspan=2><b>Checker Move 1</b></td><td colspan=2><b>Checker Move 2</b></td><td rowspan=2><b>Hit checkers</b></td><td rowspan=2><b>Born off checkers</b></td><td rowspan=2><b>Details</b></td></tr><tr><td><b>Start point</b></td><td><b>Move length</b></td><td><b>Start point</b></td><td><b>Move length</b></td></tr>\n<tr style=\"color:#000000\"><td rowspan=1>1</td><td>black</td><td>5</td><td>2</td><td>1</td><td>5</td><td>1</td><td>2</td><td rowspan=1>0</td><td rowspan=1>0</td><td rowspan=1><a href=\"moves/1.html\" target=\"_blank\">View</a></td></tr>\n<tr style=\"color:#000000\"><td rowspan=2>2</td><td>black</td><td>2</td><td>2</td><td>4</td><td>2</td><td>15</td><td>2</td><td rowspan=2>0</td><td rowspan=2>0</td><td rowspan=2><a href=\"moves/2.html\" target=\"_blank\">View</a></td></tr>\n<tr><td>black</td><td>2</td><td>2</td><td>15</td><td>2</td><td>15</td><td>2</td></tr>\n<tr style=\"color:#FF0000\"><td colspan=11>white player wins the game - exception</td></tr></table></body></html>\n");
	}

	@Override
	protected void tearDown() throws Exception {
		String fileName = BackgammonatorConfig.getProperty("backgammonator.game.loggerOutputDir") + "\\"
				+ this.gameLogger.getFilename();
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
