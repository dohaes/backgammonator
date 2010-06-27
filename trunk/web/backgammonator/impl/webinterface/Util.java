package backgammonator.impl.webinterface;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import backgammonator.impl.common.Backgammonator;
import backgammonator.lib.db.Account;
import backgammonator.util.StreamParser;

/**
 * Holds utility methods.
 */
public class Util {

	/**
	 * Home, login and register pages.
	 */
	public static final int HOME = 0;

	/**
	 * Administrator pages.
	 */
	public static final int ADMIN = 1;

	/**
	 * User pages.
	 */
	public static final int USER = 2;

	static final String ADMIN_HOME = "StartTournament.jsp";
	static final String USER_HOME = "SourceUpload.jsp";
	static final String LOGIN_HOME = "index.jsp";
	static final String REGISTER = "Register.jsp";
	static final String MANAGE_REPORTS = "ManageReports.jsp";
	static final String START_TOURNAMENT = "StartTournament.jsp";
	static final String SOURCE_UPLOAD = "SourceUpload.jsp";

	/**
	 * Prints the header of a regular jsp.
	 * 
	 * @param request the Http request.
	 * @param out the output stream.
	 * @param title the title of the pages.
	 * @param type the type of the page.
	 */
	public static void printHeader(HttpServletRequest request, JspWriter out,
			String title, int type) {
		printHeader(request, new PrintWriter(out), title, type, false);
	}

	/**
	 * Prints the header of a regular jsp.
	 * 
	 * @param request the Http request.
	 * @param out the output stream.
	 * @param title the title of the pages.
	 * @param type the type of the page.
	 */
	public static void printHeader(HttpServletRequest request, PrintWriter out,
			String title, int type) {
		printHeader(request, out, title, type, false);
	}

	/**
	 * Prints the header of a regular jsp.
	 * 
	 * @param request the Http request.
	 * @param out the output stream.
	 * @param title the title of the pages.
	 * @param type the type of the page.
	 * @param checkUpload shows if a check should be performed if the source
	 *            upload is blocked or nor.
	 */
	public static void printHeader(HttpServletRequest request, PrintWriter out,
			String title, int type, boolean checkUpload) {
		Account user = getCurrentUser(request);
		if (!checkCredentials(out, user, type)) {
			return;
		}
		out.print("<html><head><link href='style.css' rel='stylesheet'"
				+ "type='text/css'><title>Backgammonator - " + title
				+ "</title></head><body><table class='main'><tr>"
				+ "<td colspan='5'><h1 class='header'>Backgammonator - "
				+ title + "</h1></td></tr><tr><td width='40px'>&nbsp;</td>"
				+ "<td width='200px' style='vertical" + "-align:" + " top;'>");

		if (user != null && user.isAdmin()) {
			out.print("<a href='StartTournament.jsp'>Start Tournament</a>"
					+ "<br /><a href='ManageReports.jsp'>Manage Reports</a>"
					+ "<br /><a href='ManageRegistrations.jsp'>"
					+ "Manage Registrations</a>");
		}
		if (user != null && !user.isAdmin()) {
			out.print("<a href='SourceUpload.jsp'>Source Upload</a><br />"
					+ "<a href='ViewReports.jsp'>View Reports</a><br />"
					+ "<a href='download'>Download Source</a>");
		}

		if (user == null) {
			out.print("<a href='/'>Login</a><br />"
					+ "<a href='Register.jsp'>Register</a>");
		}
		out.print("<br /><br /><a href='Tutorial.jsp'>Tutorial</a><br />"
				+ "<a href='Protocol.jsp'>Protocol</a><br />"
				+ "<a href='res/backgammonatorLibrary.jar'>Library Jar</a>");
		if (user != null) {
			out.print("<br /><br />" + user.getUsername()
					+ "<br /><a href='logout'>logout</a><br />");

		}
		out.println("</td><td width='20px'>&nbsp;</td>"
				+ "<td style='vertical-align: top;'>");
		String message = request.getParameter("result");
		if (message != null) {
			out.println("<b class='message'>" + message + "</b><br/><br/>");
		} else if (checkUpload) {
			if (Backgammonator.isUploadBlocked()) {
				out.println("<b class='message'>"
						+ "Cannot upload source files "
						+ "because a tournament is executing!"
						+ "</b><br/><br/>");
			}
		}
	}

