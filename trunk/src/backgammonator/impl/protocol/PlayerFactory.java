package backgammonator.impl.protocol;

import backgammonator.game.Player;

/**
 * Represents factory for {@link Player} objects.
 */
public final class PlayerFactory {

	/**
	 * Returns new Player object created with the given arguments.
	 * 
	 * @param command the command to be executed to init the process with.
	 * @param name the name of the player - same as the username of the user who uploaded the source.
	 * @return new Player object
	 */
	public static Player newPlayer(String command, String name) {
		return new PlayerImpl(command, name);
	}
}
