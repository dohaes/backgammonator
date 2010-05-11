package backgammonator.test.tournament;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import backgammonator.impl.tournament.TournamentManager;
import backgammonator.lib.game.Player;
import backgammonator.lib.tournament.Tournament;
import backgammonator.lib.tournament.TournamentConfiguration;
import backgammonator.lib.tournament.TournamentType;
import backgammonator.sample.players.interfacce.SamplePlayer;

public class TournamentTestCase extends TestCase {
	public void testBattle() {
		List<Player> players = new LinkedList<Player>();
		for (int i = 0; i < 10; i++) {
			players.add(new SamplePlayer(i));
		}
		Tournament tournament = TournamentManager.newTournament(players);
		TournamentConfiguration config = new TournamentConfiguration(
				TournamentType.BATTLE);
		Player winner = tournament.start(config);
		System.out.println(winner.getName());
	}

	public void testEliminations() {
		List<Player> players = new LinkedList<Player>();
		for (int i = 0; i < 10; i++) {
			players.add(new SamplePlayer(i));
		}
		Tournament tournament = TournamentManager.newTournament(players);
		TournamentConfiguration config = new TournamentConfiguration(
				TournamentType.ELIMINATIONS);
		Player winner = tournament.start(config);
		System.out.println(winner.getName());
	}

	public void testGroups() {
		List<Player> players = new LinkedList<Player>();
		for (int i = 0; i < 10; i++) {
			players.add(new SamplePlayer(i));
		}
		Tournament tournament = TournamentManager.newTournament(players);
		TournamentConfiguration config = new TournamentConfiguration(
				TournamentType.GROUPS);
		Player winner = tournament.start(config);
		System.out.println(winner.getName());
	}
}