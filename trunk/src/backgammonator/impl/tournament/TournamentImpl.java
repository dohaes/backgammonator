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
import backgammonator.lib.tournament.TournamentException;
import backgammonator.lib.tournament.TournamentResult;
import backgammonator.util.BackgammonatorConfig;

/**
 * @author georgi.b.andreev
 */
public class TournamentImpl implements Tournament {

	private List<Player> players;
	private Random randomGenerator = new Random();

	private static final boolean usePlainRate = !"special"
			.equals(BackgammonatorConfig
					.getProperty("backgammonator.tournament.rate"));
	private static final int invalidGamePoints = BackgammonatorConfig
			.getProperty("backgammonator.tournament.invalid.game.points", 1);

	TournamentImpl(List<Player> players) throws TournamentException {
		if (players == null || players.size() < 2) {
			throw new TournamentException("Players must be more than 2.");
		}
		this.players = new LinkedList<Player>(players);
	}

	/**
	 * @see backgammonator.lib.tournament.Tournament#start(backgammonator.lib.tournament.TournamentConfiguration)
	 */
	public TournamentResult start(TournamentConfiguration config)
			throws TournamentException {
		if (config == null) {
			throw new TournamentException("Config must not be null.");
		}
		List<Player> players = new LinkedList<Player>(this.players);
		TournamentLogger logger = TournamentLoggerFactory
				.getLogger(TournamentLoggerFactory.HTML);
		logger.startTournament(players, config.getTournamentType());
		TournamentResult result = null;
		switch (config.getTournamentType()) {
		case ELIMINATIONS:
			result = executeElimintaions(players, config, logger);
			break;
		case GROUPS:
			result = executeGroups(players, config, logger);
			break;
		case BATTLE:
			result = executeBattle(players, config, logger);
			break;
		default:
			throw new TournamentException("Invalid tournament type.");
		}
		logger.endTournament(result.getWinner());
		return result;
	}

	private TournamentResult executeBattle(List<Player> players,
			TournamentConfiguration config, TournamentLogger logger)
			throws TournamentException {
		if (players == null || players.size() < 2) {
			throw new TournamentException("Players must be more than 2.");
		}
		return runGroup(players, config, logger);
	}

	private TournamentResult executeElimintaions(List<Player> players,
			TournamentConfiguration config, TournamentLogger logger)
			throws TournamentException {
		if (players == null || players.size() < 2
				|| (players.size() & (players.size() - 1)) != 0) {
			throw new TournamentException("Players must be more than 2.");
		}
		return runEliminations(players, config, logger);
	}

	private TournamentResult executeGroups(List<Player> players,
			TournamentConfiguration config, TournamentLogger logger)
			throws TournamentException {
		if (players == null
				|| players.size() < 2
				|| (config.getGroupsCount() & (config.getGroupsCount() - 1)) != 0) {
			throw new TournamentException("Players must be more than 2.");
		}
		int groups = config.getGroupsCount();
		List<Player> winners = new LinkedList<Player>();
		int size = players.size();
		TournamentResultImpl list = new TournamentResultImpl();
		for (int i = 0; i < groups; i++) {
			int p = size / groups;
			if (i < size % groups) {
				p++;
			}
			List<Player> group = remove(players, p);
			TournamentResult result = runGroup(group, config, logger);
			for (int j = result.getPlayersCount() - 1; j >= 1; j--) {
				list.addFirst(result.getPlayer(j), result.getPlayerPoints(j));
			}
			winners.add(result.getWinner());
		}
		list.sort();
		TournamentResultImpl result = runEliminations(winners, config, logger);
		list.addFirst(result, 3 * config.getGamesCount() * (size / groups + 1));
		return result;
	}

	private TournamentResultImpl runEliminations(List<Player> players,
			TournamentConfiguration config, TournamentLogger logger)
			throws TournamentException {
		TournamentResultImpl result = new TournamentResultImpl();
		int rounds = 0;
		while (players.size() > 1) {
			List<Player> winners = new LinkedList<Player>();
			List<Player> losers = new LinkedList<Player>();
			TournamentResultImpl list = new TournamentResultImpl();
			while (players.size() > 1) {
				List<Player> chosen = remove(players, 2);
				Player first = chosen.get(0);
				Player second = chosen.get(1);
				int points[] = runBattle(first, second, config, logger);
				if (points[0] == points[1]) {
					points = runBattle(first, second, config, logger);
				}
				if (points[0] >= points[1]) {
					winners.add(first);
					losers.add(second);
					list.addFirst(second, points[1]);
				} else {
					winners.add(second);
					losers.add(first);
					list.addFirst(first, points[0]);
				}
			}
			if (players.size() == 1) {
				winners.add(players.remove(0));
			}
			players = winners;
			list.sort();
			result.addFirst(list, 3 * config.getGamesCount() * rounds);
			rounds++;
		}
		result.addFirst(players.get(0), 3 * config.getGamesCount() * rounds);
		return result;
	}

	private TournamentResultImpl runGroup(List<Player> players,
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

		TournamentResultImpl result = new TournamentResultImpl();
		for (int i = 0; i < players.size(); i++) {
			result.addFirst(players.get(i), points[i]);
		}
		result.sort();
		return result;
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
			points[game.getWinner() == first ? 0 : 1] += getPoints(status);
		}

		return points;
	}

	private int getPoints(GameOverStatus status) {
		switch (status) {
		case EXCEPTION:
			return invalidGamePoints;
		case INVALID_MOVE:
			return invalidGamePoints;
		case DOUBLE:
			return usePlainRate ? 1 : 2;
		case TRIPLE:
			return usePlainRate ? 1 : 3;
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