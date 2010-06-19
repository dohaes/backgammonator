package backgammonator.impl.webinterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import backgammonator.lib.db.Account;

/**
 * @author georgi.b.andreev
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
	 * @throws IOException if an IO error occurs.
	 */
	public static void printHeader(HttpServletRequest request, JspWriter out,
			String title, int type) throws IOException {
		Account user = getCurrentUser(request);
		if (!checkCredentials(new PrintWriter(out), user, type)) {
			return;
		}
		out.print("<html><head><title>Backgammonator - " + title
				+ "</title></head><body><h2>Backgammonator - " + title
				+ "</h2><table><tr><td width='150px' style='vertical"
				+ "-align:" + " top;'>");

		if (user != null && user.isAdmin()) {
			out.print("<a href='StartTournament.jsp'>Start Tournament</a>"
					+ "<br /><a href='ManageReports.jsp'>Manage Reports</a>"
					+ "<br /><a href='ManageRegistrations.jsp'>"
					+ "Manage Registrations</a><br /><br />");
		}
		if (user != null && !user.isAdmin()) {
			out.print("<a href='SourceUpload.jsp'>Source Upload</a><br />"
					+ "<a href='ViewReports.jsp'>View Reports</a><br /><br />");
		}

		if (user == null) {
			out.print("<a href='/'>Login</a><br />");
		}
		out.print("<a href='Tutorial.jsp'>Tutorial</a><br />"
				+ "<a href='res/backgammonatorLibrary.jar'>Library Jar</a>");
		if (user != null) {
			out.print("<br /><br />" + user.getUsername()
					+ "<br /><a href='logout'>exit</a><br />");

		}
		out.println("</td><td style='vertical-align: top;'>");
		String message = request.getParameter("result");
		if (message != null) {
			out.println(message + "<br/><br/>");
		}
	}

	/**
	 * Prints the footer of a regular jsp.
	 * 
	 * @param out the output stream.
	 * @throws IOException if an IO error occurs.
	 */
	public static void printFooter(JspWriter out) throws IOException {
		out.print("</td></tr></table></body></html>");
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
		out.print("<body><form name='hiddenForm' action='" + link);
		out.print("' method='POST'><input type='hidden' name='result' value='");
		out.print(message
				+ "' ></input> </form> </body><script language='Javascript'>"
				+ "document.hiddenForm.submit();</script>");
	}

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

	static Account getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object account = session.getValue("user");
		if (!(account instanceof Account)) {
			return null;
		}
		return (Account) account;
	}

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
}
