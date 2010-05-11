package backgammonator.impl.logger;

import backgammonator.lib.logger.GameLogger;

/**
 * Represents factory for selecting a desired type of {@link GameLogger}
 * implementation.
 */
public final class GameLoggerFactory {

	/**
	 * If given as argument to {@link #getLogger(int)} specifies that the
	 * results of the game should be saved in a plain text file.
	 */
	public static final int PLAIN = 0;

	/**
	 * If given as argument to {@link #getLogger(int)} specifies that the
	 * results of the game should be saved in an html file.
	 */
	public static final int HTML = 1;

	/**
	 * If given as argument to {@link #getLogger(int)} specifies that the
	 * results of the game should be printed on the standard output.
	 */
	public static final int CONSOLE = 2;

	/**
	 * Gets new instance of the selected Logger implementation.
	 * 
	 * @param type
	 *            the type of the logger
	 * @return new instance of the selected Logger implementation
	 */
	public static GameLogger getLogger(int type) {
		switch (type) {
		case PLAIN:
			throw new IllegalArgumentException("Not supported");
		case CONSOLE:
			throw new IllegalArgumentException("Not supported");
		case HTML:
			return new HTMLGameLogger();
		default:
			throw new IllegalArgumentException("Not supported");
		}
	}

}
