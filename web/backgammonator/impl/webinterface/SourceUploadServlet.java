package backgammonator.impl.webinterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import backgammonator.impl.common.Backgammonator;
import backgammonator.impl.protocol.ProcessingException;
import backgammonator.impl.protocol.SourceProcessor;
import backgammonator.lib.db.Account;
import backgammonator.util.BackgammonatorConfig;

/**
 * SourceUploadServlet represents web interface via which contestants can upload
 * their sources.
 */
public final class SourceUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 6501514760176956574L;

	private static final int uploadMaxSize = BackgammonatorConfig.getProperty(
			"backgammonator.web.uploadMaxSize", 40960);
	private static final String REPOSITORY = BackgammonatorConfig.getProperty(
			"backgammonator.web.repositoryDir", "repository").replace('/',
			File.separatorChar);
	private static final String UPLOADS = BackgammonatorConfig.getProperty(
			"backgammonator.web.uploadDir", "uploads").replace('/',
			File.separatorChar);
	private static final File UPLOADS_DIR = new File(UPLOADS);

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings( { "unchecked" })
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		PrintWriter out = res.getWriter();
		Util.printHeader(req, out, "Source Upload", Util.USER);

		Account user = Util.getCurrentUser(req);
		if (!Util.checkCredentials(out, user, Util.USER)) {
			return;
		}
		boolean canBlockTournament = Backgammonator.blockToutnament();
		try {
			if (!canBlockTournament) {
				Util.redirect(out, Util.USER_HOME, "Cannot upload file!"
						+ "<br />A tournament has just started!");
				return;
			}
			boolean validate = false;
			boolean java = false;
			FileItem file = null;

			if (!ServletFileUpload.isMultipartContent(req)) {
				// invalid content type
				Util.redirect(out, Util.USER_HOME, "Invalid content type!");
				return;
			}
			// Parse the HTTP request...
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			// diskFileItemFactory.setSizeThreshold(uploadMaxSize); in bytes -
			// 40K

			diskFileItemFactory.setRepository(new File(REPOSITORY));

			ServletFileUpload servletFileUpload = new ServletFileUpload(
					diskFileItemFactory);
			servletFileUpload.setSizeMax(uploadMaxSize); // in bytes - 40K
			List fileItemsList = servletFileUpload.parseRequest(req);
			// Process file items...
			Iterator iter = fileItemsList.iterator();
			while (iter.hasNext()) {
				FileItem fileItem = (FileItem) iter.next();
				if (fileItem.isFormField()) {
					// The file item contains a simple name-value pair of a
					// form field
					String fieldName = fileItem.getFieldName();
					if (fieldName.equals("language")) {
						if ("java".equals(fileItem.getString())) java = true;
					} else if (fieldName.equals("validate")) {
						validate = true;
					}
				} else {
					// The file item contains an uploaded file
					file = fileItem;
				}
			}

			// process the file
			if (file == null) {
				Util.redirect(out, Util.USER_HOME,
						"No file specified for upload!");
				return;
			}
			FileOutputStream fos = null;
			InputStream is = null;
			String filename = file.getName();
			String validationMessage = null;
			boolean hasError = false;

			File userDir = new File(UPLOADS_DIR, user.getUsername());

			try {
				if (!verify(filename, java)) {
					Util.redirect(out, Util.USER_HOME, "Invalid file name!");
					return;
				}

				if (!UPLOADS_DIR.exists()) {
					UPLOADS_DIR.mkdirs();
				}
				if (!userDir.exists()) {
					userDir.mkdirs();
				} else {
					if (!deleteContents(userDir)) {
						Util.redirect(out, Util.USER_HOME,
								"Cannot delete previously uploaded sources!");
						return;
					}
				}

				File uploaded = new File(userDir, filename);

				fos = new FileOutputStream(uploaded);
				is = file.getInputStream();
				byte[] buff = new byte[24];
				int len = 0;
				while ((len = is.read(buff, 0, 24)) != -1) {
					fos.write(buff, 0, len);
				}
				fos.flush();

				try {
					SourceProcessor.processSource(uploaded.getAbsolutePath());
				} catch (ProcessingException pe) {
					hasError = true;
					validationMessage = pe.getMessage();
				}

				if (!hasError && validate) {
					validationMessage = SourceProcessor.validateSource(uploaded
							.getAbsolutePath());
				}
			} finally {
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
			}
			
			if (hasError) {
				ReportsServlet.deleteDirectory(userDir);
			}

			Util.redirect(out, Util.USER_HOME,
					(hasError ? "Upload unsuccessful." : "Upload successful.")
							+ (validationMessage != null ? "<br />"
									+ validationMessage : ""));
		} catch (Exception e) {
			Util.redirect(out, Util.USER_HOME, e.getMessage());

		} finally {
			if (canBlockTournament) {
				Backgammonator.unblockTournament();
			}
		}
		Util.printFooter(out);
	}

	private boolean deleteContents(File file) {
		File[] files = file.listFiles();
		if (files == null || files.length == 0) {
			return true;
		}
		for (int i = 0; i < files.length; i++) {
			delete(files[i]);
		}
		return true;
	}

	private boolean delete(File file) {
		if (file.isFile()) {
			return file.delete();
		}
		File[] files = file.listFiles();
		if (files == null || files.length == 0) {
			return file.delete();
		}
		for (int i = 0; i < files.length; i++) {
			delete(files[i]);
		}
		return file.delete();
	}

	private boolean verify(String filename, boolean java) {
		// TODO use regex
		if (filename == null || filename.length() == 0) {
			return false; // empty name
		}
		int first = filename.indexOf('.');
		int last = filename.lastIndexOf('.');
		// no or more than one dot
		if (first != last || first == -1 || last == filename.length()) {
			return false;
		}
		String actual = filename.substring(last + 1);
		if (actual.length() == 0) return false;
		if (java) {
			if (actual.equals("java")) return true;
		} else {
			if (actual.equals("c") || actual.equals("cpp")) return true;
		}
		return false;
	}
}
