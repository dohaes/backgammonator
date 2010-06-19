package backgammonator.impl.webinterface;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import backgammonator.impl.tournament.TournamentManager;
import backgammonator.lib.tournament.Tournament;
import backgammonator.lib.tournament.TournamentConfiguration;
import backgammonator.lib.tournament.TournamentResult;
import backgammonator.lib.tournament.TournamentType;
import backgammonator.util.BackgammonatorConfig;
import backgammonator.util.Debug;

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
		PrintWriter out = response.getWriter();
		if (!Util.checkCredentials(out, Util.getCurrentUser(request),
				Util.ADMIN)) {
			return;
		}
		try {
			String type = request.getParameter("type");
			TournamentConfiguration config = new TournamentConfiguration(
					TournamentType.valueOf(type));
			String logMoves = request.getParameter("logmoves");
			config.setLogTournament(logMoves != null);
			String groupsCount = request.getParameter("groupscount");
			try {
				int tmp = Integer.parseInt(groupsCount);
				config.setGroupsCount(tmp);
			} catch (Exception e) {
				Debug.getInstance().error("Error creating tournament.",
						Debug.WEB_INTERFACE, e);
				Util.redirect(out, Util.START_TOURNAMENT,
						"Error!<br/>Error reading Groups Count. "
								+ e.getMessage());
				return;
			}
			String gamesCount = request.getParameter("gamescount");
			try {
				int tmp = Integer.parseInt(gamesCount);
				config.setGamesCount(tmp);
			} catch (Exception e) {
				Debug.getInstance().error("Error creating tournament.",
						Debug.WEB_INTERFACE, e);
				Util.redirect(out, Util.START_TOURNAMENT,
						"Error!<br/>Error reading Games Count. "
								+ e.getMessage());
				return;
			}

			String[] players = request.getParameterValues("players");
			if (players == null) {
				players = new String[0];
			}
			Tournament tournament = TournamentManager.newTournament(players);
			TournamentResult result = tournament.start(config);
			StringBuilder message = new StringBuilder("Success ! <br/>");
			for (int i = 0; i < result.getPlayersCount(); i++) {
				message.append((i + 1)).append(". ").append(
						result.getPlayer(i).getName()).append(" with ").append(
						result.getPlayerPoints(i)).append(" points.<br/>");
			}
			Util.redirect(out, Util.START_TOURNAMENT, message.toString());
		} catch (Exception e) {
			Debug.getInstance().error("Error creating tournament.",
					Debug.WEB_INTERFACE, e);
			Util.redirect(out, Util.START_TOURNAMENT,
					"Error!<br/>Error creating tournament. " + e.getMessage());
		}
	}

	/**
	 * Prints all players in an option form.
	 * 
	 * @param out the output stream.
	 * @throws IOException if an IO error occurs.
	 */
	public static void printPlayers(JspWriter out) throws IOException {
		File dir = new File(BackgammonatorConfig.getProperty(
				"backgammonator.web.uploadDir", "uploads").replace('/',
				File.separatorChar));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String[] players = dir.list();
		for (int i = 0; i < players.length; i++) {
			out.println("<option value='" + players[i] + "'>" + players[i]
					+ "</option>");
		}
	}
}