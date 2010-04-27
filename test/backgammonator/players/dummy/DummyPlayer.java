package backgammonator.players.dummy;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.CheckerMove;
import backgammonator.core.Dice;
import backgammonator.core.Player;
import backgammonator.core.PlayerMove;

public class DummyPlayer implements Player {
	public void gameOver(boolean wins) {
		// ignore
	}

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
		if (board.get(25) > 0) {
			if (board.get(25 - die) < -1) {
				return null;
			}
			return new CheckerMove(25, die);
		}

		for (int i = 24; i > 0; i--) {
			if (board.get(i) > 0 && board.get(i - die) >= -1) {
				return new CheckerMove(i, die);
			}
		}
		return null;
	}

	public String getName() {
		return "Dummy G";
	}
}