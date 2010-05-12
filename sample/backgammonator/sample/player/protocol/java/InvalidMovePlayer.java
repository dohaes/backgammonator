package backgammonator.sample.player.protocol.java;

import java.util.Scanner;

/**
 * Sample implementation of a player using the protocol. This player returns an
 * invalid move.
 */
public class InvalidMovePlayer {

	private int[] count = new int[25]; // to index from 1 to 24
	private int[] possesion = new int[25]; // to index from 1 to 24

	private int hits_mine;
	private int hits_opponent;
	private int bornoff_mine;
	private int bornoff_opponent;

	private int die1;
	private int die2;

	private int status = 0; // 0 - continue playing

	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		new InvalidMovePlayer().start();
	}

	private void start() {
		readInput();
		while (status == 0) {
			System.out.println("2 " + (die1 == 6 ? die1 - 1 : die1 + 1) + " 2 "
					+ (die2 == 6 ? die2 - 1 : die2 + 1));
			readInput();
		}

	}

	private void readInput() {

		// read the board configuration
		for (int i = 1; i <= 24; i++) {
			count[i] = scanner.nextInt();
			possesion[i] = scanner.nextInt();
		}

		// read hits and bornoffs
		hits_mine = scanner.nextInt();
		hits_opponent = scanner.nextInt();
		bornoff_mine = scanner.nextInt();
		bornoff_opponent = scanner.nextInt();

		// read the dice
		die1 = scanner.nextInt();
		die2 = scanner.nextInt();

		// read the status
		status = scanner.nextInt();
	}
}
