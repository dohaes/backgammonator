package backgammonator.test.protocol;

import backgammonator.impl.game.BackgammonBoardImpl;
import backgammonator.impl.game.DiceImpl;
import backgammonator.impl.protocol.ProtocolParser;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.PlayerMove;
import junit.framework.TestCase;

/**
 * Tests class {@link ProtocolParser}.
 */

public class ProtocolParserTestCase extends TestCase {

	public void testGetMoveStandardMoves() {
		PlayerMove resultPlayerMove = ProtocolParser.getMove("11 3 7 6");

		assertFalse(resultPlayerMove.isDouble());

		assertFalse(resultPlayerMove.getCheckerMove(0).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(0).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(0).getStartPoint(), 11);
		assertEquals(resultPlayerMove.getCheckerMove(0).getDie(), 3);

		assertFalse(resultPlayerMove.getCheckerMove(1).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(1).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(1).getStartPoint(), 7);
		assertEquals(resultPlayerMove.getCheckerMove(1).getDie(), 6);
	}

	public void testGetMoveReenterCheckerAndStandardMove() {
		PlayerMove resultPlayerMove = ProtocolParser.getMove("25 5 20 6");

		assertFalse(resultPlayerMove.isDouble());

		assertTrue(resultPlayerMove.getCheckerMove(0).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(0).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(0).getDie(), 5);

		assertFalse(resultPlayerMove.getCheckerMove(1).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(1).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(1).getStartPoint(), 20);
		assertEquals(resultPlayerMove.getCheckerMove(1).getDie(), 6);
	}

	public void testGetMoveReenterCheckers() {
		PlayerMove resultPlayerMove = ProtocolParser.getMove("25 5 25 2");

		assertFalse(resultPlayerMove.isDouble());

		assertTrue(resultPlayerMove.getCheckerMove(0).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(0).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(0).getDie(), 5);

		assertTrue(resultPlayerMove.getCheckerMove(1).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(1).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(1).getDie(), 2);
	}

	public void testGetMoveUnavailableMoves() {
		PlayerMove resultPlayerMove = ProtocolParser.getMove("0 5 0 6");

		assertFalse(resultPlayerMove.isDouble());

		assertFalse(resultPlayerMove.getCheckerMove(0).isReenterHitChecker());
		assertTrue(resultPlayerMove.getCheckerMove(0).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(0).getDie(), 5);

		assertFalse(resultPlayerMove.getCheckerMove(1).isReenterHitChecker());
		assertTrue(resultPlayerMove.getCheckerMove(1).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(1).getDie(), 6);
	}

	public void testGetMoveStandartMoveAndUnavailableMove() {
		PlayerMove resultPlayerMove = ProtocolParser.getMove("20 5 0 6");

		assertFalse(resultPlayerMove.isDouble());

		assertFalse(resultPlayerMove.getCheckerMove(0).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(0).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(0).getStartPoint(), 20);
		assertEquals(resultPlayerMove.getCheckerMove(0).getDie(), 5);

		assertFalse(resultPlayerMove.getCheckerMove(1).isReenterHitChecker());
		assertTrue(resultPlayerMove.getCheckerMove(1).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(1).getDie(), 6);
	}

	public void testGetMoveReenterCheckerAndUnavailableMove() {
		PlayerMove resultPlayerMove = ProtocolParser.getMove("25 5 0 1");

		assertFalse(resultPlayerMove.isDouble());

		assertTrue(resultPlayerMove.getCheckerMove(0).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(0).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(0).getDie(), 5);

		assertFalse(resultPlayerMove.getCheckerMove(1).isReenterHitChecker());
		assertTrue(resultPlayerMove.getCheckerMove(1).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(1).getDie(), 1);
	}

	public void testGetMoveDoubleAssortie() {
		PlayerMove resultPlayerMove = ProtocolParser.getMove("25 6 25 6 7 6 0 6");

		assertTrue(resultPlayerMove.isDouble());

		assertTrue(resultPlayerMove.getCheckerMove(0).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(0).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(0).getDie(), 6);

		assertTrue(resultPlayerMove.getCheckerMove(1).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(1).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(1).getDie(), 6);

		assertFalse(resultPlayerMove.getCheckerMove(2).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(2).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(2).getStartPoint(), 7);
		assertEquals(resultPlayerMove.getCheckerMove(2).getDie(), 6);

		assertFalse(resultPlayerMove.getCheckerMove(3).isReenterHitChecker());
		assertTrue(resultPlayerMove.getCheckerMove(3).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(3).getDie(), 6);
	}

	public void testGetBoardConfigurationInGame() {
		String resultString = ProtocolParser.getBoardConfiguration(
				new BackgammonBoardImpl(), new DiceImpl(4, 3), false, null);
		assertEquals(
				resultString,
				"2 1 0 1 0 1 0 1 0 1 5 0 0 1 3 0 0 1 0 1 0 1 5 1 5 0 0 1 0 1 0 1 3 1 0 1 5 1 0 1 0 1 0 1 0 1 2 0 0 0 0 0 4 3 0");
	}

	public void testGetBoardConfigurationInvalidMove() {
		String resultString = ProtocolParser.getBoardConfiguration(
				new BackgammonBoardImpl(), null, false,
				GameOverStatus.INVALID_MOVE);
		assertEquals(
				resultString,
				"2 1 0 1 0 1 0 1 0 1 5 0 0 1 3 0 0 1 0 1 0 1 5 1 5 0 0 1 0 1 0 1 3 1 0 1 5 1 0 1 0 1 0 1 0 1 2 0 0 0 0 0 1 1 11");
	}

	public void testGetBoardConfigurationException() {
		String resultString = ProtocolParser
				.getBoardConfiguration(new BackgammonBoardImpl(), null, true,
						GameOverStatus.EXCEPTION);
		assertEquals(
				resultString,
				"2 1 0 1 0 1 0 1 0 1 5 0 0 1 3 0 0 1 0 1 0 1 5 1 5 0 0 1 0 1 0 1 3 1 0 1 5 1 0 1 0 1 0 1 0 1 2 0 0 0 0 0 1 1 4");
	}

	public void testGetBoardConfigurationTimedOut() {
		String resultString = ProtocolParser.getBoardConfiguration(
				new BackgammonBoardImpl(), null, true, GameOverStatus.TIMEDOUT);
		assertEquals(
				resultString,
				"2 1 0 1 0 1 0 1 0 1 5 0 0 1 3 0 0 1 0 1 0 1 5 1 5 0 0 1 0 1 0 1 3 1 0 1 5 1 0 1 0 1 0 1 0 1 2 0 0 0 0 0 1 1 6");
	}

	public void testGetBoardConfigurationDoubleWin() {
		String resultString = ProtocolParser.getBoardConfiguration(
				new BackgammonBoardImpl(), null, true, GameOverStatus.DOUBLE);
		assertEquals(
				resultString,
				"2 1 0 1 0 1 0 1 0 1 5 0 0 1 3 0 0 1 0 1 0 1 5 1 5 0 0 1 0 1 0 1 3 1 0 1 5 1 0 1 0 1 0 1 0 1 2 0 0 0 0 0 1 1 2");
	}

}
