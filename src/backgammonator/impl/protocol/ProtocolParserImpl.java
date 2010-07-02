package backgammonator.impl.protocol;

import backgammonator.lib.game.BackgammonBoard;
import backgammonator.lib.game.CheckerMove;
import backgammonator.lib.game.CheckerMoveType;
import backgammonator.lib.game.Dice;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.PlayerMove;
import backgammonator.lib.protocol.ProtocolParser;

/**
 * @see ProtocolParser
 */

public final class ProtocolParserImpl implements ProtocolParser {

	/**
	 * @see ProtocolParser#getMove(String)
	 * @throws IllegalArgumentException when the given string is not in the
	 *             requested format.
	 */
	public PlayerMove getMove(String playerMoveString)
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
	 * @see ProtocolParser#getBoardConfiguration(BackgammonBoard, Dice, boolean,
	 *      GameOverStatus)
	 */
	public String getBoardConfiguration(BackgammonBoard board, Dice dice,
			boolean isCurrentPlayerWon, GameOverStatus status) {

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
