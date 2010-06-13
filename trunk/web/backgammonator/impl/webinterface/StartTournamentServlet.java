package backgammonator.impl.webinterface;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backgammonator.impl.protocol.SourceProcessor;
import backgammonator.impl.tournament.TournamentManager;
import backgammonator.lib.game.Player;
import backgammonator.lib.tournament.Tournament;
import backgammonator.lib.tournament.TournamentConfiguration;
import backgammonator.lib.tournament.TournamentType;

/**
 * @author georgi.b.andreev
 */
public final class StartTournamentServlet extends HttpServlet {

	private static final long serialVersionUID = 3569917850372148040L;

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String type = request.getParameter("type");
		TournamentConfiguration config = new TournamentConfiguration(
				TournamentType.valueOf(type));
		String logMoves = request.getParameter("logmoves");
		config.setLogMoves(logMoves != null);
		String plainRate = request.getParameter("plainrate");
		config.setPlainRate(plainRate != null);
		String groupsCount = request.getParameter("groupscount");
		try {
			int tmp = Integer.parseInt(groupsCount);
			config.setGroupsCount(tmp);
		} catch (NumberFormatException e) {
			// todo
		}
		String gamesCount = request.getParameter("gamescount");
		try {
			int tmp = Integer.parseInt(gamesCount);
			config.setGamesCount(tmp);
		} catch (NumberFormatException e) {
			// todo
		}
		String invalidGamePoints = request.getParameter("invalid");
		try {
			int tmp = Integer.parseInt(invalidGamePoints);
			config.setInvalidGamePoints(tmp);
		} catch (NumberFormatException e) {
			// todo
		}

		String[] players = request.getParameterValues("players");
		if (players == null) {
			players = new String[0];
		}
		List<Player> tmp = new ArrayList<Player>(players.length);
		for (int i = 0; i < players.length; i++) {
			Player p = createPlayer(players[i]);
			if (p == null) {
				// todo
			}
			tmp.add(p);
		}
		Tournament tournament = TournamentManager.newTournament(tmp);
		Player winner = tournament.start(config);
		//todo
	}

	private Player createPlayer(String user) {
		try {
			File dir = new File(new File("D:/uploads"), user);
			if (dir.isDirectory()) {
				String[] java = dir.list(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.endsWith(".java");
					}
				});
				File j = new File(dir, java[0]);
				return SourceProcessor.processFile(j.getAbsolutePath());
			}
		} catch (Exception e) {
		}
		return null;
	}
}