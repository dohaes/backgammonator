package backgammonator.test.players;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.CheckerMove;
import backgammonator.core.Dice;
import backgammonator.core.PlayerMove;

public class DummyPlayer extends AbstractTestPlayer {

	private static int ID = 0;
	private int id = ID++;

	public PlayerMove getMove(BackgammonBoard board, Dice dice)
			throws Exception {
		CheckerMove m1, m2, m3, m4;
		DummyBoard b = new DummyBoard(board);
		m1 = findMove(b, dice.getDie1());
		m2 = findMove(b, dice.getDie2());
		if (m1 == null) {
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
		int point = -1;
		if (board.get(0) > 0) {
			if (board.get(die) >= -1) {
				point = 0;
			}
		} else {
			for (int i = 1; i < 25; i++) {
				if (board.get(i) > 0 && board.get(i + die) >= -1) {
					point = i;
					break;
				}
			}
		}
		if (point != -1) {
			board.move(point, die);
		}
		return point == -1 ? null : new CheckerMove(point, die);
	}

	public String getName() {
		return "Dummy G " + id;
	}
}