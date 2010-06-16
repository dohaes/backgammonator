package backgammonator.impl.webinterface;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author georgi.b.andreev
 */
public final class ReportsServlet extends HttpServlet {

	private static final long serialVersionUID = 7345160614448725636L;
	private static final String URL = "ManageReports.jsp";

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();

		@SuppressWarnings("unused")
		String tournamentId = (String) request.getAttribute("tid");

		// TODO delete specified tournament

		StartTournamentServlet.redirect(out, URL, "Delete successfull!");
	}
}