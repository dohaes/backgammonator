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
 * @author georgi.b.andreev
 */
public final class DownloadPlayer extends HttpServlet {

	private static final long serialVersionUID = 2194257132301721616L;

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		ServletOutputStream output = res.getOutputStream();
		PrintWriter out = new PrintWriter(output);

		Account user = Util.getCurrentUser(req);
		if (!Util.checkCredentials(out, user, Util.USER)) {
			return;
		}
		File userDir = new File(StartTournamentServlet.UPLOAD_DIR, user
				.getUsername());
		if (userDir.exists() && userDir.isDirectory()) {
			String[] java = userDir.list(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".java") || name.endsWith(".c");
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

		Util.redirect(out, Util.USER_HOME, "No source available!");
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