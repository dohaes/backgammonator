package backgammonator.test.util;

import java.lang.reflect.Method;

import backgammonator.impl.game.BackgammonBoardImpl;
import backgammonator.impl.game.DiceImpl;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.PlayerMove;

/**
 * Holds utility methods for test purposes.
 */
public final class TestUtil {

	private TestUtil() {
	}

	private static Method generateNext = null;
	private static Method makeMove = null;

	static {
		try {
			generateNext = DiceImpl.class.getDeclaredMethod("generateNext",
					(Class[]) null);
			generateNext.setAccessible(true);
		} catch (Exception e) {
		}

		try {
			makeMove = BackgammonBoardImpl.class.getDeclaredMethod("makeMove",
					new Class[] { PlayerMove.class, Dice.class });
			makeMove.setAccessible(true);
		} catch (Exception e) {
		}
	}

	/**
	 * Invokes method {@link DiceImpl#generateNext()} via reflection on
	 * <code>dice</code>
	 * 
	 * @see {@link DiceImpl#generateNext()}
	 */
	public static void generateNext(DiceImpl dice) {
		if (generateNext == null) throw new RuntimeException(
				"Method DiceImpl.generateNext not found!");
		try {
			generateNext.invoke(dice, (Object[]) null);
		} catch (Exception e) {
			throw new RuntimeException("Unexpected exception", e);
		}
	}

	/**
	 * Invokes method
	 * {@link BackgammonBoardImpl#makeMove(PlayerMove move, Dice dice)} via
	 * reflection. The method is invoked on <code>board</code> with arguments
	 * <code>move</code> and <code>dice</code>.
	 * 
	 * @see {@link BackgammonBoardImpl#makeMove(PlayerMove move, Dice dice)}
	 */
	public static boolean makeMove(BackgammonBoardImpl board, PlayerMove move,
			DiceImpl dice) throws Exception {
		if (makeMove == null) throw new RuntimeException(
				"Method BackgammonBoardImpl.makeMove not found!");
		try {
			return ((Boolean) makeMove.invoke(board, move, dice))
					.booleanValue();
		} catch (Exception e) {
			throw new RuntimeException("Unexpected exception", e);
		}
	}

}
