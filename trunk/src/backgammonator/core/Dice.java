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
		setDie1(generator.nextInt(6) + 1);
		setDie2(generator.nextInt(6) + 1);
	}

	/**
	 * @param die1
	 */
	public void setDie1(int die1) {
		this.die1 = die1;
	}

	/**
	 * @return
	 */
	public int getDie1() {
		return die1;
	}

	/**
	 * @param die2
	 */
	public void setDie2(int die2) {
		this.die2 = die2;
	}

	/**
	 * @return
	 */
	public int getDie2() {
		return die2;
	}

	public boolean isDouble() {
		return getDie1() == getDie2();
	}
}