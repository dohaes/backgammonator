package backgammonator.test.tournament;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import backgammonator.game.Player;
import backgammonator.impl.tournament.TournamentManager;
import backgammonator.sample.players.interfacce.SamplePlayer;
import backgammonator.tournament.Tournament;
import backgammonator.tournament.TournamentConfiguration;
import backgammonator.tournament.TournamentType;

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