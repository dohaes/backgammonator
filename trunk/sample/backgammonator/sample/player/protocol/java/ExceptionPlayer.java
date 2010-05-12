package backgammonator.sample.player.protocol.java;

/**
 * Sample implementation of a player using the protocol. This player throws
 * exception in main.
 */
public class ExceptionPlayer {

	/**
	 * The entry point.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		throw new RuntimeException("Test exeption");
	}

}
