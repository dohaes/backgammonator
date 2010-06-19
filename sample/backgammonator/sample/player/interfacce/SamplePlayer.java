package backgammonator.sample.player.interfacce;

import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.CheckerMove;
import backgammonator.lib.game.CheckerMoveType;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.Player;
import backgammonator.lib.game.PlayerMove;
import backgammonator.lib.game.Point;

/**
 * Sample implementation of the {@link Player} interface.
 */
public class SamplePlayer extends AbstractSamplePlayer {

	private static int ID = 0;
	private int id;

	/**
	 * Constructs new instance of the class.
	 */
	public SamplePlayer() {
		this.id = ID++;
	}

	/**
	 * Constructs new instance of the class with id.
	 */
	public SamplePlayer(int id) {
		this.id = id;
	}

	/**
	 * @see Player#getMove(BackgammonBoard, Dice)
	 */
	public PlayerMove getMove(BackgammonBoard board, Dice dice) {
		// System.out.println("Dice: (" + dice.getDie1() + ", " + dice.getDie2()
		// + ")");
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
		}
		return new PlayerMove(new CheckerMove[] { m1, m2 });
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

	/**
	 * @see Player#getName()
	 */
	public String getName() {
		return "Sample G " + id;
	}
}

final class InternalBoard {
	private int[] pos;

	public InternalBoard(BackgammonBoard board) {
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
	}
}