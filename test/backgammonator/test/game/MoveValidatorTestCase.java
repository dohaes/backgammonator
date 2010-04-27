package backgammonator.test.game;

import junit.framework.TestCase;
import backgammonator.core.Dice;
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
		for (int i = 0; i < 1000; i++) {
			Dice dice = new DiceImpl();
			PlayerMove move = player.getMove(board, dice);
			assert (MoveValidator.validateMove(board, move, dice));
			board.makeMove(move, dice);
		}
	}

	public void testInvalidMovesDices() throws Exception {
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