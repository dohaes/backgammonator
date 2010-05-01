package backgammonator.impl.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import backgammonator.core.Dice;
import backgammonator.core.GameLogger;
import backgammonator.core.GameOverStatus;
import backgammonator.core.Player;
import backgammonator.core.PlayerColor;
import backgammonator.core.PlayerMove;
import backgammonator.util.Debug;

/**
 * This class implements the Logger interface. Represents the log as an html
 * document.
 */

class HTMLLogger implements GameLogger {

	private Player whitePlayer;
	private Player blackPlayer;
	private StringBuffer logStringBuffer;
	private String timestamp;
	private int moveId;

	private static String outputdir = "reports"; // TODO should be configured

	@Override
	public void startGame(Player whitePlayer, Player blackPlayer) {

		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;
		this.moveId = 1;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		this.timestamp = dateFormat.format(date);

		this.logStringBuffer = new StringBuffer(
				"<html>\n<body>\n<h1 style=\"color:#0000FF\">"
						+ whitePlayer.getName()
						+ "(white) vs. "
						+ blackPlayer.getName()
						+ "(black)</h1>\n<h3>"
						+ this.timestamp
						+ "</h3>\n<table border=\"1\"><tr><td rowspan=2><b>#</b></td><td rowspan=2><b>Player</b></td><td rowspan=2><b>Die 1</b></td><td rowspan=2><b>Die 2</b></td><td colspan=2><b>Checker Move 1</b></td><td colspan=2><b>Checker Move 2</b></td><td rowspan=2><b>Hit checkers</b></td><td rowspan=2><b>Born off checkers</b></td></tr><tr><td><b>Start point</b></td><td><b>Move length</b></td><td><b>Start point</b></td><td><b>Move length</b></td></tr>\n");
	}

	@Override
	public void endGame(GameOverStatus status, PlayerColor winner) {

		String statusString;
		String textColor;

		switch (status) {
		case OK:
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

		this.logStringBuffer.append("<tr style=\"color:" + textColor
				+ "\"><td colspan=10>" + winner.toString()
				+ " player wins the game - " + statusString
				+ "</td></tr></table></body></html>\n");

		try {
			File outputDir = new File(outputdir);
			if (!outputDir.exists()) {
				outputDir.mkdir();
			}
			File file = new File(outputDir, this.timestamp + "_"
					+ this.whitePlayer.getName() + "_"
					+ this.blackPlayer.getName() + ".html");
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(this.logStringBuffer.toString());
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
		this.logStringBuffer.append("<tr style=\"color:" + textColor
				+ "\"><td>" + this.moveId + "</td><td>" + color + "</td><td>"
				+ dice.getDie1() + "</td><td>" + dice.getDie2() + "</td><td>"
				+ move.getCheckerMove(0).getStartPoint() + "</td><td>"
				+ move.getCheckerMove(0).getMoveLength() + "</td><td>"
				+ move.getCheckerMove(1).getStartPoint() + "</td><td>"
				+ move.getCheckerMove(1).getMoveLength() + "</td><td rowspan="
				+ rowspan + ">" + hit + "</td><td rowspan=" + rowspan + ">"
				+ bornOff + "</td></tr>\n");
		if (move.isDouble()) {
			this.logStringBuffer.append("<tr><td>" + this.moveId + "</td><td>"
					+ color + "</td><td>" + dice.getDie1() + "</td><td>"
					+ dice.getDie2() + "</td><td>"
					+ move.getCheckerMove(2).getStartPoint() + "</td><td>"
					+ move.getCheckerMove(2).getMoveLength() + "</td><td>"
					+ move.getCheckerMove(3).getStartPoint() + "</td><td>"
					+ move.getCheckerMove(3).getMoveLength() + "</td></tr>\n");
		}
		this.moveId++;
	}

}
