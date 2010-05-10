package backgammonator.sample.player.protocol.java;

import java.util.Scanner;

/**
 * Sample implementation of a player using the protocol.
 */
public class SamplePlayer {

	private static int ID = 0;
	private int id = ID++;
	
	private int [] count = new int[25]; // to index from 1 to 24
	private int [] possesion = new int[25]; // to index from 1 to 24
	
	private int hits_mine;
	private int hits_opponent;
	private int bornoff_mine;
	private int bornoff_opponent;
	
	private int die1;
	private int die2;
	
	private int status = 0; // 0 - continue playing
	
	private Scanner scanner = new Scanner(System.in);
	
	
	public static void main(String[] args) {
		new SamplePlayer().start();
	}
	
	
	private void start() {
		readInput();
		while (status == 0) {
			System.out.println(getMove());
			readInput();
		}

	}
	
	private void readInput() {
		
		//read the board configuration
		for (int i = 1; i <= 24; i ++) {
			count[i] = scanner.nextInt();
			possesion[i] = scanner.nextInt();
		}
		
		//read hits and bornoffs
		hits_mine = scanner.nextInt();
		hits_opponent = scanner.nextInt();
		bornoff_mine = scanner.nextInt();
		bornoff_opponent = scanner.nextInt();
		
		//read the dice
		die1 = scanner.nextInt();
		die2 = scanner.nextInt();
		
		//read the status
		status = scanner.nextInt();
	}

	public SamplePlayer() {
	}
	
	public SamplePlayer(int id) {
		this.id = id;
	}

	private String getMove() {
		String m1, m2, m3, m4;
		InternalBoard b = new InternalBoard();
		m1 = findMove(b, die1);
		m2 = findMove(b, die2);
		if (m1.startsWith("0 ")) { //is unavailable
			m1 = m2;
			m2 = findMove(b, die1);
		}
		if (die1 == die2) {
			m3 = findMove(b, die1);
			m4 = findMove(b, die2);
			return m1 + " " + m2 + " " + m3 + " " + m4;
		} else {
			return m1 + " " + m2;
		}
	}

	private String findMove(InternalBoard board, int die) {
		if (board.get(25) > 0) {
			if (board.get(25 - die) >= -1) {
				board.move(25, die);
				return "25 " + die; //reenter
			}
		} else {
			for (int i = 24; i >= 1; i--) {
				if (board.get(i) > 0 && board.get(i - die) >= -1) {
					board.move(i, die);
					return i + " " + die; //nornal
				}
			}
		}
		return "0 " + die; //cant move
	}
	
	final class InternalBoard {
		
		private int[] pos;

		public InternalBoard() {
			pos = new int[26];
			pos[25] = hits_mine; //current player's hits
			pos[0] = bornoff_mine; //current player's borrnoffs
			
			for (int i = 1; i < 25; i++) {
				pos[i] = count[i] * (possesion[i] == 0 ? 1 : -1);
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
}

