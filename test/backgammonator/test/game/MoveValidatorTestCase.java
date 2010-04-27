package backgammonator.test.game;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.PlayerMove;
import backgammonator.impl.game.BackgammonBoardImpl;
import backgammonator.impl.game.DiceImpl;
import backgammonator.impl.game.MoveValidator;
import backgammonator.test.players.DummyPlayer;
import junit.framework.TestCase;

public class MoveValidatorTestCase extends TestCase {
	public void testValidMoves() {
		BackgammonBoard board = new BackgammonBoardImpl();
		DummyPlayer player = new DummyPlayer();
		Dice dice = new DiceImpl();
		PlayerMove move = player.getMove(board, dice);
		MoveValidator.validateMove(board, move, dice);
	}

	public void testInvalidMovesDices() {
	}

	public void testInvalidMovesPoints() {
	}

	public void testInvalidMovesHits() {
	}

	public void testInvalidMovesBearOffs() {
	}

	public void testInvalidMovesNoValidMoves() {
	}
}