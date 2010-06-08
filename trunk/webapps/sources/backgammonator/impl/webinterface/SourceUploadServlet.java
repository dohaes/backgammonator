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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import backgammonator.impl.protocol.SourceProcessor;

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

	// TODO use user name from the account
	private volatile int index = 0;

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)throws IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("1");
		boolean validate = false;
		String expected = null;
		FileItem file = null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			// invalid content type
			out.print("2");
			afterUpload(out, "Invalit content type!");
		}
		out.print("3");
		// Parse the HTTP request...
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		// diskFileItemFactory.setSizeThreshold(40960); // in bytes - 40K

		diskFileItemFactory.setRepository(new File(REPOSITORY));

		ServletFileUpload servletFileUpload = new ServletFileUpload(
				diskFileItemFactory);
		servletFileUpload.setSizeMax(81920); // in bytes - 80K
		out.print("4");
		try {
			List fileItemsList = servletFileUpload.parseRequest(request);
			// Process file items... 
			Iterator iter = fileItemsList.iterator();
			while (iter.hasNext()) {
				FileItem fileItem = (FileItem) iter.next();
				if (fileItem.isFormField()) {
					out.print("5");
					// The file item contains a simple name-value pair of a
					// form field
					String fieldName = fileItem.getFieldName();
					if (fieldName.equals("language")) {
						expected = fileItem.getString();
					} else if (fieldName.equals("validate")) {
						validate = true;
					}
				} else {
					// The file item contains an uploaded file
					file = fileItem;
					out.print("6");
				}
			}
		} catch (SizeLimitExceededException slee) {
			// The size of the HTTP request body exceeds the limit
			out.print("7");
			afterUpload(out, slee.getMessage());
		} catch (FileUploadException fue) {
			out.print("8");
			afterUpload(out, fue.getMessage());
		} catch (Throwable t) {
			out.print("9");
			afterUpload(out, t.getMessage());
		}
		out.print("10");
		
		// process the file
		if (file == null) {
			out.print("11");
			afterUpload(out, "No file specified for upload!");
		}
		FileOutputStream fos = null;
		InputStream is = null;
		String filename = file.getName();
		String validationMessage = null;

		try {
			if (!verifay(filename, expected)) {
				out.print("12");
				out.print("Invalid file name!");
				afterUpload(out, "Invalid file name!");
				
			}
			
			if (!UPLOADS_DIR.exists()) {
				UPLOADS_DIR.mkdirs();
			}
			File userDir = new File(UPLOADS_DIR, "user" + (index++)); // TODO
			if (!userDir.exists()) {
				userDir.mkdirs();
			} else {
				if (!deleteContents(userDir)) {
					out.print("Cannot delete previously uploaded sources!");
					afterUpload(out, "Cannot delete previously uploaded sources!");
				}
			}

			File uploaded = new File(userDir, filename);

			out.print(uploaded.getAbsolutePath());
			
			fos = new FileOutputStream(uploaded);
			is = file.getInputStream();
			byte[] buff = new byte[24];
			int len = 0;
			while ((len = is.read(buff, 0, 24)) != -1) {
				fos.write(buff, 0, len);
			}
			fos.flush();
			
			if (validate) {
				validationMessage = SourceProcessor.validateSource(uploaded.getAbsolutePath());
			}
			out.println("==== valid. : " + validationMessage + "<br/>");
		} catch (Throwable t) {
			afterUpload(out, t.getMessage());
		} finally {
			try {
				if (fos != null) fos.close();
				if (is != null) is.close();
			} catch (IOException ioe) {
				afterUpload(out, ioe.getMessage());
			}
		}
	
		afterUpload(out, "Upload successful!<br>" + (validate ? "<br/>" + validationMessage + "<br/>" : ""));
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
		// no or more than one dot
		if (first != last || first == -1 || last == filename.length()) return false; 
		String actual = filename.substring(last + 1);
		if (actual.length() == 0 || !actual.equals(expected)) return false;
		return true;
	}
	
	private void afterUpload(PrintWriter out, String message) {
		out.print("<form name=\"hiddenForm\" action=\"SourceUpload.jsp\" method=\"POST\">"); 
		out.print("<input type=\"hidden\" name=\"result\" value=\"" + message + "\" > </form> ");
		out.print("<script language=\"Javascript\">");
		out.print("document.hiddenForm.submit();");
		out.print("</script>");
	}
}
