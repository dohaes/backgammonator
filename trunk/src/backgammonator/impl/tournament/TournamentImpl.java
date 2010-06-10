package backgammonator.impl.tournament;

import java.util.LinkedList;

import java.util.List;
import java.util.Random;

import backgammonator.impl.game.GameManager;
import backgammonator.impl.logger.TournamentLoggerFactory;
import backgammonator.lib.game.Game;
import backgammonator.lib.game.GameOverStatus;
import backgammonator.lib.game.Player;
import backgammonator.lib.logger.TournamentLogger;
import backgammonator.lib.tournament.Tournament;
import backgammonator.lib.tournament.TournamentConfiguration;

/**
 * @author georgi.b.andreev
 */
public class TournamentImpl implements Tournament {

	private List<Player> players;
	private Random randomGenerator = new Random();

	TournamentImpl(List<Player> players) {
		if (players == null || players.size() < 2) {
			throw new IllegalArgumentException("Players must be more than 2.");
		}
		this.players = new LinkedList<Player>(players);
	}

	/**
	 * @see backgammonator.lib.tournament.Tournament#start(backgammonator.lib.tournament.TournamentConfiguration)
	 */
	public Player start(TournamentConfiguration config) {
		if (config == null) {
			throw new IllegalArgumentException("Config must not be null.");
		}
		List<Player> players = new LinkedList<Player>(this.players);
		TournamentLogger logger = TournamentLoggerFactory
				.getLogger(TournamentLoggerFactory.HTML);
		logger.startTournament(players, config.getTournamentType());
		Player winner = null;
		switch (config.getTournamentType()) {
		case ELIMINATIONS:
			winner = executeElimintaions(players, config, logger);
			break;
		case GROUPS:
			winner = executeGroups(players, config, logger);
			break;
		case BATTLE:
			winner = executeBattle(players, config, logger);
			break;
		}
		logger.endTournament(winner);
		return winner;
	}

	private Player executeBattle(List<Player> players,
			TournamentConfiguration config, TournamentLogger logger) {
		return runGroup(players, config, logger);
	}

	private Player executeElimintaions(List<Player> players,
			TournamentConfiguration config, TournamentLogger logger) {
		return runEliminations(players, config, logger);
	}

	private Player executeGroups(List<Player> players,
			TournamentConfiguration config, TournamentLogger logger) {
		int groups = config.getGroupsCount();
		List<Player> winners = new LinkedList<Player>();
		int size = players.size();
		for (int i = 0; i < groups; i++) {
			int p = size / groups;
			if (i < size % groups) {
				p++;
			}
			List<Player> group = remove(players, p);
			Player winner = runGroup(group, config, logger);
			winners.add(winner);
		}
		return runEliminations(winners, config, logger);
	}

	private Player runEliminations(List<Player> players,
			TournamentConfiguration config, TournamentLogger logger) {
		while (players.size() > 1) {
			List<Player> winners = new LinkedList<Player>();
			List<Player> losers = new LinkedList<Player>();
			while (players.size() > 1) {
				List<Player> chosen = remove(players, 2);
				Player first = chosen.get(0);
				Player second = chosen.get(1);
				int points[] = runBattle(first, second, config, logger);
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

	private Player runGroup(List<Player> players,
			TournamentConfiguration config, TournamentLogger logger) {
		int points[] = new int[players.size()];
		for (int i = 0; i < players.size() - 1; i++) {
			for (int j = i + 1; j < players.size(); j++) {
				int p[] = runBattle(players.get(i), players.get(j), config,
						logger);
				points[i] += p[0];
				points[j] += p[1];
			}
		}

		int max = points[0];
		Player winner = players.get(0);
		for (int i = 1; i < players.size(); i++) {
			if (points[i] > max) {
				max = points[i];
				winner = players.get(i);
			}
		}
		return winner;
	}

	private int[] runBattle(Player first, Player second,
			TournamentConfiguration config, TournamentLogger logger) {
		boolean ran = randomGenerator.nextBoolean();
		GameOverStatus status = null;
		Game game = null;
		int points[] = new int[2];

		for (int i = 0; i < config.getGamesCount(); i++) {
			if (ran = !ran) {
				game = GameManager.newGame(first, second, config.isLogMoves());
				status = game.start();
				logger.logGame(first, second, game, status);
			} else {
				game = GameManager.newGame(second, first, config.isLogMoves());
				status = game.start();
				logger.logGame(second, first, game, status);
			}
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