package backgammonator.impl.webinterface;

import java.io.PrintWriter;

/**
 * @author georgi.b.andreev
 */
public class Util {

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
}
