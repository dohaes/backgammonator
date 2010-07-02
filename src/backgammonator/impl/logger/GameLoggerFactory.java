package backgammonator.impl.logger;

import backgammonator.lib.logger.GameLogger;

/**
 * Represents factory for getting the configured type of {@link GameLogger}
 * implementation.
 */
public final class GameLoggerFactory {

	/**
	 * Gets new instance of the configured Logger implementation.
	 * 
	 * @return new instance of the configured Logger implementation
	 */
	public static GameLogger getLogger() {
		return new HTMLGameLogger();
	}

}
