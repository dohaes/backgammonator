package backgammonator.test.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import backgammonator.impl.logger.TournamentLoggerFactory;
import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.Game;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;
import backgammonator.lib.game.PlayerMove;
import backgammonator.lib.logger.TournamentLogger;
import backgammonator.lib.tournament.TournamentType;
import backgammonator.util.BackgammonatorConfig;
import junit.framework.TestCase;

/**
 * Tests classes which implement the {@link TournamentLogger} interface.
 */
public class TournamentLoggerTestCase extends TestCase {

	private class TestPlayer implements Player {

		private String name;

		public TestPlayer(String name) {
			this.name = name;
		}

		@Override
		public void gameOver(BackgammonBoard board, boolean wins,
				GameOverStatus status) {
		}

		@SuppressWarnings("unused")
		@Override
		public PlayerMove getMove(BackgammonBoard board, Dice dice)
				throws Exception {
			return null;
		}

		@Override
		public String getName() {
			return this.name;
		}

	}

	private class TestGame implements Game {

		private Player winner;
		private String filename;

		public TestGame(Player winner, String filename) {
			this.winner = winner;
			this.filename = filename;
		}

		@Override
		public String getFilename() {
			return this.filename;
		}

		@Override
		public Player getWinner() {
			return this.winner;
		}

		@Override
		public GameOverStatus start() {
			return null;
		}

	}

