package backgammonator.sample.players.interfacce;

import backgammonator.game.BackgammonBoard;
import backgammonator.game.CheckerMove;
import backgammonator.game.Dice;
import backgammonator.game.Player;
import backgammonator.game.PlayerMove;

/**
 * Sample implementation of the {@link Player} interface.
 * This player returns an invalid move.
 */
public class InvalidMovePlayer extends AbstractSamplePlayer {

	@Override
	public PlayerMove getMove(BackgammonBoard board, Dice dice) throws Exception {
		int die1 = dice.getDie1();
		int die2 = dice.getDie2();
		
		CheckerMove chmove1 = new CheckerMove(2, die1 == 6 ? die1 - 1 : die1 + 1);
		CheckerMove chmove2 = new CheckerMove(2, die2 == 6 ? die2 - 1 : die2 + 1);
		
		return new PlayerMove(new CheckerMove[] {chmove1, chmove2});
	}

	@Override
	public String getName() {
		return "Sample Player Invalid Move";
	}

}
