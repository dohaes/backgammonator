package backgammonator.impl.webinterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpRetryException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author georgi.b.andreev
 */
public class Util {

	/**
	 * 
	 */
	public static final int HOME = 0;
	/**
	 * 
	 */
	public static final int ADMIN = 1;
	/**
	 * 
	 */
	public static final int USER = 2;

	/**
	 * Redirects the current page to the specified url and adds parameter
	 * result=message.
	 * 
	 * @param out the output stream.
	 * @param link the url to which the page is redirected.
	 * @param message the value of the result parameter.
	 */
	public static void redirect(PrintWriter out, String link, String message) {
		out.print("<body><form name='hiddenForm' action='" + link);
		out.print("' method='POST'><input type='hidden' name='result' value='");
		out.print(message
				+ "' ></input> </form> </body><script language='Javascript'>"
				+ "document.hiddenForm.submit();</script>");
	}

	/**
	 * Prints the header of a regular jsp.
	 * 
	 * @param out the output stream.
	 * @param title the title of the pages.
	 * @param type the type of the page.
	 * @throws IOException if an IO error occurs.
	 */
	public static void printHeader(JspWriter out, String title, int type)
			throws IOException {
		out.print("<html><head><title>Backgammonator - " + title
				+ "</title></head><body><h2>Backgammonator - " + title
				+ "</h2><table><tr><td width='150px' style='vertical"
				+ "-align:" + " top;'><a href='/'>Home</a><br />"
				+ "<a href='Tutorial.jsp'>Tutorials</a><br />"
				+ "<a href='res/backgammonatorLibrary.jar'>Library Jar</a>");
		out.println("</td><td style='vertical-align: top;'>");
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
	 * Prints the result parameter.
	 * 
	 * @param request the http request.
	 * @param out the output stream.
	 * @throws IOException if an IO error occurs.
	 */
	public static void printMessage(HttpServletRequest request, JspWriter out)
			throws IOException {
		String message = request.getParameter("result");
		if (message != null) {
			out.println(message + "<br/><br/>");
		}
	}
}
