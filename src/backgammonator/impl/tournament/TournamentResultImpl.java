package backgammonator.impl.tournament;

import java.util.Arrays;
import java.util.LinkedList;

import backgammonator.lib.game.Player;
import backgammonator.lib.tournament.TournamentException;
import backgammonator.lib.tournament.TournamentResult;

/**
 * Represents th eresult form the tournament.
 */
public class TournamentResultImpl implements TournamentResult {

	LinkedList<Player> players = new LinkedList<Player>();
	LinkedList<Integer> points = new LinkedList<Integer>();

	/**
	 * @see backgammonator.lib.tournament.TournamentResult#getPlayer(int)
	 */
	public Player getPlayer(int position) throws TournamentException {
		if (position >= players.size()) {
			throw new TournamentException(
					"Position must be less than players count, was : "
							+ position);
		}
		return players.get(position);
	}

	/**
	 * @see backgammonator.lib.tournament.TournamentResult#getPlayerPoints(int)
	 */
	public int getPlayerPoints(int position) throws TournamentException {
		if (position >= players.size()) {
			throw new TournamentException(
					"Position must be less than players count, was : "
							+ position);
		}
		return points.get(position).intValue();
	}

	/**
	 * @see backgammonator.lib.tournament.TournamentResult#getPlayersCount()
	 */
	public int getPlayersCount() {
		return players.size();
	}

	/**
	 * @see backgammonator.lib.tournament.TournamentResult#getWinner()
	 */
	public Player getWinner() throws TournamentException {
		return getPlayer(0);
	}

	void addFirst(TournamentResultImpl list, int p) throws TournamentException {
		for (int i = list.getPlayersCount() - 1; i >= 0; i--) {
			players.addFirst(list.getPlayer(i));
			points.addFirst(list.getPlayerPoints(i) + p);
		}
	}

	void addFirst(Player player, int p) {
		players.addFirst(player);
		points.addFirst(p);
	}

	void sort() {
		Couple couples[] = new Couple[players.size()];
		for (int i = 0; i < players.size(); i++) {
			couples[i] = new Couple(players.get(i), points.get(i));
		}
		players.clear();
		points.clear();
		Arrays.sort(couples);
		for (int i = 0; i < couples.length; i++) {
			players.addFirst(couples[i].player);
			points.addFirst(couples[i].points);
		}
	}

	class Couple implements Comparable<Couple> {

		Integer points;
		Player player;

		Couple(Player player, Integer points) {
			this.player = player;
			this.points = points;
		}

		public int compareTo(Couple c) {
			return points < c.points ? -1 : points > c.points ? 1 : 0;
		}
	}
}