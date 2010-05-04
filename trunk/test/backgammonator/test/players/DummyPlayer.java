package backgammonator.test.players;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.CheckerMove;
import backgammonator.core.CheckerMoveType;
import backgammonator.core.Dice;
import backgammonator.core.PlayerMove;

public class DummyPlayer extends AbstractTestPlayer {

	private static int ID = 0;
	private int id = ID++;

	public DummyPlayer() {
	}

	public DummyPlayer(int id) {
		this.id = id;
	}

	public PlayerMove getMove(BackgammonBoard board, Dice dice)
			throws Exception {
		System.out.println("Dice: (" + dice.getDie1() + ", " + dice.getDie2()
				+ ")");
		CheckerMove m1, m2, m3, m4;
		DummyBoard b = new DummyBoard(board);
		m1 = findMove(b, dice.getDie1());
		m2 = findMove(b, dice.getDie2());
		if (m1.isUnavailableMove()) {
			m1 = m2;
			m2 = findMove(b, dice.getDie1());
		}
		if (dice.isDouble()) {
			m3 = findMove(b, dice.getDie1());
			m4 = findMove(b, dice.getDie2());
			return new PlayerMove(new CheckerMove[] { m1, m2, m3, m4 });
		} else {
			return new PlayerMove(new CheckerMove[] { m1, m2 });
		}
	}

	private CheckerMove findMove(DummyBoard board, int die) {
		if (board.get(25) > 0) {
			if (board.get(25 - die) >= -1) {
				board.move(25, die);
				return new CheckerMove(CheckerMoveType.REENTER_HIT_CHECKER, die);
			}
		} else {
			for (int i = 24; i >= 1; i--) {
				if (board.get(i) > 0 && board.get(i - die) >= -1) {
					board.move(i, die);
					return new CheckerMove(i, die);
				}
			}
		}
		return new CheckerMove(CheckerMoveType.NO_AVAILABLE_MOVE, die);
	}

	public String getName() {
		return "Dummy G " + id;
	}
}