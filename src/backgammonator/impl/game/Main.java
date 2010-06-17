package backgammonator.impl.game;

import java.io.File;

import backgammonator.impl.protocol.SourceProcessor;
import backgammonator.lib.game.Game;
import backgammonator.util.Debug;

/**
 * Runs a single game between two players. The players are to be source files
 * implementing the defined protocol. The absolute paths to that source files
 * should be given as arguments in an appropriate way.
 */
public class Main {

	/**
	 * Main method for testing two backgammon players
	 * 
	 * @param args the two absolute paths to that source files
	 */
	public static void main(String[] args) {
		if (args.length < 2 || args.length > 4) {
			System.out
					.println("Usage : java -jar backgammonatorLibrary.jar "
							+ "<full_path_to_player1> <full_path_to_player2> [<number_of_games>] [clean]");
			return;
		}

		boolean clean = false;
		int runs = 1;

		if (args.length == 3) {
			if (args[2].equals("clean")) clean = true;
			else try {
				runs = Integer.parseInt(args[2]);
			} catch (NumberFormatException nfe) {
				Debug.getInstance().error(
						"Error parsing number of runs : " + args[2],
						Debug.DEMO, nfe);
			}
		} else if (args.length == 4) {
			if (args[3].equals("clean")) clean = true;
			try {
				runs = Integer.parseInt(args[2]);
			} catch (NumberFormatException nfe) {
				Debug.getInstance().error(
						"Error parsing number of runs : " + args[2],
						Debug.DEMO, nfe);
			}
		}

		Game game = GameManager.newGame(args[0], args[1], true);
		
		for (int i = 0; i < runs; i++) {
			game.start();
		}

		if (clean) try {
			SourceProcessor.cleanUp(new File(args[0]).getParent());
			SourceProcessor.cleanUp(new File(args[2]).getParent());
		} catch (Throwable t) {
			Debug.getInstance().error(
					"Error cleaning compiled files : " + args[0] + " and "
							+ args[1], Debug.DEMO, t);
		}
	}
}
