package backgammonator.test.game;

import junit.framework.TestCase;
import backgammonator.core.PlayerMove;
import backgammonator.impl.game.BackgammonBoardImpl;
import backgammonator.impl.game.DiceImpl;
import backgammonator.impl.game.MoveValidator;
import backgammonator.test.players.SamplePlayer;
import backgammonator.test.util.TestUtil;

/**
 * Tests class {@link MoveValidator}
 */
public class MoveValidatorTestCase extends TestCase {
	
	public void testValidMoves() throws Exception {
		BackgammonBoardImpl board = new BackgammonBoardImpl();
		SamplePlayer player = new SamplePlayer();
		DiceImpl dice = new DiceImpl();
		for (int i = 0; i < 10; i++) {
			TestUtil.generateNext(dice);
			PlayerMove move = player.getMove(board, dice);
			assertTrue("Invalid move " + i, TestUtil.makeMove(board, move, dice));
		}
	}

	

	public void testInvalidMovesDices() throws Exception {
		BackgammonBoardImpl board = new BackgammonBoardImpl();
		SamplePlayer player = new SamplePlayer();
		DiceImpl dice = new DiceImpl();
		for (int i = 0; i < 1; i++) {
			TestUtil.generateNext(dice);
			PlayerMove move = player.getMove(board, dice);
			TestUtil.generateNext(dice);
			assertFalse("Invalid move accepted " + i, TestUtil.makeMove(board, move,
					dice));
		}
	}

	public void testInvalidMovesPoints() throws Exception {
	}

	public void testInvalidMovesHits() throws Exception {
	}

	public void testInvalidMovesBearOffs() throws Exception {
	}

	public void testInvalidMovesNoValidMoves() throws Exception {
	}

	
}