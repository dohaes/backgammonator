package backgammonator.impl.tournament;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import backgammonator.impl.protocol.SourceProcessor;
import backgammonator.lib.game.Player;
import backgammonator.lib.tournament.Tournament;
import backgammonator.lib.tournament.TournamentException;
import backgammonator.util.BackgammonatorConfig;
import backgammonator.util.Debug;

/**
 * @author georgi.b.andreev
 */
public class TournamentManager {

	/**
	 * Creates new tournament from the given players.
	 * 
	 * @param players the players that will take part in the tournament.
	 * @return the tournament that can be executed.
	 * @throws TournamentException if an error occurs.
	 */
	public static Tournament newTournament(List<Player> players)
			throws TournamentException {
		return new TournamentImpl(players);
	}

	/**
	 * Creates new tournament from the given players.
	 * 
	 * @param players the players that will take part in the tournament.
	 * @return the tournament that can be executed.
	 * @throws TournamentException if an error occurs.
	 */
	public static Tournament newTournament(String[] players)
			throws TournamentException {
		List<Player> tmp = new ArrayList<Player>(players.length);
		for (int i = 0; i < players.length; i++) {
			Player p = createPlayer(players[i]);
			if (p != null) {
				tmp.add(p);
			}
		}
		return new TournamentImpl(tmp);
	}

	private static Player createPlayer(String user) {
		try {
			File dir = new File(new File(BackgammonatorConfig.getProperty(
					"backgammonator.web.uploadDir", "uploads").replace('/',
					File.separatorChar)), user);
			if (dir.isDirectory()) {
				String[] java = dir.list(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.endsWith(".java");
					}
				});
				File j = new File(dir, java[0]);
				return SourceProcessor.processSource(j.getAbsolutePath());
			}
		} catch (Exception e) {
			Debug.getInstance().error("Error creating player.",
					Debug.WEB_INTERFACE, e);
		}
		return null;
	}
}