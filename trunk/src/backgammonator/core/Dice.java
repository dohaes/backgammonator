package backgammonator.core;

/**
 * Creates new randomly generated pair of dice.
 */
public interface Dice {

	/**
	 * Returns the number on the first die.
	 */
	public int getDie1();

	/**
	 * Returns the number on the second die.
	 */
	public int getDie2();

	/**
	 * Checks if the dice form double.
	 */
	public boolean isDouble();
}