package backgammonator.impl.protocol;

import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.CheckerMove;
import backgammonator.lib.game.CheckerMoveType;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.PlayerMove;

/**
 * The instance of this class is used for parsing strings, received from AI
 * players to Move objects and for creating strings for them from Board objects.
 */

public final class Parser {

	/**
	 * Parses the AI player's output string to PlayerMove object.
	 * 
	 * @param playerMoveString is the AI player's output string. It should be in
	 *            the following format(values must be separated with spaces):<br />
	 *            &lt;start_point_1&gt; &lt;move_length_1&gt;
	 *            &lt;start_point_2&gt; &lt;move_length_2&gt; [
	 *            &lt;start_point_3&gt; &lt;move_length_3&gt;
	 *            &lt;start_point_4&gt; &lt;move_length_4&gt;]<br />
	 *            Where<br />
	 *            <b>&lt;start_point_n&gt;</b> is the start point of the n-th
	 *            checker move - integer number between 0 and 25. If the AI
	 *            player reenters a hit checker he should use 25 for start
	 *            point. If the AI player can't move, he should use 0 for start
	 *            point.<br />
	 *            <b>&lt;move_length_n&gt;</b> is the length of the n-th checker
	 *            move - integer number between 1 and 6.<br />
	 *            The number of checker moves might be 4 when the dice are
	 *            double.
	 * @return the parsed PlayerMove object.
	 * @throws IllegalArgumentException when the given string is not in the
	 *             requested format.
	 */
	public static PlayerMove getMove(String playerMoveString)
			throws IllegalArgumentException {

		if (playerMoveString == null) {
			throw new NullPointerException("playerMoveString is null.");
		}

		String[] stringArray = playerMoveString.split(" ");

		if (stringArray.length != 4 && stringArray.length != 8) {
			throw new IllegalArgumentException("playerMoveString has "
					+ stringArray.length
					+ " elements, separated with spaces. But they must 4 or 8.");
		}

		int[] integers = null;

		try {
			integers = new int[stringArray.length];
			for (int i = 0; i < integers.length; i++) {
				integers[i] = Integer.parseInt(stringArray[i]);
				if (i % 2 == 0 && (integers[i] < 0 || integers[i] > 25)) {
					throw new IllegalArgumentException(
							"Illegal value for start point number occured.");
				} else if (i % 2 == 1 && (integers[i] < 1 || integers[i] > 6)) {
					throw new IllegalArgumentException(
							"Illegal value for move lenght occured.");
				}
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Could not parse playerMoveString to list of integers.");
		}

		CheckerMove[] checkerMoves = new CheckerMove[integers.length / 2];
		for (int i = 0; i < checkerMoves.length; i++) {

			// determine checker move's type by the given start position
			switch (integers[i * 2]) {
			case 0:
				checkerMoves[i] = new CheckerMove(
						CheckerMoveType.NO_AVAILABLE_MOVE, integers[i * 2 + 1]);
				break;

			case 25:
				checkerMoves[i] = new CheckerMove(
						CheckerMoveType.REENTER_HIT_CHECKER,
						integers[i * 2 + 1]);
				break;

			default:
				checkerMoves[i] = new CheckerMove(integers[i * 2],
						integers[i * 2 + 1]);
				break;
			}
		}

		return new PlayerMove(checkerMoves);
	}

	/**
	 * Converts the information needed to AI player to make its move from
	 * BackgammonBoard and Dice objects to string, which will be passed to the
	 * AI player's input.
	 * 
	 * @param board is the BackgammonBoard object, representing the current
	 *            board state.
	 * @param dice is the Dice object for the AI player's move.
	 *            <code>null</code> if the game is over.
	 * @param isCurrentPlayerWon determines if the current player is the winner.
	 * @param status is the game over status. <code>null</code> if the game is
	 *            not over yet
	 * @return a string, representing the current board state and dice. Suitable
	 *         for passing to AI player's input. It will be in the following
	 *         format(the values are separated with spaces):<br />
	 *         &lt;count_1&gt; &lt;possession_1&gt; &lt;count_2&gt;
	 *         &lt;possession_2&gt; ... &lt;count_24&gt; &lt;possession_24&gt;
	 *         &lt;hits_current&gt; &lt;hits_opponent&gt;
	 *         &lt;bornoff_current&gt; &lt;bornoff_opponent&gt; &lt;die_1&gt;
	 *         &lt;die_2&gt; &lt;game_status&gt;<br />
	 *         Where<br />
	 *         <b>&lt;count_n&gt;</b> is the number of checkers in point n -
	 *         integer number between 0 and 15.<br />
	 *         <b>&lt;possession_n&gt;</b> is the player whose are the checkers
	 *         in point n - integer number between 0 and 1 (0 for current
	 *         player, 1 for opponent player).<br />
	 *         <b>&lt;hits_current&gt;</b> and <b>&lt;hits_opponent&gt;</b> are
	 *         the numbers of hit checkers for the current and opponent players
	 *         respectively - integer number between 0 and 15.<br/>
	 *         <b>&lt;bornoff_current&gt;</b> and
	 *         <b>&lt;bornoff_opponent&gt;</b> are the numbers of born off
	 *         checkers for the current and opponent players respectively -
	 *         integer number between 0 and 15.<br/>
	 *         <b>&lt;die_1&gt;</b> and <b>&lt;die_2&gt;</b> are the dices'
	 *         values - integer numbers between 1 and 6.<br />
	 *         <b>&lt;game_status&gt;</b> gives information for the current game
	 *         status:<br />
	 *         0: Game is not over yet<br />
	 *         1: Standard win for the current player<br />
	 *         2: Double win for the current player<br />
	 *         3: Triple win for the current player<br />
	 *         4: Exception game end and the current player wins<br />
	 *         5: Invalid move and the current player wins<br />
	 *         6: Game end due timeout and the current player wins<br />
	 *         7: Standard win for the opposite player<br />
	 *         8: Double win for the opposite player<br />
	 *         9: Triple win for the opposite player<br />
	 *         10: Exception game end and the opposite player wins<br />
	 *         11: Invalid move and the opposite player wins<br />
	 *         12: Game end due timeout and the opposite player wins<br />
	 */
	public static String getBoardConfiguration(BackgammonBoard board,
			Dice dice, boolean isCurrentPlayerWon, GameOverStatus status) {

		if (board == null) {
			throw new NullPointerException("board is null.");
		}
		if (dice == null && status == null) {
			throw new NullPointerException(
					"dice is null, but status is null(the game is not over).");
		}
		StringBuffer stringBuffer = new StringBuffer(150);
		for (int pointNumber = 1; pointNumber <= 24; pointNumber++) {
			stringBuffer.append(board.getPoint(pointNumber).getCount());
			stringBuffer.append(" ");
			stringBuffer
					.append((board.getPoint(pointNumber).getColor() == board
							.getCurrentPlayerColor()) ? 0 : 1);
			stringBuffer.append(" ");
		}
		stringBuffer.append(board.getHits(board.getCurrentPlayerColor()));
		stringBuffer.append(" ");
		stringBuffer.append(board.getHits(board.getCurrentPlayerColor()
				.opposite()));
		stringBuffer.append(" ");
		stringBuffer.append(board.getBornOff(board.getCurrentPlayerColor()));
		stringBuffer.append(" ");
		stringBuffer.append(board.getBornOff(board.getCurrentPlayerColor()
				.opposite()));
		stringBuffer.append(" ");
		if (dice != null) {
			stringBuffer.append(dice.getDie1());
			stringBuffer.append(" ");
			stringBuffer.append(dice.getDie2());
			stringBuffer.append(" ");
		} else {
			stringBuffer.append("1 1 ");
		}

		int gameOverStatusCode;

		if (status == null) {
			gameOverStatusCode = 0;
		} else {
			switch (status) {
			case NORMAL:
				gameOverStatusCode = 1;
				break;
			case DOUBLE:
				gameOverStatusCode = 2;
				break;
			case TRIPLE:
				gameOverStatusCode = 3;
				break;
			case EXCEPTION:
				gameOverStatusCode = 4;
				break;
			case INVALID_MOVE:
				gameOverStatusCode = 5;
				break;
			case TIMEDOUT:
				gameOverStatusCode = 6;
				break;
			default:
				gameOverStatusCode = 0;
				break;
			}
			if (isCurrentPlayerWon == false && gameOverStatusCode != 0) {
				gameOverStatusCode += 6;
			}
		}

		stringBuffer.append(gameOverStatusCode);
		stringBuffer.append(" ");

		return stringBuffer.toString();
	}
}
