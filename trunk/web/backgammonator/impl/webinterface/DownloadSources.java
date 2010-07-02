package backgammonator.impl.webinterface;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

/**
 * Used to download the uploaded sources.
 */
public final class DownloadSources extends HttpServlet {

	private static final long serialVersionUID = -3868381542797293264L;

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		ServletOutputStream output = res.getOutputStream();
		PrintWriter out = new PrintWriter(output);
		Util.printHeader(req, out, "Download Sources", Util.ADMIN);
		String user = req.getParameter("user");

		File userDir = new File(StartTournamentServlet.UPLOAD_DIR, user);
		if (userDir.exists() && userDir.isDirectory()) {
			String[] java = userDir.list(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".java") || name.endsWith(".c")
							|| name.endsWith(".cpp");
				}
			});
			if (java != null && java.length > 0) {
				File file = new File(userDir, java[0]);
				if (file.exists()) {
					downloadFile(res, output, file);
					return;
				}
			}
		}

		Util.redirect(out, Util.ADMIN_HOME, "No sources available!");
		Util.printFooter(out);
	}

	/**
	 * Prints the users that have uploaded sources.
	 * 
	 * @param req the http request.
	 * @param out the output stream.
	 * @throws IOException if an io exception occurs.
	 */
	public static void printSources(HttpServletRequest req, JspWriter out)
			throws IOException {
		String[] players = StartTournamentServlet.UPLOAD_DIR.list();
		if (players.length == 0) {
			out.println("No sources available.");
			return;
		}
		out.println("<table class='internal'>");
		for (int i = 0; i < players.length; i++) {
			out.println("<tr><td>" + players[i]
					+ "</td><td width='20px'>&nbsp;</td><td>"
					+ "<a href='downloadsources?user=" + players[i]
					+ "'> download </a></td></tr>");
		}
		out.println("</table>");
	}

	private void downloadFile(HttpServletResponse res,
			ServletOutputStream stream, File file) throws IOException {
		InputStream buf = null;
		try {
			stream = res.getOutputStream();
			res.setContentType("application/txt");
			res.setHeader("Content-Disposition", "attachment; filename="
					+ file.getName());
			res.setContentLength((int) file.length());

			FileInputStream input = new FileInputStream(file);
			buf = new BufferedInputStream(input);
			int readBytes = 0;

			while ((readBytes = buf.read()) != -1)
				stream.write(readBytes);
		} finally {
			if (stream != null) {
				stream.close();
			}
			if (buf != null) {
				buf.close();
			}
		}
	}
}