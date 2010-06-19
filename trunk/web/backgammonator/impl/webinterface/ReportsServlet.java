package backgammonator.impl.webinterface;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import backgammonator.util.BackgammonatorConfig;

/**
 * @author georgi.b.andreev
 */
public final class ReportsServlet extends HttpServlet {

	private static final long serialVersionUID = 7345160614448725636L;

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();
		if (!Util.checkCredentials(out, Util.getCurrentUser(request),
				Util.ADMIN)) {
			return;
		}
		// String tournamentId = (String) request.getAttribute("tid");
		Util.redirect(out, Util.MANAGE_REPORTS, "Delete successfull!");
	}

	/**
	 * Print the available reports.
	 * 
	 * @param request the http request.
	 * @param out the output stream.
	 * @param manage if there should be management of the reports available.
	 * @throws IOException if IO an error occurs.
	 */
	public static void printReports(HttpServletRequest request, JspWriter out,
			boolean manage) throws IOException {
		File dir = new File(BackgammonatorConfig.getProperty(
				"backgammonator.logger.outputDir", "reports"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String[] tournaments = dir.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".html");
			}
		});
		if (tournaments.length == 0) {
			out.println("No reports available.");
		}
		String url = BackgammonatorConfig.getProperty(
				"backgammonator.web.reports", "reports")
				.replaceAll("\\\\", "/");
		out.println("<table class='internal'>");
		for (int i = 0; i < tournaments.length; i++) {
			out.println("<tr><td> <a href='" + url + "/" + tournaments[i]
					+ "' target='_blank'> " + tournaments[i] + " </a> </td>");
			if (manage) {
				out.println("<td width='20px'>&nbsp;</td><td>"
						+ "<a href='reports?tid=" + tournaments[i]
						+ "'> delete </a> </td>");
			}
			out.println("</tr>");
		}
		out.println("</table>");
	}
}