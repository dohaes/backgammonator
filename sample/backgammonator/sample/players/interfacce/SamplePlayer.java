package backgammonator.sample.players.interfacce;

import backgammonator.game.BackgammonBoard;
import backgammonator.game.CheckerMove;
import backgammonator.game.CheckerMoveType;
import backgammonator.game.Dice;
import backgammonator.game.Player;
import backgammonator.game.PlayerMove;
import backgammonator.game.Point;

/**
 * Sample implementation of the {@link Player} interface.
 */
public class SamplePlayer extends AbstractSamplePlayer {

	private static int ID = 0;
	private int id = ID++;

	public SamplePlayer() {
	}

	public SamplePlayer(int id) {
		this.id = id;
	}

	public PlayerMove getMove(BackgammonBoard board, Dice dice)
			throws Exception {
		//System.out.println("Dice: (" + dice.getDie1() + ", " + dice.getDie2()
		//		+ ")");
		CheckerMove m1, m2, m3, m4;
		InternalBoard b = new InternalBoard(board);
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

	private CheckerMove findMove(InternalBoard board, int die) {
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
		return "Sample G " + id;
	}
}

final class InternalBoard {
	private int[] pos;
	//private String color;

	public InternalBoard(BackgammonBoard board) {
		//color = board.getCurrentPlayerColor().toString();
		pos = new int[26];
		pos[25] = board.getHits(board.getCurrentPlayerColor());
		pos[0] = board.getBornOff(board.getCurrentPlayerColor());
		for (int i = 1; i < 25; i++) {
			Point p = board.getPoint(i);
			pos[i] = p.getCount()
					* (p.getColor() == board.getCurrentPlayerColor() ? 1 : -1);
		}
	}

	public int get(int i) {
		if (i < 0) {
			i = 0;
		}
		return pos[i];
	}

	public void move(int point, int die) {
		//System.out.println(color + " (" + point + ", " + die + ")");
		//System.out.println("Before: " + this);
		pos[point]--;
		int end = point - die;
		if (end < 0) {
			end = 0;
		}
		if (pos[end] == -1) {
			pos[end] = 1;
		} else {
			pos[end]++;
		}
		//System.out.println("After:  " + this + "\n");
	}

	public String toString() {
		StringBuilder s = new StringBuilder(100);
		s.append("Hit[").append(pos[25]).append("] ");
		s.append("BornOff[").append(pos[0]).append("]\n");
		for (int i = 13; i <= 18; i++) {
			s.append((pos[i] >= 0 ? " " : "") + pos[i]).append(" ");
		}
		s.append(" || ");
		for (int i = 19; i <= 24; i++) {
			s.append((pos[i] >= 0 ? " " : "") + pos[i]).append(" ");
		}
		s.append("\n");
		for (int i = 12; i >= 7; i--) {
			s.append((pos[i] >= 0 ? " " : "") + pos[i]).append(" ");
		}
		s.append(" || ");
		for (int i = 6; i >= 1; i--) {
			s.append((pos[i] >= 0 ? " " : "") + pos[i]).append(" ");
		}
		return s.toString();
	}
}