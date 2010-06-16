package backgammonator.impl.protocol;

import java.util.Scanner;

import backgammonator.impl.game.BackgammonBoardImpl;
import backgammonator.impl.game.DiceImpl;
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
 * wish to use BackgammonDemo.jar you have to override method
 * {@link #main(String[])} as well. You can also override the start method for
 * advanced scenarios.
 */
public abstract class AbstractPlayer implements Player {

	/**
	 * The entry point. Classes which extend this class in order to use the
	 * BackagammonDemo.jar must override this method by creating new instance
	 * and invoking method {@link #start()} on it.
	 * 
	 * @param args ignored
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		throw new NoSuchMethodException("Must be overriden.");
	}

	/**
	 * Starts the game. Use this method in {@link #main(String[])}.
	 * 
	 * @throws Exception if {@link Player#getMove(BackgammonBoard, Dice)} throws
	 *             any Exception
	 */
	protected void start() throws Exception {
		Scanner scanner = new Scanner(System.in);
		int[] count = new int[24];
		int[] possesion = new int[24];
		int hits_mine, hits_opponent, bornoff_mine, bornoff_opponent, die1, die2;

		while (true) {
			// read the board configuration
			for (int i = 0; i < 24; i++) {
				count[i] = scanner.nextInt();
				possesion[i] = scanner.nextInt();
			}

			// read hits and born-offs
			hits_mine = scanner.nextInt();
			hits_opponent = scanner.nextInt();
			bornoff_mine = scanner.nextInt();
			bornoff_opponent = scanner.nextInt();

			// read the dice
			die1 = scanner.nextInt();
			die2 = scanner.nextInt();

			// read the status
			if (scanner.nextInt() != 0) {
				break;
			}

			BackgammonBoard board = new BackgammonBoardImpl(count, possesion,
					hits_mine, hits_opponent, bornoff_mine, bornoff_opponent);

			System.out.println(getMove(board, new DiceImpl(die1, die2)));
		}
		gameOver(new BackgammonBoardImpl(), false, GameOverStatus.NORMAL);

	}
}
