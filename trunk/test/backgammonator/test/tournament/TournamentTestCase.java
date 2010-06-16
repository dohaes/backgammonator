package backgammonator.test.tournament;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import backgammonator.impl.tournament.TournamentManager;
import backgammonator.lib.game.Player;
import backgammonator.lib.tournament.Tournament;
import backgammonator.lib.tournament.TournamentConfiguration;
import backgammonator.lib.tournament.TournamentException;
import backgammonator.lib.tournament.TournamentResult;
import backgammonator.lib.tournament.TournamentType;
import backgammonator.sample.player.interfacce.SamplePlayer;

/**
 * @author georgi.b.andreev
 */
public class TournamentTestCase extends TestCase {

	/**
	 * @throws TournamentException if an error occurs.
	 */
	public void testBattle() throws TournamentException {
		int PLAYERS = 10;
		List<Player> players = new LinkedList<Player>();
		for (int i = 0; i < PLAYERS; i++) {
			players.add(new SamplePlayer(i));
		}
		Tournament tournament = TournamentManager.newTournament(players);
		TournamentConfiguration config = new TournamentConfiguration(
				TournamentType.BATTLE);
		config.setLogMoves(false);
		config.setGamesCount(5);
		TournamentResult result = tournament.start(config);

		assertEquals(PLAYERS, result.getPlayersCount());
		assertEquals(result.getPlayer(0), result.getWinner());

		int prev = Integer.MAX_VALUE;
		for (int i = 0; i < PLAYERS; i++) {
			assertTrue(players.remove(result.getPlayer(i)));
			assertTrue(prev >= result.getPlayerPoints(i));
			prev = result.getPlayerPoints(i);
		}
	}

	/**
	 * @throws TournamentException if an error occurs.
	 */
	public void testEliminations() throws TournamentException {
		int PLAYERS = 16;
		List<Player> players = new LinkedList<Player>();
		for (int i = 0; i < PLAYERS; i++) {
			players.add(new SamplePlayer(i));
		}
		Tournament tournament = TournamentManager.newTournament(players);
		TournamentConfiguration config = new TournamentConfiguration(
				TournamentType.ELIMINATIONS);
		config.setLogMoves(false);
		config.setGamesCount(5);
		TournamentResult result = tournament.start(config);

		assertEquals(PLAYERS, result.getPlayersCount());
		assertEquals(result.getPlayer(0), result.getWinner());

		int prev = Integer.MAX_VALUE;
		for (int i = 0; i < PLAYERS; i++) {
			assertTrue(players.remove(result.getPlayer(i)));
			assertTrue(prev >= result.getPlayerPoints(i));
			prev = result.getPlayerPoints(i);
		}
	}

	/**
	 * @throws TournamentException if an error occurs.
	 */
	public void testGroups() throws TournamentException {
		int PLAYERS = 25;
		List<Player> players = new LinkedList<Player>();
		for (int i = 0; i < PLAYERS; i++) {
			players.add(new SamplePlayer(i));
		}
		Tournament tournament = TournamentManager.newTournament(players);
		TournamentConfiguration config = new TournamentConfiguration(
				TournamentType.GROUPS);
		config.setLogMoves(false);
		config.setGamesCount(5);
		config.setGroupsCount(8);
		TournamentResult result = tournament.start(config);

		assertEquals(PLAYERS, result.getPlayersCount());
		assertEquals(result.getPlayer(0), result.getWinner());

		int prev = Integer.MAX_VALUE;
		for (int i = 0; i < PLAYERS; i++) {
			assertTrue(players.remove(result.getPlayer(i)));
			assertTrue(prev >= result.getPlayerPoints(i));
			prev = result.getPlayerPoints(i);
		}
	}
}