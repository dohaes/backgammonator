package backgammonator.test.core;

import backgammonator.impl.game.DiceImpl;
import backgammonator.lib.game.Dice;
import backgammonator.test.util.TestUtil;
import junit.framework.TestCase;

/**
 * Tests class {@link DiceImpl}
 */
public class DiceTestCase extends TestCase {

	private DiceImpl dice = new DiceImpl();
	private int die;

	/**
	 * Tests if the generated values of the dice are in the interval for 1 to 6.
	 */
	public void testNumberInterval() {
		for (int i = 0; i < 10; i++) {
			TestUtil.generateNext(dice);
			assertTrue((die = dice.getDie1()) > 0 && die < 7);
			assertTrue((die = dice.getDie2()) > 0 && die < 7);
		}
	}

	/**
	 * Tests method {@link Dice#isDouble()}
	 */
	public void testDouble() {
		for (int i = 0; i < 10; i++) {
			TestUtil.generateNext(dice);
			boolean equal = dice.getDie1() == dice.getDie2();
			if (dice.isDouble()) assertTrue(equal);
			else assertFalse(equal);
		}
	}
}
