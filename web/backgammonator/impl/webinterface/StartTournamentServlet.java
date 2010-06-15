package backgammonator.impl.webinterface;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backgammonator.impl.protocol.SourceProcessor;
import backgammonator.impl.tournament.TournamentManager;
import backgammonator.lib.game.Player;
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
	private static final String URL = "StartTournament.jsp";

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		long start = new Date().getTime();

		try {
			String type = request.getParameter("type");
			TournamentConfiguration config = new TournamentConfiguration(
					TournamentType.valueOf(type));
			String logMoves = request.getParameter("logmoves");
			config.setLogMoves(logMoves != null);
			String groupsCount = request.getParameter("groupscount");
			try {
				int tmp = Integer.parseInt(groupsCount);
				config.setGroupsCount(tmp);
			} catch (Exception e) {
				Debug.getInstance().error("Error creating tournament.",
						Debug.WEB_INTERFACE, e);
				redirect(out, URL, "Error ! <br/>Error reading Groups Count. "
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
				redirect(out, URL, "Error ! <br/>Error reading Games Count. "
						+ e.getMessage());
				return;
			}

			String[] players = request.getParameterValues("players");
			if (players == null) {
				players = new String[0];
			}
			List<Player> tmp = new ArrayList<Player>(players.length);
			for (int i = 0; i < players.length; i++) {
				Player p = createPlayer(players[i]);
				if (p == null) {
					redirect(out, URL, "Error ! <br/>Error creating player "
							+ players[i] + ".");
					return;
				}
				tmp.add(p);
			}
			long compile = new Date().getTime() - start;
			Tournament tournament = TournamentManager.newTournament(tmp);
			TournamentResult result = tournament.start(config);
			StringBuilder message = new StringBuilder("Success ! <br/>");
			long end = new Date().getTime() - start;
			for (int i = 0; i < result.getPlayersCount(); i++) {
				message.append((i + 1)).append(". ").append(
						result.getPlayer(i).getName()).append(" with ").append(
						result.getPlayerPoints(i)).append(" points.<br/>");
			}
			message.append(compile + " " + end + "<br/>");
			redirect(out, URL, message.toString());
		} catch (Exception e) {
			Debug.getInstance().error("Error creating tournament.",
					Debug.WEB_INTERFACE, e);
			redirect(out, URL, "Error ! <br/>Error creating tournament. "
					+ e.getMessage());
		}
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

	private static void redirect(PrintWriter out, String link, String message) {
		out.print("<body><form name='hiddenForm' action='" + link);
		out.print("' method='POST'><input type='hidden' name='result' value='");
		out.print(message
				+ "' ></input> </form> </body><script language='Javascript'>"
				+ "document.hiddenForm.submit();</script>");
	}
}