	private TournamentLogger tournamentLogger;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		this.tournamentLogger = TournamentLoggerFactory
				.getLogger(TournamentLoggerFactory.HTML);
		super.setUp();
	}

	/**
	 * Tests logging battle tournament.
	 */
	public void testBattleTournament() {

		List<Player> players = new ArrayList<Player>();

		TestPlayer p1 = new TestPlayer("p1");
		TestPlayer p2 = new TestPlayer("p2");
		TestPlayer p3 = new TestPlayer("p3");
		players.add(p1);
		players.add(p2);
		players.add(p3);

		this.tournamentLogger.startTournament(players, TournamentType.BATTLE);

		Game g1 = new TestGame(p1, "filename1");
		this.tournamentLogger.logGame(p1, p2, g1, GameOverStatus.DOUBLE);
		Game g2 = new TestGame(p3, "filename2");
		this.tournamentLogger.logGame(p1, p3, g2, GameOverStatus.EXCEPTION);
		Game g3 = new TestGame(p2, "filename3");
		this.tournamentLogger.logGame(p2, p3, g3, GameOverStatus.TIMEDOUT);

		this.tournamentLogger.endTournament(p1);

		String filename = BackgammonatorConfig
				.getProperty("backgammonator.logger.outputDir")
				+ File.separator + this.tournamentLogger.getFilename();
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
						+ "<h1 style=\"color:#0000FF\">Tournament - Battle</h1>\n"
						+ "<h3>"
						+ this.tournamentLogger.getTournamentTimestamp()
						+ "</h3><table border=\"1\">\n"
						+ "<tr><td align=\"center\"><b>Players</b></td><tr>\n"
						+ "<tr><td>p1</td></tr>\n"
						+ "<tr><td>p2</td></tr>\n"
						+ "<tr><td>p3</td></tr>\n"
						+ "</table>\n"
						+ "<br />\n"
						+ "<table border=\"1\">\n"
						+ "<tr><td colspan=6 align=\"center\"><b>Games</b></td><tr>\n"
						+ "<tr><td><b>#</b></td><td><b>White player</b></td><td><b>Black player</b></td><td><b>Winner</b></td><td><b>Game over status</b></td><td><b>Details</b></td></tr>\n"
						+ "<tr><td>1</td><td>p1</td><td>p2</td><td>p1</td><td>double victory</td><td><a href=\"filename1\" target=\"_blank\">View</a></td></tr>\n"
						+ "<tr><td>2</td><td>p1</td><td>p3</td><td>p3</td><td>exception</td><td><a href=\"filename2\" target=\"_blank\">View</a></td></tr>\n"
						+ "<tr><td>3</td><td>p2</td><td>p3</td><td>p2</td><td>timed out</td><td><a href=\"filename3\" target=\"_blank\">View</a></td></tr>\n"
						+ "</table>\n" + "<br />\n" + "<table border=\"1\">\n"
						+ "<tr><td align=\"center\"><b>Results</b></td><tr>\n"
						+ "<tr><td>p1 is the tournament's winner!</td></tr>\n"
						+ "</table>\n" + "</body></html>");
	}

	/**
	 * Tests logging eliminations tournament.
	 */
	public void testEliminationsTournament() {

		List<Player> players = new ArrayList<Player>();

		TestPlayer p1 = new TestPlayer("p1");
		TestPlayer p2 = new TestPlayer("p2");
		TestPlayer p3 = new TestPlayer("p3");
		players.add(p1);
		players.add(p2);
		players.add(p3);

		this.tournamentLogger.startTournament(players, TournamentType.ELIMINATIONS);

		Game g1 = new TestGame(p1, "filename1");
		this.tournamentLogger.logGame(p1, p2, g1, GameOverStatus.DOUBLE);
		Game g2 = new TestGame(p3, "filename2");
		this.tournamentLogger.logGame(p1, p3, g2, GameOverStatus.EXCEPTION);
		Game g3 = new TestGame(p2, "filename3");
		this.tournamentLogger.logGame(p2, p3, g3, GameOverStatus.TIMEDOUT);

		this.tournamentLogger.endTournament(p1);

		String filename = BackgammonatorConfig
				.getProperty("backgammonator.logger.outputDir")
				+ File.separator + this.tournamentLogger.getFilename();
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
						+ "<h1 style=\"color:#0000FF\">Tournament - Eliminations</h1>\n"
						+ "<h3>"
						+ this.tournamentLogger.getTournamentTimestamp()
						+ "</h3><table border=\"1\">\n"
						+ "<tr><td align=\"center\"><b>Players</b></td><tr>\n"
						+ "<tr><td>p1</td></tr>\n"
						+ "<tr><td>p2</td></tr>\n"
						+ "<tr><td>p3</td></tr>\n"
						+ "</table>\n"
						+ "<br />\n"
						+ "<table border=\"1\">\n"
						+ "<tr><td colspan=6 align=\"center\"><b>Games</b></td><tr>\n"
						+ "<tr><td><b>#</b></td><td><b>White player</b></td><td><b>Black player</b></td><td><b>Winner</b></td><td><b>Game over status</b></td><td><b>Details</b></td></tr>\n"
						+ "<tr><td>1</td><td>p1</td><td>p2</td><td>p1</td><td>double victory</td><td><a href=\"filename1\" target=\"_blank\">View</a></td></tr>\n"
						+ "<tr><td>2</td><td>p1</td><td>p3</td><td>p3</td><td>exception</td><td><a href=\"filename2\" target=\"_blank\">View</a></td></tr>\n"
						+ "<tr><td>3</td><td>p2</td><td>p3</td><td>p2</td><td>timed out</td><td><a href=\"filename3\" target=\"_blank\">View</a></td></tr>\n"
						+ "</table>\n" + "<br />\n" + "<table border=\"1\">\n"
						+ "<tr><td align=\"center\"><b>Results</b></td><tr>\n"
						+ "<tr><td>p1 is the tournament's winner!</td></tr>\n"
						+ "</table>\n" + "</body></html>");
	}
	
	/**
	 * Tests logging groups tournament.
	 */
	public void testGroupsTournament() {

		List<Player> players = new ArrayList<Player>();

		TestPlayer p1 = new TestPlayer("p1");
		TestPlayer p2 = new TestPlayer("p2");
		TestPlayer p3 = new TestPlayer("p3");
		players.add(p1);
		players.add(p2);
		players.add(p3);

		this.tournamentLogger.startTournament(players, TournamentType.GROUPS);

		Game g1 = new TestGame(p1, "filename1");
		this.tournamentLogger.logGame(p1, p2, g1, GameOverStatus.DOUBLE);
		Game g2 = new TestGame(p3, "filename2");
		this.tournamentLogger.logGame(p1, p3, g2, GameOverStatus.EXCEPTION);
		Game g3 = new TestGame(p2, "filename3");
		this.tournamentLogger.logGame(p2, p3, g3, GameOverStatus.TIMEDOUT);

		this.tournamentLogger.endTournament(p1);

		String filename = BackgammonatorConfig
				.getProperty("backgammonator.logger.outputDir")
				+ File.separator + this.tournamentLogger.getFilename();
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
						+ "<h1 style=\"color:#0000FF\">Tournament - Groups</h1>\n"
						+ "<h3>"
						+ this.tournamentLogger.getTournamentTimestamp()
						+ "</h3><table border=\"1\">\n"
						+ "<tr><td align=\"center\"><b>Players</b></td><tr>\n"
						+ "<tr><td>p1</td></tr>\n"
						+ "<tr><td>p2</td></tr>\n"
						+ "<tr><td>p3</td></tr>\n"
						+ "</table>\n"
						+ "<br />\n"
						+ "<table border=\"1\">\n"
						+ "<tr><td colspan=6 align=\"center\"><b>Games</b></td><tr>\n"
						+ "<tr><td><b>#</b></td><td><b>White player</b></td><td><b>Black player</b></td><td><b>Winner</b></td><td><b>Game over status</b></td><td><b>Details</b></td></tr>\n"
						+ "<tr><td>1</td><td>p1</td><td>p2</td><td>p1</td><td>double victory</td><td><a href=\"filename1\" target=\"_blank\">View</a></td></tr>\n"
						+ "<tr><td>2</td><td>p1</td><td>p3</td><td>p3</td><td>exception</td><td><a href=\"filename2\" target=\"_blank\">View</a></td></tr>\n"
						+ "<tr><td>3</td><td>p2</td><td>p3</td><td>p2</td><td>timed out</td><td><a href=\"filename3\" target=\"_blank\">View</a></td></tr>\n"
						+ "</table>\n" + "<br />\n" + "<table border=\"1\">\n"
						+ "<tr><td align=\"center\"><b>Results</b></td><tr>\n"
						+ "<tr><td>p1 is the tournament's winner!</td></tr>\n"
						+ "</table>\n" + "</body></html>");
	}
	
	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		String fileName = BackgammonatorConfig
				.getProperty("backgammonator.logger.outputDir")
				+ File.separator + this.tournamentLogger.getFilename();
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

		System.out.print("File deleted:" + fileName + "\n");

		super.tearDown();
	}

}
