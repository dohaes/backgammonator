package backgammonator.impl.webinterface;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RegisterServlet represents...
 */
public final class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO if it is registered!
		System.out.println("12do postfffffffff!");
		System.err.println("12do postsddgdfffffffffffffffffff!");
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		String user = (String) req.getAttribute("username");
		String pass = (String) req.getAttribute("password");
		String email = (String) req.getAttribute("email");
		String first = (String) req.getAttribute("first");
		String last = (String) req.getAttribute("last");

		if (user != null && pass != null && validateMail(email)
				&& first != null && last != null) {

		} else {
			afterUpload(out, "Something is not typed!");
		}

	}

	private void afterUpload(PrintWriter out, String message) {
		out
				.print("<body><form name='hiddenForm' action='Register.jsp' method='POST'>");
		out.print("<input type='hidden' name='result' value='" + message
				+ "' ></input> </form> </body>");
		out.print("<script language='Javascript'>");
		out.print("document.hiddenForm.submit();");
		out.print("</script>");
	}

	private boolean validateMail(String email) {
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

		// Match the given string with the pattern
		Matcher m = p.matcher(email);

		// check whether match is found
		return m.matches();
	}

}
