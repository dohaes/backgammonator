package backgammonator.impl.webinterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * SourceUploadServlet represents web interface via which contestants can upload
 * their sources.
 */
public final class SourceUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 6501514760176956574L;
	private static final String REPOSITORY = "D:" + File.separator
			+ "repository";
	private static final String UPLOADS = "D:" + File.separator + "uploads";
	private static final File UPLOADS_DIR = new File(UPLOADS);

	// TODO use user name
	private volatile int index = 0;

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("test <br/> ");

		boolean validate = false;
		String expected = null;
		FileItem file = null;

		if (ServletFileUpload.isMultipartContent(request)) {

			// Parse the HTTP request...
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			// diskFileItemFactory.setSizeThreshold(40960); // in bytes - 40K

			diskFileItemFactory.setRepository(new File(REPOSITORY));

			ServletFileUpload servletFileUpload = new ServletFileUpload(
					diskFileItemFactory);
			servletFileUpload.setSizeMax(81920); // in bytes - 80K

			try {
				List fileItemsList = servletFileUpload.parseRequest(request);
				/* Process file items... */
				Iterator iter = fileItemsList.iterator();
				while (iter.hasNext()) {
					FileItem fileItem = (FileItem) iter.next();
					if (fileItem.isFormField()) {
						// The file item contains a simple name-value pair of a
						// form field
						out.print("name-value : " + fileItem.getFieldName()
								+ " -> " + fileItem.getString() + " <br/> ");
						String fieldName = fileItem.getFieldName();
						if (fieldName.equals("language")) {
							expected = fileItem.getString();
						} else if (fieldName.equals("validate")) {
							validate = true;
						}
					} else {
						// The file item contains an uploaded file
						file = fileItem;
					}
				}
			} catch (SizeLimitExceededException slee) {
				out.print("EXCEPTION 1 <br/> ");
				slee.printStackTrace(out);
				out.print("<br/> ");
				// The size of the HTTP request body exceeds the limit
			} catch (FileUploadException fue) {
				out.print("EXCEPTION 2 <br/> ");
				fue.printStackTrace(out);
				out.print("<br/> ");
			} catch (Throwable t) {
				out.print("EXCEPTION 3 <br/> ");
				t.printStackTrace(out);
				out.print("<br/> ");
			}
			
			
			// process the file
			FileOutputStream fos = null;
			InputStream is = null;
			String filename = file.getName();
	
			try {
				out.print("file : " + filename + " <br/> ");
				if (!verifay(filename, expected)) {
					out.println("ERROR invalid filename !"); // TODO
					out.flush();
					out.close();
					return;
				}
				
				if (!UPLOADS_DIR.exists()) {
					UPLOADS_DIR.mkdirs();
				}
				File userDir = new File(UPLOADS_DIR, "user" + (index++)); // TODO
				if (!userDir.exists()) {
					userDir.mkdirs();
				} else {
					if (!deleteContents(userDir)) {
						out.println("ERROR deleting !"); // TODO
						out.flush();
						out.close();
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
			} catch (Throwable t) {
				out.print("EXCEPTION 4 <br/> ");
				t.printStackTrace(out);
				out.print("<br/> ");
			} finally {
				if (fos != null) fos.close();
				if (is != null) is.close();
			}

		} else {
			// invalid mime
			out.print("in else <br/> ");
		}

		// response.setContentType("text/html");
		// PrintWriter out = response.getWriter();
		// out.println(" <br/> test <br/>");
		//		
		// Enumeration e = request.getAttributeNames();
		// out.print("Attributes: <br/> ");
		// while (e.hasMoreElements()) {
		// Object object = (Object) e.nextElement();
		// out.print(object + " <br/> ");
		// }
		// e = request.getParameterNames();
		// out.print("Parameters: <br/> ");
		// while (e.hasMoreElements()) {
		// Object object = (Object) e.nextElement();
		// out.print(object + " <br/> ");
		// }
		// e = request.getHeaderNames();
		// out.print("Headers: <br/> ");
		// while (e.hasMoreElements()) {
		// Object object = (Object) e.nextElement();
		// out.print(object + " <br/> ");
		// }
		// out.print("Session values: <br/> ");
		// String[] s = request.getSession().getValueNames();
		// for (int i = 0; i < s.length; i++) {
		// out.print(s[i] + " <br/> ");
		// }
		// /////////////////////////////////////////////////
		// String filename = request.getParameter("filename");
		// out.println(" <br/> === filename : " + filename);
		// String language = request.getParameter("language");
		// out.println(" <br/> === language : " + language);
		//		
		// /////////////////////////////////////////////
		//		
	}

	private boolean deleteContents(File file) {
		File[] files = file.listFiles();
		if (files == null || files.length == 0) return true;
		for (int i = 0; i < files.length; i++) {
			delete(files[i]);
		}
		return true;
	}

	private boolean delete(File file) {
		if (file.isFile()) return file.delete();
		File[] files = file.listFiles();
		if (files == null || files.length == 0) return file.delete();
		for (int i = 0; i < files.length; i++) {
			delete(files[i]);
		}
		return file.delete();
	}

	private boolean verifay(String filename, String expected) {// TODO use regex
		if (filename == null || filename.length() == 0) return false; // empty
																		// name
		int first = filename.indexOf('.');
		int last = filename.lastIndexOf('.');
		if (first != last || first == -1 || last == filename.length()) return false; // no
																						// or
																						// more
																						// than
																						// one
																						// dot
		String actual = filename.substring(last + 1);
		if (actual.length() == 0 || !actual.equals(expected)) return false;
		return true;
	}

}
