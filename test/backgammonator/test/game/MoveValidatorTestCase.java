package backgammonator.test.game;

import junit.framework.TestCase;
import backgammonator.core.PlayerMove;
import backgammonator.impl.game.BackgammonBoardImpl;
import backgammonator.impl.game.DiceImpl;
import backgammonator.impl.game.MoveValidator;
import backgammonator.test.players.DummyPlayer;

/**
 * Tests class {@link MoveValidator}
 */
public class MoveValidatorTestCase extends TestCase {
	public void testValidMoves() throws Exception {
		BackgammonBoardImpl board = new BackgammonBoardImpl();
		DummyPlayer player = new DummyPlayer();
		DiceImpl dice = new DiceImpl();
		for (int i = 0; i < 10; i++) {
			dice.generateNext();
			PlayerMove move = player.getMove(board, dice);
			assertTrue("Invalid move " + i, board.makeMove(move, dice));
		}
	}

	public void testInvalidMovesDices() throws Exception {
		BackgammonBoardImpl board = new BackgammonBoardImpl();
		DummyPlayer player = new DummyPlayer();
		DiceImpl dice = new DiceImpl();
		for (int i = 0; i < 10; i++) {
			dice.generateNext();
			PlayerMove move = player.getMove(board, dice);
			dice.generateNext();
			assertFalse("Invalid move accepted " + i, board.makeMove(move, dice));
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