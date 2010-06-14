package backgammonator.sample.player.protocol.java;

import backgammonator.util.BackgammonatorConfig;

/**
 * Sample implementation of a player using the protocol. This player is timed
 * out.
 */
public class TimedoutMovePlayer {

	/**
	 * The entry point.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(BackgammonatorConfig.getProperty("backgammonator.game.moveTimeout", 2000) + 200);
	}
}
