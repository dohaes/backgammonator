package backgammonator.impl.game;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import backgammonator.core.Game;
import backgammonator.core.GameOverStatus;
import backgammonator.core.Player;
import backgammonator.core.Tournament;
import backgammonator.core.TournamentConfiguration;

public class TournamentImpl implements Tournament {

	private Player players[];
	private Random randomGenerator = new Random();

	TournamentImpl(List<Player> players) {
		if (players == null || players.size() < 2) {
			throw new IllegalArgumentException("Players must be more than 2.");
		}
		this.players = new Player[players.size()];
		players.toArray(this.players);
	}

	public Player start(TournamentConfiguration config) {
		if (config == null) {
			throw new IllegalArgumentException("Config must not be null.");
		}
		switch (config.getTournamentType()) {
		case ELIMINATIONS:
			return executeElimintaions(config);
		case GROUPS:
			return executeGroups(config);
		case BATTLE:
			return executeBattle(config);
		}
		throw new NotImplementedException();
	}

	private Player executeBattle(TournamentConfiguration config) {
		return runGroup(players, config);
	}

	private Player executeGroups(TournamentConfiguration config) {
		int groups = config.getGroupsCount();
		List<Player> players = Arrays.asList(this.players);
		List<Player> winners = new LinkedList<Player>();
		int size = players.size();
		for (int i = 0; i < groups; i++) {
			int p = size / groups;
			if (i < size % groups) {
				p++;
			}
			List<Player> group = remove(players, p);
			Player winner = runGroup((Player[]) group.toArray(), config);
			winners.add(winner);
		}
		return runEliminations(winners, config);
	}

	private Player executeElimintaions(TournamentConfiguration config) {
		return runEliminations(Arrays.asList(players), config);
	}

	private Player runEliminations(List<Player> players,
			TournamentConfiguration config) {
		while (players.size() > 1) {
			List<Player> winners = new LinkedList<Player>();
			List<Player> losers = new LinkedList<Player>();
			while (players.size() > 1) {
				List<Player> chosen = remove(players, 2);
				Player first = chosen.get(0);
				Player second = chosen.get(1);
				int points[] = runBattle(first, second, config);
				// TODO equal points
				if (points[0] >= points[1]) {
					winners.add(first);
					losers.add(second);
				} else {
					winners.add(second);
					losers.add(first);
				}
			}
			if (players.size() == 1) {
				winners.add(players.remove(0));
			}
			players = winners;
		}
		return players.get(0);
	}

	private Player runGroup(Player[] players, TournamentConfiguration config) {
		int points[] = new int[players.length];
		for (int i = 0; i < players.length - 1; i++) {
			for (int j = i + 1; j < players.length; j++) {
				int p[] = runBattle(players[i], players[j], config);
				points[i] += p[0];
				points[j] += p[1];
			}
		}

		int max = points[0];
		Player winner = players[0];
		for (int j = 1; j < players.length; j++) {
			if (points[j] > max) {
				max = points[j];
				winner = players[j];
			}
		}
		return winner;
	}

	private int[] runBattle(Player first, Player second,
			TournamentConfiguration config) {
		boolean ran = randomGenerator.nextBoolean();
		GameOverStatus status = null;
		Game game = null;
		int points[] = new int[2];

		for (int i = 0; i < config.getGamesCount(); i++) {
			if (ran = !ran) {
				game = GameManager.newGame(first, second, config.isLogMoves());
			} else {
				game = GameManager.newGame(second, first, config.isLogMoves());
			}
			status = game.start();
			points[game.getWinner() == first ? 0 : 1] += getPoints(status,
					config);
		}

		return points;
	}

	private int getPoints(GameOverStatus status, TournamentConfiguration config) {
		switch (status) {
		case EXCEPTION:
			return config.getInvalidGamePoints();
		case INVALID_MOVE:
			return config.getInvalidGamePoints();
		case DOUBLE:
			return config.isPlainRate() ? 1 : 2;
		case TRIPLE:
			return config.isPlainRate() ? 1 : 3;
		default:
			return 1;
		}
	}

	private List<Player> remove(List<Player> players, int count) {
		List<Player> result = new LinkedList<Player>();
		for (int i = 0; i < count; i++) {
			result.add(players.remove(randomGenerator.nextInt(players.size())));
		}
		return result;
	}
}