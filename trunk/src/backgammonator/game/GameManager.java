package backgammonator.game;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.Player;
import backgammonator.core.MoveValidator;
import backgammonator.core.PlayerColor;

/**
 * An instance of this class is created for each game between two players.
 * It is used for realization of the rules of backgammon and manages the game. With each instance of the
 * GameManager class a {@link BackgammonBoard} object is associated. Class GameManager
 * uses class {@link MoveValidator} to validate the palyers' moves.
 */

public final class GameManager {
	
	private Player player1;
	private Player player2;
	
	private static final long MOVE_TIMEOUT = 1000;
	
	private BackgammonBoard board;
	private Dice dice;
	
	
	/**
	 * Constructs a game between two AI players.
	 */
	public GameManager(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		board = new BackgammonBoard();
		dice = new Dice();
	}
	
	/**
	 * This method starts and navigates the game between the two players. 
	 */
	public void start() {
		boolean flag = true;
		Player current;
		
		while (flag) {
			
		}
		
	}
	
	class WatchDog extends Thread {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
		}
	}
	
	

}