	/**
	 * Prints the footer of a regular jsp.
	 * 
	 * @param out the output stream.
	 */
	public static void printFooter(JspWriter out) {
		printFooter(new PrintWriter(out));
	}

	/**
	 * Prints the footer of a regular jsp.
	 * 
	 * @param out the output stream.
	 */
	public static void printFooter(PrintWriter out) {
		out.print("</td><td width='40px'>&nbsp;</td></tr>"
				+ "<tr height='50px'><td colspan='5'>&nbsp;</td>"
				+ "</tr></table></body></html>");
	}

	/**
	 * Redirects the current page to the specified url and adds parameter
	 * result=message.
	 * 
	 * @param out the output stream.
	 * @param link the url to which the page is redirected.
	 * @param message the value of the result parameter.
	 */
	static void redirect(PrintWriter out, String link, String message) {
		out.print("<form name='hiddenForm' action='" + link);
		out.print("' method='POST'><input type='hidden' name='result' value='");
		out.print(message + "' ></input> </form> ");
		printFooter(out);
		out.print("<script language='Javascript'>document."
				+ "hiddenForm.submit();</script>");
	}

	/**
	 * Encodes the given string using md5.
	 */
	static String MD5(String pass) {
		try {
			MessageDigest m = null;
			m = MessageDigest.getInstance("MD5");
			byte[] data = pass.getBytes();
			m.update(data, 0, data.length);
			BigInteger i = new BigInteger(1, m.digest());
			return String.format("%1$032X", i);
		} catch (NoSuchAlgorithmException e) {
			return pass;
		}
	}

	/**
	 * Retrieves the current session user.
	 */
	static Account getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object account = session.getValue("user");
		if (!(account instanceof Account)) {
			return null;
		}
		return (Account) account;
	}

	/**
	 * Checks the credentials of the given user.
	 */
	static boolean checkCredentials(PrintWriter out, Account user, int type) {
		if ((user == null && (type == USER || type == ADMIN))
				|| (user != null && user.isAdmin() && type == USER)
				|| (user != null && !user.isAdmin() && type == ADMIN)) {
			String message = "You have tried to access an invalid page.";
			if (user == null) {
				redirect(out, LOGIN_HOME, message);
				return false;
			}
			if (!user.isAdmin()) {
				redirect(out, USER_HOME, message);
				return false;
			}
			redirect(out, ADMIN_HOME, message);
			return false;
		}
		return true;
	}

	private static String os = null;
	private static String gccVersion = null;
	private static String jvmVersion = null;

	/**
	 * Identifies the environment.
	 */
	static {
		// identify the operation system
		os = System.getProperty("os.name");

		// identify g++ compiler version
		try {
			Process gccv = Runtime.getRuntime().exec("g++ --version");
			StreamParser sp = new StreamParser(gccv.getInputStream());
			sp.start();
			gccv.waitFor();
			gccVersion = sp.getMessage();
		} catch (Throwable ignored) {
			// FIXME should be initialized with the process!
			gccVersion = "g++ (GCC) 3.4.5 (mingw-vista special r3)\n"
					+ "Copyright (C) 2004 Free Software Foundation, Inc.";
			ignored.printStackTrace();
		}

		// identify JVM version
		try {
			// first try the standard input stream
			Process jvmv = Runtime.getRuntime().exec("java -version");
			StreamParser sp = new StreamParser(jvmv.getInputStream());
			sp.start();
			jvmv.waitFor();
			jvmVersion = sp.getMessage();
		} catch (Throwable ignored) {
			ignored.printStackTrace();
		}
		try {
			// try the standard error stream
			if ("".equals(jvmVersion)) {
				Process jvmv = Runtime.getRuntime().exec("java -version");
				StreamParser sp = new StreamParser(jvmv.getErrorStream());
				sp.start();
				jvmv.waitFor();
				jvmVersion = sp.getMessage();
			}
		} catch (Throwable ignored) {
			ignored.printStackTrace();
		}
	}

	/**
	 * Retrieves the version of the used g++ compiler.
	 */
	public static String getGCCVersion() {
		return gccVersion;
	}

	/**
	 * Retrieves the version of the used JVM.
	 */
	public static String getJVMVersion() {
		return jvmVersion;
	}

	/**
	 * Retrieves the OS.
	 */
	public static String getOS() {
		return os;
	}
}
