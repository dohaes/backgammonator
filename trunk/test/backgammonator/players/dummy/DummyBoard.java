package backgammonator.players.dummy;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Point;

public class DummyBoard {
	private int[] pos;

	public DummyBoard(BackgammonBoard board) {
		pos = new int[26];
		pos[0] = board.getBornOff(board.getCurrentPlayerColor());
		pos[25] = board.getHits(board.getCurrentPlayerColor());
		for (int i = 1; i < 25; i++) {
			Point p = board.getPoint(i);
			pos[i] = p.getCount()
					* (p.getColor().equals(board.getCurrentPlayerColor()) ? 1
							: -1);
		}
	}

	public int get(int i) {
		if (i < 1) {
			i = 0;
		}
		return pos[i];
	}
}