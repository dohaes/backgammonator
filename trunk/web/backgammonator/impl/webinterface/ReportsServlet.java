package backgammonator.impl.webinterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import backgammonator.util.BackgammonatorConfig;
import backgammonator.util.Debug;

/**
 * Represents the reports.
 */
public final class ReportsServlet extends HttpServlet {

	private static final long serialVersionUID = 7345160614448725636L;

	private final static File REPORT_DIR = new File(BackgammonatorConfig
			.getProperty("backgammonator.logger.outputDir", "reports"));

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		PrintWriter out = res.getWriter();
		Util.printHeader(req, out, "Delete Reports", Util.ADMIN);
		String tournamentId = req.getParameter("tid");
		File tournament = new File(REPORT_DIR, tournamentId);
		if (tournament.exists()) {
			int len = (int) (tournament.length());
			FileInputStream fis = new FileInputStream(tournament);
			byte buf[] = new byte[len];
			fis.read(buf);
			fis.close();
			String resultFileContent = new String(buf, Charset.defaultCharset());
			Pattern p = Pattern
					.compile("<a href=\"([^/\"]*)/index.html\" target=\"_blank\">View</a>");
			Matcher m = p.matcher(resultFileContent);
			Debug.getInstance().info("Matches:", Debug.WEB_INTERFACE);
			while (m.find()) {
				String path = m.group(1);
				Debug.getInstance().info(path, Debug.WEB_INTERFACE);
				File f = new File(REPORT_DIR, path);
				deleteDirectory(f);
			}

			if (!tournament.delete()) {
				Util.redirect(out, Util.MANAGE_REPORTS,
						"The log could not be deleted.");
			} else {
				Util.redirect(out, Util.MANAGE_REPORTS, "Delete successfull.");
			}
		} else {
			Util.redirect(out, Util.MANAGE_REPORTS,
					"The log does not exist and it cannot be deleted.");
		}
		Util.printFooter(out);
	}

	/**
	 * Print the available reports.
	 * 
	 * @param req the http request.
	 * @param out the output stream.
	 * @param manage if there should be management of the reports available.
	 * @throws IOException if IO an error occurs.
	 */
	public static void printReports(HttpServletRequest req, JspWriter out,
			boolean manage) throws IOException {
		if (!REPORT_DIR.exists()) {
			REPORT_DIR.mkdirs();
		}
		String[] tournaments = REPORT_DIR.list(new FilenameFilter() {
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

	/**
	 * Deletes the directory and all subdirectories.
	 * 
	 * @param path the directory to delete.
	 * @return true if the directory is deleted successfully.
	 */
	public static boolean deleteDirectory(File path) {
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				deleteDirectory(files[i]);
			} else {
				files[i].delete();
			}
		}
		return path.delete();
	}
}