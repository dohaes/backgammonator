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

import backgammonator.lib.db.Account;

/**
 * Used to download the uploaded sources.
 */
public final class DownloadPlayer extends HttpServlet {

	private static final long serialVersionUID = 2194257132301721616L;

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		Account user = Util.getCurrentUser(req);

		if (user == null || user.isAdmin()) {
			PrintWriter out = res.getWriter();
			Util.printHeader(req, out, "Download Source", Util.USER);
			String message = "You have tried to access an invalid page.";
			if (user == null) {
				Util.redirect(out, Util.LOGIN_HOME, message);
			} else if (!user.isAdmin()) {
				Util.redirect(out, Util.USER_HOME, message);
			} else {
				Util.redirect(out, Util.ADMIN_HOME, message);
			}
			Util.redirect(out, Util.USER_HOME, "No source available!");
			return;
		}

		File userDir = new File(StartTournamentServlet.UPLOAD_DIR, user
				.getUsername());
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
					ServletOutputStream output = res.getOutputStream();
					downloadFile(res, output, file);
					return;
				}
			}
		}

		PrintWriter out = res.getWriter();
		Util.printHeader(req, out, "Download Source", Util.USER);
		Util.redirect(out, Util.USER_HOME, "No source available!");
		Util.printFooter(out);
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