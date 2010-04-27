package backgammonator.test.players;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.PlayerColor;
import backgammonator.core.Point;

public class DummyBoard {
	private int[] pos;
	private String color;

	public DummyBoard(BackgammonBoard board) {
		color = PlayerColor.WHITE.equals(board.getCurrentPlayerColor()) ? "White" : "Black";
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
		System.out.println(color + " (" + point + ", " + die + ")");
		System.out.println("Before: " + this);
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
		System.out.println("After:  " + this + "\n");
	}

	public String toString() {
		StringBuilder s = new StringBuilder(100);
		s.append("Hit[").append(pos[0]).append("] ");
		s.append("BornOff[").append(pos[25]).append("]\n");
		for (int i = 12; i >= 7; i--) {
			s.append((pos[i] >= 0 ? " " : "") + pos[i]).append(" ");
		}
		s.append(" || ");
		for (int i = 6; i >= 1; i--) {
			s.append((pos[i] >= 0 ? " " : "") + pos[i]).append(" ");
		}
		s.append("\n");
		for (int i = 13; i <= 18; i++) {
			s.append((pos[i] >= 0 ? " " : "") + pos[i]).append(" ");
		}
		s.append(" || ");
		for (int i = 19; i <= 24; i++) {
			s.append((pos[i] >= 0 ? " " : "") + pos[i]).append(" ");
		}
		return s.toString();
	}
}