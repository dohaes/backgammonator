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
import javax.servlet.jsp.JspWriter;

import backgammonator.impl.protocol.SourceProcessor;
import backgammonator.impl.tournament.TournamentManager;
import backgammonator.lib.game.Player;
import backgammonator.lib.tournament.Tournament;
import backgammonator.lib.tournament.TournamentConfiguration;
import backgammonator.lib.tournament.TournamentResult;
import backgammonator.lib.tournament.TournamentType;
import backgammonator.util.BackgammonatorConfig;

/**
 * Servlet to start a toutnament.
 */
public final class StartTournamentServlet extends HttpServlet {

	private static final long serialVersionUID = 3569917850372148040L;
	static final File UPLOAD_DIR = new File(BackgammonatorConfig.getProperty(
			"backgammonator.web.uploadDir", "uploads").replace('/',
			File.separatorChar));

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		PrintWriter out = res.getWriter();
		if (!Util.checkCredentials(out, Util.getCurrentUser(req), Util.ADMIN)) {
			return;
		}
		try {
			String type = req.getParameter("type");
			TournamentConfiguration config = new TournamentConfiguration(
					TournamentType.valueOf(type));
			String logMoves = req.getParameter("logmoves");
			config.setLogTournament(logMoves != null);
			String groupsCount = req.getParameter("groupscount");
			try {
				int tmp = Integer.parseInt(groupsCount);
				config.setGroupsCount(tmp);
			} catch (Exception e) {
				Util.redirect(out, Util.START_TOURNAMENT,
						"Error reading Groups Count! " + e.getMessage());
				return;
			}
			String gamesCount = req.getParameter("gamescount");
			try {
				int tmp = Integer.parseInt(gamesCount);
				config.setGamesCount(tmp);
			} catch (Exception e) {
				Util.redirect(out, Util.START_TOURNAMENT,
						"Error reading Games Count! " + e.getMessage());
				return;
			}

			String[] players = req.getParameterValues("players");
			if (players == null) {
				players = new String[0];
			}

			List<Player> tmp = new ArrayList<Player>();
			List<String> disqualified = new ArrayList<String>();
			for (int i = 0; i < players.length; i++) {
				Player p = createPlayer(players[i]);
				if (p != null) {
					tmp.add(p);
				} else {
					disqualified.add(players[i]);
				}
			}
			if (disqualified.size() > 0) {
				if (tmp.size() == 0) {
					Util.redirect(out, Util.START_TOURNAMENT,
							"All players have been disqualified!");
				}
				if (tmp.size() == 1) {
					Util.redirect(out, Util.START_TOURNAMENT, "Winner is "
							+ tmp.get(0).getName()
							+ ". All other have been disqualified!");
				}
			}
			Tournament tournament = TournamentManager.newTournament(tmp);
			TournamentResult result = tournament.start(config);
			StringBuilder message = new StringBuilder(
					"Tournament succesfully executed.");
			for (int i = 0; i < result.getPlayersCount(); i++) {
				message.append("<br />").append((i + 1)).append(". ").append(
						result.getPlayer(i).getName()).append(" with ").append(
						result.getPlayerPoints(i)).append(" points.");
			}
			for (int i = 0; i < disqualified.size(); i++) {
				message.append("<br />").append(
						(i + 1 + result.getPlayersCount())).append(". ")
						.append(disqualified.get(i)).append(
								" was disqualified.");
			}
			Util.redirect(out, Util.START_TOURNAMENT, message.toString());
		} catch (Exception e) {
			Util.redirect(out, Util.START_TOURNAMENT, e.getMessage());
		}
	}

	private static Player createPlayer(String user) {
		try {
			File userDir = new File(UPLOAD_DIR, user);
			if (userDir.isDirectory()) {
				String[] java = userDir.list(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.endsWith(".java") || name.endsWith(".c")
								|| name.endsWith(".cpp");
					}
				});
				File j = new File(userDir, java[0]);
				return SourceProcessor.processSource(j.getAbsolutePath());
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Prints all players in an option form.
	 * 
	 * @param out the output stream.
	 * @throws IOException if an IO error occurs.
	 */
	public static void printPlayers(JspWriter out) throws IOException {
		if (!UPLOAD_DIR.exists()) {
			UPLOAD_DIR.mkdirs();
		}
		String[] players = UPLOAD_DIR.list();
		for (int i = 0; i < players.length; i++) {
			out.println("<option value='" + players[i] + "'>" + players[i]
					+ "</option>");
		}
	}
}