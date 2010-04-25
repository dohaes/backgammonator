package backgammonator.game;

import backgammonator.core.BackgammonBoard;
import backgammonator.core.Dice;
import backgammonator.core.GameOverStatus;
import backgammonator.core.Logger;
import backgammonator.core.Player;
import backgammonator.core.MoveValidator;
import backgammonator.core.PlayerMove;

/**
 * An instance of this class is created for each game between two players.
 * It is used for realization of the rules of backgammon and manages the game. With each instance of the
 * GameManager class a {@link BackgammonBoard} object is associated. Class GameManager
 * uses class {@link MoveValidator} to validate the palyers' moves.
 */

public final class GameManager {
	
	private Player whitePlayer;
	private Player blackPlayer;
	
	private static final long MOVE_TIMEOUT = 1000;
	
	private BackgammonBoard board;
	private Dice dice;
	private Logger logger;
	
	/**
	 * Constructs a game between two AI players.
	 */
	public GameManager(Player whitePlayer, Player blackPlayer) {
		this.whitePlayer = whitePlayer;
		this.blackPlayer = blackPlayer;
		board = new BackgammonBoard();
		dice = new Dice();
		logger = new HTMLLogger();
	}
	
	/**
	 * This method starts and navigates the game between the two players.
	 * Always the white player is first.
	 */
	public void start() {
		
		GameOverStatus status = null;
		logger.startGame(whitePlayer, blackPlayer);
		
		while (true) {
			status = makeMove(whitePlayer, blackPlayer);
			if (status != null) break;
			status = makeMove(blackPlayer, whitePlayer);
			if (status != null) break;
		}
		
		logger.endGame(status);
	}
	
	/**
	 * Return the end game status if the game is over, on null otherwise
	 */
	private GameOverStatus makeMove(Player currentPlayer, Player other) {
		PlayerMove currentMove;
		dice.generateNext();
		try {
			currentMove = currentPlayer.getMove(board, dice);
			if (currentMove == null || !MoveValidator.validateMove(board, currentMove, dice)) {
				currentPlayer.gameOver(false);
				other.gameOver(true);
				return GameOverStatus.INVALID_MOVE;
			}
			logger.logMove(currentMove, dice, board.getHits(currentPlayer.getColor()),
					                          board.getBornOff(currentPlayer.getColor()));
			board.makeMove(currentMove);
			if (board.getBornOff(currentPlayer.getColor()) == 15) {
				currentPlayer.gameOver(true);
				other.gameOver(false);
				return GameOverStatus.OK;
			}
		} catch (Exception e) {
			currentPlayer.gameOver(false);
			other.gameOver(true);
			return GameOverStatus.EXCEPTION;
		}
		
		return null;
	}
	
}

