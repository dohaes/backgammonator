package backgammonator.test.players;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Point;

public class DummyBoard {
	private int[] pos;

	public DummyBoard(BackgammonBoard board) {
		pos = new int[26];
		pos[0] = board.getHits(board.getCurrentPlayerColor());
		pos[25] = board.getBornOff(board.getCurrentPlayerColor());
		for (int i = 1; i < 25; i++) {
			Point p = board.getPoint(i);
			pos[i] = p.getCount()
					* (p.getColor().equals(board.getCurrentPlayerColor()) ? 1
							: -1);
		}
	}

	public int get(int i) {
		if (i > 25) {
			i = 25;
		}
		return pos[i];
	}

	public void move(int point, int die) {
		pos[point]--;
		int end = point + die;
		if (end > 25) {
			end = 25;
		}
		if (pos[end] == -1) {
			pos[end] = 1;
		} else {
			pos[end]++;
		}
	}
}