package backgammonator.test.protocol;

import backgammonator.core.PlayerMove;
import backgammonator.impl.protocol.Parser;
import junit.framework.TestCase;

public class ParserTestCase extends TestCase {

	public void testGetMoveStandardMove() {
		PlayerMove resultPlayerMove = Parser.getMove("1 3 7 6");
		
		assertFalse(resultPlayerMove.isDouble());
		
		assertFalse(resultPlayerMove.getCheckerMove(0).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(0).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(0).getStartPoint(), 1);
		assertEquals(resultPlayerMove.getCheckerMove(0).getDie(), 3);
		
		assertFalse(resultPlayerMove.getCheckerMove(1).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(1).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(1).getStartPoint(), 7);
		assertEquals(resultPlayerMove.getCheckerMove(1).getDie(), 6);
	}
	
	public void testGetMoveBearOffAndStandardMove() {
		PlayerMove resultPlayerMove = Parser.getMove("0 5 5 6");
		
		assertFalse(resultPlayerMove.isDouble());
		
		assertTrue(resultPlayerMove.getCheckerMove(0).isBearingOff());
		assertFalse(resultPlayerMove.getCheckerMove(0).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(0).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(0).getStartPoint(), 1);
		assertEquals(resultPlayerMove.getCheckerMove(0).getDie(), 3);
		
		assertFalse(resultPlayerMove.getCheckerMove(1).isBearingOff());
		assertFalse(resultPlayerMove.getCheckerMove(1).isReenterHitChecker());
		assertFalse(resultPlayerMove.getCheckerMove(1).isUnavailableMove());
		assertEquals(resultPlayerMove.getCheckerMove(1).getStartPoint(), 7);
		assertEquals(resultPlayerMove.getCheckerMove(1).getDie(), 6);
	}

}
