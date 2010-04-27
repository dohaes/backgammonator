package backgammonator.impl.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import backgammonator.core.Dice;
import backgammonator.core.GameOverStatus;
import backgammonator.core.GameLogger;
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
	private Player lastPlayer;
	private StringBuffer logStringBuffer;
	private String timestamp;
	private int moveId;
	
	private static String outputdir = "D:\\output"; //TODO should be configured

	@Override
	public void startGame(Player whitePlayer, Player blackPlayer) {

		this.whitePlayer = whitePlayer;
		this.blackPlayer = this.lastPlayer = blackPlayer;
		this.moveId = 1;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		this.timestamp = dateFormat.format(date);

		this.logStringBuffer = new StringBuffer(
				"<html>\n<body>\n<h1>"
						+ whitePlayer.getName()
						+ "(white) vs. "
						+ blackPlayer.getName()
						+ "(black)</h1>\n<h3>"
						+ this.timestamp
						+ "</h3>\n<table border=\"1\"><tr><td rowspan=2><b>#</b></td><td rowspan=2><b>Player</b></td><td rowspan=2><b>Die 1</b></td><td rowspan=2><b>Die 2</b></td><td colspan=2><b>Checker Move 1</b></td><td colspan=2><b>Checker Move 2</b></td><td rowspan=2><b>Hit checkers</b></td><td rowspan=2><b>Born off checkers</b></td></tr><tr><td><b>Start point</b></td><td><b>Move length</b></td><td><b>Start point</b></td><td><b>Move length</b></td></tr>\n");
	}

	@Override
	public void endGame(GameOverStatus status) {

		String statusString;
		switch (status) {
		case OK:
			statusString = "he born off all checkers.";
			break;
		case EXCEPTION:
			statusString = "exeption on other player's move.";
			break;
		case INVALID_MOVE:
			statusString = "invalid move on other player.";
			break;
		default:
			throw new IllegalArgumentException();
		}
		this.logStringBuffer.append("<tr><td colspan=10>"
				+ this.lastPlayer.getName() + " wins the game - "
				+ statusString + "</td></tr></table></body></html>\n");

		try {
			FileWriter fstream = new FileWriter(outputdir + File.separatorChar + this.timestamp + "_"
					+ this.whitePlayer.getName() + "_"
					+ this.blackPlayer.getName() + ".html");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(this.logStringBuffer.toString());
			out.close();
		} catch (Exception e) {
			Debug.getInstance().error("Error writing to file",
					Debug.LOGGER_MODULE, e);
		}

	}

	@Override
	public void logMove(PlayerMove move, PlayerColor color, Dice dice, int hit, int bornOff) {

		String rowspan;
		if (move.isDouble()) {
			rowspan = "2";
		} else {
			rowspan = "1";
		}

		this.logStringBuffer.append("<tr><td>"
				+ this.moveId
				+ "</td><td>"
				+ color + "</td><td>" + dice.getDie1() + "</td><td>"
				+ dice.getDie2() + "</td><td>"
				+ move.getCheckerMove(0).getStartPoint() + "</td><td>"
				+ move.getCheckerMove(0).getMoveLength() + "</td><td>"
				+ move.getCheckerMove(1).getStartPoint() + "</td><td>"
				+ move.getCheckerMove(1).getMoveLength() + "</td><td rowspan="
				+ rowspan + ">" + hit + "</td><td rowspan=" + rowspan + ">"
				+ bornOff + "</td></tr>\n");
		if (move.isDouble()) {
			this.logStringBuffer.append("<tr><td>"
					+ this.moveId
					+ "</td><td>"
					+ color + "</td><td>" + dice.getDie1()
					+ "</td><td>" + dice.getDie2() + "</td><td>"
					+ move.getCheckerMove(2).getStartPoint() + "</td><td>"
					+ move.getCheckerMove(2).getMoveLength() + "</td><td>"
					+ move.getCheckerMove(3).getStartPoint() + "</td><td>"
					+ move.getCheckerMove(3).getMoveLength() + "</td></tr>\n");
		}
		this.moveId++;
		this.lastPlayer = (color == PlayerColor.WHITE) ? this.whitePlayer
				: this.blackPlayer;
	}

}
