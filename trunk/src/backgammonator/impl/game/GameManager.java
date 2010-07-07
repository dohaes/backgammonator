package backgammonator.impl.game;

import backgammonator.impl.processor.SourceProcessor;
import backgammonator.lib.game.Game;
import backgammonator.lib.game.Player;
import backgammonator.lib.processor.PlayerBuilderException;
import backgammonator.util.Debug;

/**
 * This class is used to create a new game between two AI players.
 */
public class GameManager {

	/**
	 * Creates a game between the two AI players given as arguments. The players
	 * are given as Player objects.
	 * 
	 * @param white Player the first player
	 * @param black Player the second player
	 * @param logMoves true if the game logger should be enabled and false
	 *            otherwise
	 * @return new instance of the {@link Game} interface implementation
	 */
	public static Game newGame(Player white, Player black, boolean logMoves) {
		return new GameImpl(white, black, logMoves);
	}

	/**
	 * Creates a game between the two AI players given as arguments. The players
	 * are given as absolute paths to corresponding the source files.
	 * 
	 * @param white String the first player
	 * @param black String the second player
	 * @param logMoves true if the game logger should be enabled and false
	 *            otherwise
	 * @return new instance of the {@link Game} interface implementation or
	 *         <code>null</code> if any errors occurs.
	 */
	public static Game newGame(String white, String black, boolean logMoves) {
		try {
			return new GameImpl(SourceProcessor.processSource(white), SourceProcessor
					.processSource(black), logMoves);
		} catch (PlayerBuilderException e) {
			Debug.getInstance().error("Error during source compilation", Debug.GAME_LOGIC, e);
			return null;
		}
	}

}
