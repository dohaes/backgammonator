package backgammonator.core;

import java.util.Random;

/**
 * Creates new randomly generated pair of dice.
 */
public class Dice {
	private static final Random generator = new Random();
	private int die1;
	private int die2;

	/**
	 * Creates new randomly generated pair of dice.
	 */
	public Dice() {
	}

	public int getDie1() {
		return die1;
	}

	public int getDie2() {
		return die2;
	}

	public boolean isDouble() {
		return getDie1() == getDie2();
	}

	public void generateDice() {
		die1 = generator.nextInt(6) + 1;
		die2 = generator.nextInt(6) + 1;
	}
}