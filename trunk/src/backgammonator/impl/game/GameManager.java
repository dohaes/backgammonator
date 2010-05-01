package backgammonator.impl.game;

import backgammonator.core.Game;
import backgammonator.core.Player;

/**
 * Represents ...
 */
public class GameManager {
	
	public static Game newGame(Player white, Player black, boolean logMoves) {
		return new GameImpl(white, black, logMoves);
	}

}
