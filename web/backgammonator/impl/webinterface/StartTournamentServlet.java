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
	private final static String UPLOADS = "D:/uploads";
	private static final File UPLOADS_DIR = new File(UPLOADS);
	static {
		if (!UPLOADS_DIR.exists()) {
			UPLOADS_DIR.mkdir();
		}
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Start Tournament</title>"
				+ "</head><body><h2>Start Tournament</h2><br/>"
				+ "<form method='POST' action='starttournament'>"
				+ "<table><tr><td style='padding:10px;'>");
		String[] players = UPLOADS_DIR.list();
		for (int i = 0; i < players.length; i++) {
			out.println("<input type='checkbox' name='players' value='"
					+ players[i] + "'>" + players[i] + "</input><br/>");
		}
		out.println("</td><td style='padding:10px;'>");
		out.println("<input type='checkbox' name='logmoves'>"
				+ "Log moves</input><br/>");
		out.println("<input type='checkbox' name='plainrate'>"
				+ "Plain rate</input><br/>");
		out.println("<input type='text'"
				+ "name='groupscount' value='2'>Groups count</input><br/>");
		out.println("<input type='text'"
				+ "name='gamescount' value='3'>Games count</input><br/>");
		out.println("<input type='text'"
				+ "name='invalid' value='1'>Invalid game points</input><br/>");
		out.println("</td></tr></table>");
		out.println("Type : <input type='radio' name='type' "
				+ "value='BATTLE' checked>BATTLE</input>"
				+ "<input type='radio' name='type'"
				+ "value='ELIMINATIONS'>ELIMINATIONS</input>"
				+ "<input type='radio' name='type'"
				+ "value='GROUPS'>GROUPS</input><br/>");
		out.println("<br/><br/><input type=submit"
				+ " value=Start /></form></body></html>");
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Start Tournament</title>"
				+ "</head><body><h2>Start Tournament</h2><br/>");
		String[] players = request.getParameterValues("players");
		if (players == null) {
			players = new String[0];
		}
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
			out.println("error");
		}
		String gamesCount = request.getParameter("gamescount");
		try {
			int tmp = Integer.parseInt(gamesCount);
			config.setGamesCount(tmp);
		} catch (NumberFormatException e) {
			out.println("error");
		}
		String invalidGamePoints = request.getParameter("invalid");
		try {
			int tmp = Integer.parseInt(invalidGamePoints);
			config.setInvalidGamePoints(tmp);
		} catch (NumberFormatException e) {
			out.println("error");
		}
		List<Player> tmp = new ArrayList<Player>(players.length);
		for (int i = 0; i < players.length; i++) {
			tmp.add(createPlayer(players[i]));
		}
		Tournament tournament = TournamentManager.newTournament(tmp);
		Player winner = tournament.start(config);
		out.println("<h2>Winner is : " + winner.getName() + " </h2>");
		out.println("</body></html>");
	}

	private Player createPlayer(String user) {
		File dir = new File(UPLOADS_DIR, user);
		if (dir.isDirectory()) {
			String[] java = dir.list(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".java");
				}
			});
			File j = new File(dir, java[0]);
			return SourceProcessor.processFile(j.getAbsolutePath());
		}
		return null;
	}
}