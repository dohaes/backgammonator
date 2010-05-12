package backgammonator.impl.logger;

import backgammonator.lib.logger.TournamentLogger;

/**
 * Represents factory for selecting a desired type of {@link TournamentLogger}
 * implementation.
 */
public final class TournamentLoggerFactory {

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
	 * @param type the type of the logger
	 * @return new instance of the selected Logger implementation
	 */
	public static TournamentLogger getLogger(int type) {
		switch (type) {
		case PLAIN:
			throw new IllegalArgumentException("Not supported");
		case CONSOLE:
			throw new IllegalArgumentException("Not supported");
		case HTML:
			return new HTMLTournamentLogger();
		default:
			throw new IllegalArgumentException("Not supported");
		}
	}

}
