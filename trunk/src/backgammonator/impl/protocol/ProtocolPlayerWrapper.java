package backgammonator.impl.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;
import backgammonator.lib.game.PlayerMove;
import backgammonator.util.Debug;

/**
 * Represents implementation of the {@link Player} interface. This class
 * represents the core implementation of the Player interface. PlayerImpl object
 * will be created for each uploaded and successfully complied source code of AI
 * player. For each AI player a Process will be created. PlayerImpl will
 * communicate input and output to the contestant's program using the process'
 * InputStream and OutputStream.
 */
final class ProtocolPlayerWrapper implements Player {

	private String command;
	// TODO should return the name of the registered user that uploaded the
	// source
	private String name = "test_user";

	private Process process;
	private InputStream stdin;
	private InputStream stderr;
	private OutputStream stdout;

	private boolean inited = false;

	private Scanner scanner;

	/**
	 * Constructs a new player instance.
	 * 
	 * @param command the command to be executed to init the process with.
	 * @param name the name of the player - same as the username of the user who
	 *            uploaded the source.
	 */
	public ProtocolPlayerWrapper(String command, String name) {
		this.command = command;
		this.name = name;
	}

	/**
	 * @see Player#getMove(BackgammonBoard, Dice)
	 */
	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice)
			throws Exception {
		if (!inited) init();
		stdout.write(ProtocolManager.getParser().getBoardConfiguration(board, dice, false,
				null).getBytes());
		stdout.flush();

		try {
			String nextLine = scanner.nextLine();
			return ProtocolManager.getParser().getMove(nextLine);
		} catch (NoSuchElementException nse) {
			// the process has unexpectedly died
			// pipe is being closed and streams are empty
			throw new IllegalStateException("Process for player " + name
					+ " has been unexpectedly terminated", nse);
		} catch (IllegalArgumentException iae) {
			// invalid move
			// the parser cannot parse the returned string
			// the move is not formatted according to the protocol
			return null;
		}
	}

	/**
	 * @see Player#gameOver(BackgammonBoard, boolean, GameOverStatus)
	 */
	@Override
	public void gameOver(BackgammonBoard board, boolean wins,
			GameOverStatus status) {

		try {
			try {
				if (!inited) init();
				stdout.write(ProtocolManager.getParser().getBoardConfiguration(board, null,
						wins, status).getBytes());
				stdout.flush();
			} catch (Throwable ioe) {
				Debug.getInstance().error(
						"Error sending final results to player " + name,
						Debug.GAME_LOGIC, ioe);
			}

			Debug.getInstance().info("Stopping player " + name,
					Debug.GAME_LOGIC);

			// the player is not responding
			if (status == GameOverStatus.TIMEDOUT && !wins) {
				Debug
						.getInstance()
						.warning(
								"Player "
										+ name
										+ " is not responding. It will be forced to stop.",
								Debug.GAME_LOGIC, null);
				process.destroy();
			} else {
				int exitcode = process.waitFor();
				Debug.getInstance().info(
						"Process for player " + name + " returned " + exitcode,
						Debug.GAME_LOGIC);
			}
		} catch (Exception e) {
			Debug.getInstance().error("Error occured", Debug.GAME_LOGIC, e);
		} finally {
			cleanStreams();
			process = null;
			inited = false;
		}

	}

	/**
	 * @see Player#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Finalizes the Player object.
	 */
	@Override
	protected void finalize() {

		try {
			if (stdin != null) stdin.close();
			if (stdout != null) stdout.close();
			if (stderr != null) stderr.close();

		} catch (Throwable t) {
			Debug.getInstance().error("Error finalizing player : " + this,
					Debug.GAME_LOGIC, t);
		}
	}

	private void cleanStreams() {
		String line = null;
		// clean stderr
		BufferedReader cleaner = new BufferedReader(new InputStreamReader(
				stderr));
		try {
			while ((line = cleaner.readLine()) != null) {
				System.out.println("	[" + name + "] [stderr] " + line);
			}
		} catch (IOException ioe) {
			Debug.getInstance().error("Exception while cleaning stream",
					Debug.GAME_LOGIC, ioe);
		} finally {
			try {
				cleaner.close();
				stderr.close();
			} catch (IOException e) {
				Debug.getInstance().error("Exception while closing stream",
						Debug.GAME_LOGIC, e);
			}
		}
		// clean stdin
		cleaner = new BufferedReader(new InputStreamReader(stdin));
		try {
			while ((line = cleaner.readLine()) != null) {
				System.out.println("	[" + name + "] [stdout] " + line);
			}
		} catch (IOException ioe) {
			Debug.getInstance().error("Exception while cleaning stream",
					Debug.GAME_LOGIC, ioe);
		} finally {
			try {
				cleaner.close();
				stdin.close();
			} catch (IOException e) {
				Debug.getInstance().error("Exception while closing stream",
						Debug.GAME_LOGIC, e);
			}
		}
	}

	private void init() throws IOException {
		process = Runtime.getRuntime().exec(command);
		stdin = process.getInputStream();
		stdout = process.getOutputStream();
		stderr = process.getErrorStream();
		scanner = new Scanner(stdin);

		inited = true;
	}

}
