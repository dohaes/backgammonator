package backgammonator.impl.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import backgammonator.lib.game.Dice;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;
import backgammonator.lib.game.PlayerColor;
import backgammonator.lib.game.PlayerMove;
import backgammonator.lib.logger.GameLogger;
import backgammonator.util.Debug;

/**
 * This class implements the Logger interface. Represents the log as an html
 * document.
 */

class HTMLGameLogger implements GameLogger {

	private Player whitePlayer;
	private Player blackPlayer;
	private StringBuffer logStringBuffer;
	private String timestamp;
	private int moveId;

	private static String outputdir = "reports"; // TODO should be configured

	static {
		System.setProperty("game.logger.outputdir", outputdir);
	}

	@Override
	public void startGame(Player whitePlayer, Player blackPlayer) {

		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;
		this.moveId = 1;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date date = new Date();
		this.timestamp = dateFormat.format(date);

		this.logStringBuffer = new StringBuffer(
				"<html>\n<body>\n<h1 style=\"color:#0000FF\">");
		this.logStringBuffer.append(whitePlayer.getName());
		this.logStringBuffer.append("(white) vs. ");
		this.logStringBuffer.append(blackPlayer.getName());
		this.logStringBuffer.append("(black)</h1>\n<h3>");
		this.logStringBuffer.append(this.timestamp);
		this.logStringBuffer
				.append("</h3>\n<table border=\"1\"><tr><td rowspan=2><b>#</b></td><td rowspan=2><b>Player</b></td><td rowspan=2><b>Die 1</b></td><td rowspan=2><b>Die 2</b></td><td colspan=2><b>Checker Move 1</b></td><td colspan=2><b>Checker Move 2</b></td><td rowspan=2><b>Hit checkers</b></td><td rowspan=2><b>Born off checkers</b></td></tr><tr><td><b>Start point</b></td><td><b>Move length</b></td><td><b>Start point</b></td><td><b>Move length</b></td></tr>\n");
	}

	@Override
	public void endGame(GameOverStatus status, PlayerColor winner) {

		String statusString;
		String textColor;

		switch (status) {
		case NORMAL:
			statusString = "he born off all checkers.";
			textColor = "#0B3B0B";
			break;
		case EXCEPTION:
			statusString = "exeption on other player's move.";
			textColor = "#FF0000";
			break;
		case INVALID_MOVE:
			statusString = "invalid move on other player.";
			textColor = "#FF0000";
			break;
		default:
			throw new IllegalArgumentException();
		}

		this.logStringBuffer.append("<tr style=\"color:");
		this.logStringBuffer.append(textColor);
		this.logStringBuffer.append("\"><td colspan=10>");
		this.logStringBuffer.append(winner.toString());
		this.logStringBuffer.append(" player wins the game - ");
		this.logStringBuffer.append(statusString);
		this.logStringBuffer.append("</td></tr></table></body></html>\n");

		try {
			File outputDir = new File(outputdir);
			if (!outputDir.exists()) {
				outputDir.mkdir();
			}
			File file = new File(outputDir, this.getFilename());
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(this.logStringBuffer.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			Debug.getInstance().error("Error writing to file",
					Debug.GAME_LOGGER, e);
		}

	}

	@Override
	public void logMove(PlayerMove move, PlayerColor color, Dice dice, int hit,
			int bornOff, boolean invalid) {
		String rowspan;
		if (move.isDouble()) {
			rowspan = "2";
		} else {
			rowspan = "1";
		}
		String textColor = invalid ? "#FF0000" : "#000000";
		this.logStringBuffer.append("<tr style=\"color:");
		this.logStringBuffer.append(textColor);
		this.logStringBuffer.append("\"><td>");
		this.logStringBuffer.append(this.moveId);
		this.logStringBuffer.append("</td><td>");
		this.logStringBuffer.append(color);
		this.logStringBuffer.append("</td><td>");
		this.logStringBuffer.append(dice.getDie1());
		this.logStringBuffer.append("</td><td>");
		this.logStringBuffer.append(dice.getDie2());
		this.logStringBuffer.append("</td><td>");
		this.logStringBuffer.append(move.getCheckerMove(0).getStartPoint());
		this.logStringBuffer.append("</td><td>");
		this.logStringBuffer.append(move.getCheckerMove(0).getDie());
		this.logStringBuffer.append("</td><td>");
		this.logStringBuffer.append(move.getCheckerMove(1).getStartPoint());
		this.logStringBuffer.append("</td><td>");
		this.logStringBuffer.append(move.getCheckerMove(1).getDie());
		this.logStringBuffer.append("</td><td rowspan=");
		this.logStringBuffer.append(rowspan);
		this.logStringBuffer.append(">");
		this.logStringBuffer.append(hit);
		this.logStringBuffer.append("</td><td rowspan=");
		this.logStringBuffer.append(rowspan);
		this.logStringBuffer.append(">");
		this.logStringBuffer.append(bornOff);
		this.logStringBuffer.append("</td></tr>\n");
		if (move.isDouble()) {
			this.logStringBuffer.append("<tr><td>");
			this.logStringBuffer.append(this.moveId);
			this.logStringBuffer.append("</td><td>");
			this.logStringBuffer.append(color);
			this.logStringBuffer.append("</td><td>");
			this.logStringBuffer.append(dice.getDie1());
			this.logStringBuffer.append("</td><td>");
			this.logStringBuffer.append(dice.getDie2());
			this.logStringBuffer.append("</td><td>");
			this.logStringBuffer.append(move.getCheckerMove(2).getStartPoint());
			this.logStringBuffer.append("</td><td>");
			this.logStringBuffer.append(move.getCheckerMove(2).getDie());
			this.logStringBuffer.append("</td><td>");
			this.logStringBuffer.append(move.getCheckerMove(3).getStartPoint());
			this.logStringBuffer.append("</td><td>");
			this.logStringBuffer.append(move.getCheckerMove(3).getDie());
			this.logStringBuffer.append("</td></tr>\n");
		}
		this.moveId++;
	}

	@Override
	public String getFilename() {
		return this.timestamp.replace(':', '.').replace(' ', '_') + "_"
				+ this.whitePlayer.getName() + "_" + this.blackPlayer.getName()
				+ ".html";
	}

	@Override
	public String getGameTimestamp() {
		return this.timestamp;
	}

}
