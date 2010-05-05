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
 * Represents implementation of the {@link Player} interface.
 * This class represents the core implementation of the Player interface. PlayerImpl object will be created
 * for each uploaded and successfully complied source code of AI player. For each AI player a Process will
 * be created. PlayerImpl will communicate input and output to the contestant's program using the process'
 * InputStream and OutputStream.
 */
public class PlayerImpl implements Player {
	
	private Process process;
	private InputStream stdin;
	private OutputStream stdout;
	
	private BufferedReader reader;
	
	private BackgammonBoard board;
	
	PlayerImpl(Process process) {
		this.process = process;
		stdin = process.getInputStream();
		stdout = process.getOutputStream();
		
		reader = new BufferedReader(new InputStreamReader(stdin));
		
	}

	@Override
	public void gameOver(boolean wins, GameOverStatus status) {
		try {
			//TODO consider the protocol!!!!! what happens on game over?
			//TODO is it needed to send strings with the same format every time? 
			stdout.write(Parser.getBoardConfiguration(board, null, true, wins, status).getBytes());
		
			int exitcode = -1;
			exitcode = process.waitFor();
			if (exitcode != 0) throw new RuntimeException("Process returned " + exitcode);
		
		} catch (Exception e) {
			Debug.getInstance().error("Error occured", Debug.GAME_LOGIC, e);
			throw new RuntimeException(e);
		} finally {
			cleanStreams();
		}
		
	}

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice) throws Exception {
		stdout.write(Parser.getBoardConfiguration(board, dice, false, false, GameOverStatus.OK).getBytes());
		return Parser.getMove(reader.readLine());
	}

	@Override
	public String getName() {
		// TODO should return the name of the registered user that uploaded the source
		return "test user";
	}
	
	@Override
	protected void finalize() throws Throwable {
		
		try {
			stdin.close();
			stdout.close();
			
		} catch (Throwable t) {
			Debug.getInstance().error("Error finalizing player : " + this, Debug.GAME_LOGIC, t);
		}
	}
	
	private void cleanStreams() {
		
		//clean stderr
		BufferedReader cleaner = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		try {
			while (cleaner.readLine() != null) { }
		} catch (IOException ioe) {
			Debug.getInstance().error("Exception while cleaning stream", Debug.GAME_LOGIC, ioe);
		} finally {
			try {
				cleaner.close();
				process.getErrorStream().close();
			} catch (IOException e) {
				Debug.getInstance().error("Exception while closing stream", Debug.GAME_LOGIC, e);
			}
		}
		//clean stdin
		cleaner = new BufferedReader(new InputStreamReader(stdin));
		try {
			while (cleaner.readLine() != null) { }
		} catch (IOException ioe) {
			Debug.getInstance().error("Exception while cleaning stream", Debug.GAME_LOGIC, ioe);
		} finally {
			try {
				cleaner.close();
				stdin.close();
			} catch (IOException e) {
				Debug.getInstance().error("Exception while closing stream", Debug.GAME_LOGIC, e);
			}
		}
	}

}
