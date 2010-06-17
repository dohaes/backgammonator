package backgammonator.impl.game;

import backgammonator.lib.game.Game;

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
		if (args.length != 2) {
			System.out.println("Usage : java -jar backgammonatorLibrary.jar "
					+ "<full_path_to_player1> <full_path_to_player2>");
			return;
		}

		Game game = GameManager.newGame(args[0], args[1], true);
		game.start();
		// TODO clean up compilation files?
		// SourceProcessor.cleanUp(args[0]);
		// SourceProcessor.cleanUp(args[1]);
	}
}
