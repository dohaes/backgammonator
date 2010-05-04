package backgammonator.impl.game;

import java.util.Random;

import backgammonator.core.Dice;

/**
 * Represents implementation of the {@link Dice} interface.
 */
public final class DiceImpl implements Dice {

	private static final Random generator = new Random();
	private int[] die = new int[2];

	/**
	 * Creates randomly generated new pair of dice.
	 */
	public DiceImpl() {
		generateNext();
	}

	/**
	 * Creates dice with predefined values.
	 * 
	 * @param die1
	 *            the first die.
	 * @param die2
	 *            the second die.
	 */
	public DiceImpl(int die1, int die2) {
		setDie(0, die1);
		setDie(1, die2);
	}

	public int getDie1() {
		return die[0];
	}

	public int getDie2() {
		return die[1];
	}

	public boolean isDouble() {
		return die[0] == die[1];
	}

	/**
	 * Generates the next pair of dice.
	 */
	void generateNext() {
		setDie(0, generator.nextInt(6) + 1);
		setDie(1, generator.nextInt(6) + 1);
	}

	private void setDie(int id, int die) {
		if (die < 1 || die > 6) {
			throw new IllegalArgumentException("Invalid die : " + die);
		}
		this.die[id] = die;
	}
	
	public String toString() {
		return "(" + die[0] + ", " + die[1] + ")";
	}
}