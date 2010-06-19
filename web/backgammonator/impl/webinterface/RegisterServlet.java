package backgammonator.impl.webinterface;

import java.io.IOException;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backgammonator.impl.db.AccountsManager;
import backgammonator.lib.db.Account;

/**
 * RegisterServlet represents the servlet with which contestants register their
 * accounts
 */
public final class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 9174306858493853786L;

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		try {
			String user = req.getParameter("username");
			String pass = req.getParameter("password");
			String email = req.getParameter("email");
			String first = req.getParameter("firstname");
			String last = req.getParameter("lastname");
			if ("".equals(user) || "".equals(pass) || "".equals(first)
					|| "".equals(last)) {
				afterRegister(out, "Missing field!");
			} else if (!validateMail(email)) {
				afterRegister(out, "Email is not correct");
			} else {
				Account account = AccountsManager.getAccount(user);
				if (account.exists()) {
					afterRegister(out, "Username is not free!");
				} else {
					String md5Pass = MD5(pass);
					account.setEmail(email);
					account.setFirstName(first);
					account.setLastname(last);
					account.setPassword(md5Pass);
					account.store();
					afterRegister(out, "Registration completed successfully!");
				}
			}
		} catch (Exception e) {
			afterRegister(out, "Exception while registering!");
			e.printStackTrace();
		}

	}

	private static String MD5(String pass) throws NoSuchAlgorithmException {
		MessageDigest m = null;
		m = MessageDigest.getInstance("MD5");
		byte[] data = pass.getBytes();
		m.update(data, 0, data.length);
		BigInteger i = new BigInteger(1, m.digest());
		return String.format("%1$032X", i);
	}

	private void afterRegister(PrintWriter out, String message) {
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
