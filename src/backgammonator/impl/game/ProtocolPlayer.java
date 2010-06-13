package backgammonator.impl.game;

import java.util.Scanner;

import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;

/**
 * This class represents sample implementation of the protocol. Classes which
 * implement it can be run using the BackgammonLib.jar and BackgammonDemo.jar.
 * If you wish to use BackgammonLib.jar you have to implement methods
 * {@link Player#getMove(BackgammonBoard, Dice)}, {@link Player#getName()} and
 * {@link Player#gameOver(BackgammonBoard, boolean, GameOverStatus)}. If you
 * wish to use BackgammonDemo.jar you have to override method {@link #start()}
 * as well.
 */
public abstract class ProtocolPlayer implements Player {

	/**
	 * Represents the number of the checkers on each position.
	 */
	protected int[] count = new int[25]; // to index from 1 to 24

	/**
	 * Represents the color of the checkers on each position.
	 */
	protected int[] possesion = new int[25]; // to index from 1 to 24

	/**
	 * Represents your hit checkers.
	 */
	protected int hits_mine;

	/**
	 * Represents the hit checkers of your opponent.
	 */
	protected int hits_opponent;

	/**
	 * Represents your born off checkers.
	 */
	protected int bornoff_mine;

	/**
	 * Represents the born off checkers of your opponent.
	 */
	protected int bornoff_opponent;

	/**
	 * Represents the number on the first die.
	 */
	protected int die1;

	/**
	 * Represents the number on the second die.
	 */
	protected int die2;

	/**
	 * Represents the status of the game. It is 0 if the game is not over yet.
	 */
	protected int status = 0; // 0 - continue playing

	/**
	 * Scanner to read information form the standard input.
	 */
	protected Scanner scanner = new Scanner(System.in);

	/**
	 * The entry point. Classes which extend this class in order to use the
	 * BackagammonDemo.jar must override this method by creating new instance
	 * and invoking method {@link #start()} on it.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		// TODO override
	}

	/**
	 * Starts the game.
	 * 
	 * @throws Exception if {@link Player#getMove(BackgammonBoard, Dice)} throws
	 *             any Exception
	 */
	protected void start() throws Exception {
		readInput(); // read the input
		while (status == 0) {
			//TODO init with adequate board and status
			System.out.println(getMove(new BackgammonBoardImpl(), new DiceImpl(
					die1, die2))); // write the result
			readInput();
		}
		gameOver(new BackgammonBoardImpl(), false, GameOverStatus.NORMAL);

	}

	/**
	 * Reads the input.
	 */
	protected void readInput() {

		// read the board configuration
		for (int i = 1; i <= 24; i++) {
			count[i] = scanner.nextInt();
			possesion[i] = scanner.nextInt();
		}

		// read hits and bornoffs
		hits_mine = scanner.nextInt();
		hits_opponent = scanner.nextInt();
		bornoff_mine = scanner.nextInt();
		bornoff_opponent = scanner.nextInt();

		// read the dice
		die1 = scanner.nextInt();
		die2 = scanner.nextInt();

		// read the status
		status = scanner.nextInt();
	}
}
