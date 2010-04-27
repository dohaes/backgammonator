package backgammonator.impl.game;

import java.util.Random;

import backgammonator.core.Dice;

/**
 * Represents implementation of the {@link Dice} interface.
 */
public final class DiceImpl implements Dice {

	private static final Random generator = new Random();
	private int die1;
	private int die2;

	/**
	 * Creates new pair of dice.
	 * Needed for testing.
	 */
	public DiceImpl(int die1, int die2) {
		setDie1(die1);
		setDie2(die2);
	}
	
	/**
	 * Creates randomly generated new pair of dice.
	 */
	public DiceImpl() {
		this(nextDie(), nextDie());
	}

	@Override
	public int getDie1() {
		return die1;
	}

	@Override
	public int getDie2() {
		return die2;
	}

	@Override
	public boolean isDouble() {
		return die1 == die2;
	}

	/**
	 * Generates the next pair of dice.
	 */
	void generateNext() {
		die1 = generator.nextInt(6) + 1;
		die2 = generator.nextInt(6) + 1;
	}
	
	private void setDie1(int die1) {
		if (die1 < 1 || die1 > 6) {
			throw new IllegalArgumentException("Invalid die1 : " + die1);
		}
		this.die1 = die1;
	}
	
	private void setDie2(int die2) {
		if (die2 < 1 || die2 > 6) {
			throw new IllegalArgumentException("Invalid die2 : " + die2);
		}
		this.die2 = die2;
	}
	
	private static int nextDie() {
		return generator.nextInt(6) + 1;
	}
}