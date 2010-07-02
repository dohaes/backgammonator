package backgammonator.impl.protocol;

import backgammonator.lib.protocol.ProtocolParser;

/**
 * Represents a manager for getting the configured type of
 * {@link ProtocolParser} implementation.
 */
public final class ProtocolManager {

	private static ProtocolParser instance = null;

	/**
	 * Gets new instance of the configured ProtocolParser implementation.
	 * 
	 * @return new instance of the configured ProtocolParser implementation
	 */
	public static ProtocolParser getParser() {
		if (instance == null) instance = new ProtocolParserImpl();
		return instance;
	}
}
