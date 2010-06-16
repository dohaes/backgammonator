package backgammonator.impl.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import backgammonator.lib.game.Game;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;
import backgammonator.lib.logger.TournamentLogger;
import backgammonator.lib.tournament.TournamentType;
import backgammonator.util.BackgammonatorConfig;
import backgammonator.util.Debug;

/**
 * This class implements the TournamentLogger interface. Represents the log as
 * an html document.
 */

public class HTMLTournamentLogger implements TournamentLogger {

	private StringBuffer logStringBuffer;
	private String timestamp;
	private int gameNo;

	private static String outputdir = BackgammonatorConfig.getProperty(
			"backgammonator.logger.outputDir", "reports").replace(
			'/', File.separatorChar);

	/**
	 * @see TournamentLogger#endTournament(Player)
	 */
	@Override
	public void endTournament(Player winner) {

		this.logStringBuffer
				.append("</table>\n<br />\n<table border=\"1\">\n<tr><td align=\"center\"><b>Results</b></td><tr>\n<tr><td>");
		this.logStringBuffer.append(winner.getName());
		this.logStringBuffer
				.append(" is the tournament's winner!</td></tr>\n</table>\n</body></html>");

		try {
			File outputDir = new File(outputdir);
			if (!outputDir.exists()) {
				outputDir.mkdirs();
			}
			File file = new File(outputDir, this.getFilename());
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(this.logStringBuffer.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			Debug.getInstance().error("Error writing to file",
					Debug.TOURNAMENT_LOGGER, e);
		}

	}

	/**
	 * @see TournamentLogger#getFilename()
	 */
	@Override
	public String getFilename() {
		return "tournament_"
				+ this.timestamp.replace(':', '.').replace(' ', '_') + ".html";
	}

	/**
	 * @see TournamentLogger#getTournamentTimestamp()
	 */
	@Override
	public String getTournamentTimestamp() {
		return timestamp;
	}

	/**
	 * @see TournamentLogger#logGame(Player, Player, Game, GameOverStatus)
	 */
	@Override
	public void logGame(Player whitePlayer, Player blackPlayer, Game game,
			GameOverStatus status) {
		this.logStringBuffer.append("<tr><td>");
		this.logStringBuffer.append(this.gameNo);
		this.logStringBuffer.append("</td><td>");
		this.logStringBuffer.append(whitePlayer.getName());
		this.logStringBuffer.append("</td><td>");
		this.logStringBuffer.append(blackPlayer.getName());
		this.logStringBuffer.append("</td><td>");
		this.logStringBuffer.append(game.getWinner().getName());
		this.logStringBuffer.append("</td><td>");
		this.logStringBuffer.append(status.toString());
		this.logStringBuffer.append("</td><td><a href=\"");
		this.logStringBuffer.append(game.getFilename().replaceAll("\\\\", "/"));
		this.logStringBuffer
				.append("\" target=\"_blank\">View</a></td></tr>\n");
		this.gameNo++;
	}

	/**
	 * @see TournamentLogger#startTournament(List, TournamentType)
	 */
	@Override
	public void startTournament(List<Player> players, TournamentType type) {
		this.gameNo = 1;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = new Date();
		this.timestamp = dateFormat.format(date);

		this.logStringBuffer = new StringBuffer(
				"<html>\n<body>\n<h1 style=\"color:#0000FF\">Tournament - ");
		switch (type) {
		case BATTLE:
			this.logStringBuffer.append("Battle");
			break;
		case ELIMINATIONS:
			this.logStringBuffer.append("Eliminations");
			break;
		case GROUPS:
			this.logStringBuffer.append("Groups");
			break;

		default:
			break;
		}
		this.logStringBuffer.append("</h1>\n<h3>");
		this.logStringBuffer.append(this.timestamp);
		this.logStringBuffer
				.append("</h3><table border=\"1\">\n<tr><td align=\"center\"><b>Players</b></td><tr>\n");
		for (int i = 0; i < players.size(); i++) {
			this.logStringBuffer.append("<tr><td>");
			this.logStringBuffer.append(players.get(i).getName());
			this.logStringBuffer.append("</td></tr>\n");
		}
		this.logStringBuffer
				.append("</table>\n<br />\n<table border=\"1\">\n<tr><td colspan=6 align=\"center\"><b>Games</b></td><tr>\n<tr><td><b>#</b></td><td><b>White player</b></td><td><b>Black player</b></td><td><b>Winner</b></td><td><b>Game over status</b></td><td><b>Details</b></td></tr>\n");
	}
}
