package backgammonator.lib.tournament;

import backgammonator.lib.game.Player;

/**
 * @author georgi.b.andreev
 */
public interface TournamentResult {

	/**
	 * @return the winner in the tournament.
	 * @throws TournamentException if the position is incorrect.
	 */
	Player getWinner() throws TournamentException;

	/**
	 * @return the players count.
	 */
	int getPlayersCount();

	/**
	 * @param position of the player.
	 * @return the player on that rank.
	 * @throws TournamentException if the position is incorrect.
	 */
	Player getPlayer(int position) throws TournamentException;

	/**
	 * @param position the position of the player.
	 * @return the points of the specified player.
	 * @throws TournamentException if the position is incorrect.
	 */
	int getPlayerPoints(int position) throws TournamentException;
}