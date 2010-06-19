package backgammonator.impl.webinterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import backgammonator.impl.db.AccountsManager;
import backgammonator.lib.db.Account;

/**
 * RegisterServlet represents the servlet with which contestants register their
 * accounts.
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
		String user = req.getParameter("username");
		String pass = req.getParameter("password");
		String email = req.getParameter("email");
		String first = req.getParameter("firstname");
		String last = req.getParameter("lastname");
		if ("".equals(user) || "".equals(pass)) {
			Util.redirect(out, Util.REGISTER, "Missing field!");
		} else if (!validateMail(email)) {
			Util.redirect(out, Util.REGISTER, "Email is not correct!");
		} else {
			Account account = AccountsManager.getAccount(user);
			if (account.exists()) {
				Util.redirect(out, Util.REGISTER, "Username is not free!");
			} else {
				account.setEmail(email);
				account.setFirstName(first);
				account.setLastname(last);
				account.setPassword(Util.MD5(pass));
				account.store();
				HttpSession session = req.getSession();
				session.putValue("user", account);
				Util.redirect(out, Util.USER_HOME,
						"Registration completed successfully.");
			}
		}
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