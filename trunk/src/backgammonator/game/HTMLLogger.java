package backgammonator.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import backgammonator.core.Logger;
import backgammonator.core.Player;
import backgammonator.core.PlayerMove;

/**
 * This class implements the Logger interface. Represents the log as an html
 * document.
 */

public class HTMLLogger implements Logger {

	private Player whitePlayer;
	private Player blackPlayer;
	private StringBuffer logStringBuffer;
	private String timestamp;

	@Override
	public void endGame(String message) {
		try {
			FileWriter fstream = new FileWriter(this.timestamp + "_"
					+ this.whitePlayer.getName() + "_"
					+ this.blackPlayer.getName() + ".html");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("Hello Java");
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	@Override
	public void logMove(PlayerMove move) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startGame(Player whitePlayer, Player blackPlayer) {

		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
		Date date = new Date();
		this.timestamp = dateFormat.format(date);

		this.logStringBuffer = new StringBuffer(
				"<html>\n<body>\n<h1>"
						+ whitePlayer.getName()
						+ "(white) vs. "
						+ blackPlayer.getName()
						+ "(black)</h1>\n<h3>"
						+ this.timestamp
						+ "</h3>\n<table border=\"1\"><tr><td rowspan=2><b>#</b></td><td rowspan=2><b>Player</b></td><td rowspan=2><b>Die 1</b></td><td rowspan=2><b>Die 2</b></td><td colspan=2><b>Checker Move 1</b></td><td colspan=2><b>Checker Move 2</b></td><td rowspan=2><b>Hit checkers</b></td><td rowspan=2><b>Born off checkers</b></td></tr><tr><td><b>Start point</b></td><td><b>Move length</b></td><td><b>Start point</b></td><td><b>Move length</b></td></tr>");
	}
}
