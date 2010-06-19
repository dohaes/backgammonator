package backgammonator.test.game;

import junit.framework.TestCase;
import backgammonator.impl.game.BackgammonBoardImpl;
import backgammonator.impl.game.DiceImpl;
import backgammonator.impl.game.MoveValidator;
import backgammonator.lib.game.PlayerMove;
import backgammonator.sample.player.interfacce.SamplePlayer;
import backgammonator.test.util.TestUtil;

/**
 * Tests class {@link MoveValidator}
 */
public class MoveValidatorTestCase extends TestCase {

	/**
	 * Tests validation of valid moves.
	 */
	public void testValidMoves() throws Exception {
		BackgammonBoardImpl board = new BackgammonBoardImpl();
		SamplePlayer player = new SamplePlayer();
		DiceImpl dice = new DiceImpl();
		for (int i = 0; i < 10; i++) {
			TestUtil.generateNext(dice);
			PlayerMove move = player.getMove(board, dice);
			assertTrue("Invalid move " + i, TestUtil
					.makeMove(board, move, dice));
		}
	}

	/**
	 * Tests validation of invalid moves for the given dice.
	 */
	public void testInvalidMovesDice() throws Exception {
		BackgammonBoardImpl board = new BackgammonBoardImpl();
		SamplePlayer player = new SamplePlayer();
		DiceImpl dice = new DiceImpl();
		for (int i = 0; i < 1; i++) {
			TestUtil.generateNext(dice);
			PlayerMove move = player.getMove(board, dice);
			TestUtil.generateNext(dice);
			assertFalse("Invalid move accepted " + i, TestUtil.makeMove(board,
					move, dice));
		}
	}

	/**
	 * Tests validation of invalid points in the given moves.
	 */
	public void testInvalidMovesPoints() {
		// TODO
	}

	/**
	 * Tests validation of invalid hits in the given moves.
	 */
	public void testInvalidMovesHits() {
		// TODO
	}

	/**
	 * Tests validation of invalid bearoffs in the given moves.
	 */
	public void testInvalidMovesBearOffs() {
		// TODO
	}

	/**
	 * Tests validation of invalid moves.
	 */
	public void testInvalidMovesNoValidMoves() {
		// TODO
	}

}