package backgammonator.impl.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.GameOverStatus;
import backgammonator.core.Player;
import backgammonator.core.PlayerMove;
import backgammonator.impl.protocol.Parser;
import backgammonator.util.Debug;

/**
 * Represents implementation of the {@link Player} interface. This class
 * represents the core implementation of the Player interface. PlayerImpl object
 * will be created for each uploaded and successfully complied source code of AI
 * player. For each AI player a Process will be created. PlayerImpl will
 * communicate input and output to the contestant's program using the process'
 * InputStream and OutputStream.
 */
public class PlayerImpl implements Player {

	private String command;

	private Process process;
	private InputStream stdin;
	private OutputStream stdout;

	private boolean inited = false;

	private BufferedReader reader;

	/**
	 * Constructs a new player instance.
	 * 
	 * @param command
	 *            the command to be executed to init the process with.
	 * @throws IOException
	 *             if the execution of the command throws exception.
	 */
	PlayerImpl(String command) throws IOException {
		this.command = command;
	}

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice)
			throws Exception {
		if (!inited)
			init();
		stdout.write(Parser.getBoardConfiguration(board, dice, false, null)
				.getBytes());
		return Parser.getMove(reader.readLine());
	}

	@Override
	public void gameOver(BackgammonBoard board, boolean wins,
			GameOverStatus status) {
		try {
			stdout.write(Parser
					.getBoardConfiguration(board, null, wins, status)
					.getBytes());

			int exitcode = -1;
			exitcode = process.waitFor();
			if (exitcode != 0) {
				Debug.getInstance().error("Process returned " + exitcode,
						Debug.GAME_LOGIC, null);
			}
		} catch (Exception e) {
			Debug.getInstance().error("Error occured", Debug.GAME_LOGIC, e);
		} finally {
			cleanStreams();
			process.destroy();
			process = null;
			inited = false;
		}

	}

	@Override
	public String getName() {
		// TODO should return the name of the registered user that uploaded the
		// source
		return "test user";
	}

	@Override
	protected void finalize() throws Throwable {

		try {
			stdin.close();
			stdout.close();

		} catch (Throwable t) {
			Debug.getInstance().error("Error finalizing player : " + this,
					Debug.GAME_LOGIC, t);
		}
	}

	private void cleanStreams() {

		// clean stderr
		BufferedReader cleaner = new BufferedReader(new InputStreamReader(
				process.getErrorStream()));
		try {
			while (cleaner.readLine() != null) {
			}
		} catch (IOException ioe) {
			Debug.getInstance().error("Exception while cleaning stream",
					Debug.GAME_LOGIC, ioe);
		} finally {
			try {
				cleaner.close();
				process.getErrorStream().close();
			} catch (IOException e) {
				Debug.getInstance().error("Exception while closing stream",
						Debug.GAME_LOGIC, e);
			}
		}
		// clean stdin
		cleaner = new BufferedReader(new InputStreamReader(stdin));
		try {
			while (cleaner.readLine() != null) {
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
		reader = new BufferedReader(new InputStreamReader(stdin));

		inited = true;
	}

}
