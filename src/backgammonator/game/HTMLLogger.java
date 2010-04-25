package backgammonator.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import backgammonator.core.Logger;
import backgammonator.core.PlayerMove;
import backgammonator.core.PlayerMove;

/**
 * This class implements the Logger interface. Represents the log as an html
 * document.
 */

public class HTMLLogger implements Logger {

	@Override
<<<<<<< .mine
	public void endGame() {
=======
	public void log(PlayerMove move) {
>>>>>>> .r63
		// TODO Auto-generated method stub

	}

	@Override
	public void logMove(PlayerMove move) {
		// TODO Auto-generated method stub

	}

	@Override
	public void startGame(Player firstPlayer, Player secondPlayer) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
			Date date = new Date();
			FileWriter fstream = new FileWriter(dateFormat.format(date) + "_" + firstPlayer.getName() + "_" + secondPlayer.getName() + ".html");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("Hello Java");
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
