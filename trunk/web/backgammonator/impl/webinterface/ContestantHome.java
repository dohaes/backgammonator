package backgammonator.impl.webinterface;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author georgi.b.andreev
 */
public class ContestantHome extends HttpServlet {

	private static final long serialVersionUID = 5636029534043133573L;

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Upload Player <br/>");
		out.println("View Results <br/>");
		out.println("contest settings <br/>");
		out.println("exit <br/>");
	}
}