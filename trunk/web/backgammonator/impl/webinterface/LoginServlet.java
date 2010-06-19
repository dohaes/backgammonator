package backgammonator.impl.webinterface;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import backgammonator.impl.db.AccountsManager;
import backgammonator.lib.db.Account;

/**
 * Login represents the servlet with which contestants login into the system.
 */
public final class LoginServlet extends HttpServlet {

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
			String pass = Util.MD5(req.getParameter("password"));

			if ("".equals(pass)) {
				Util.redirect(out, "index.jsp", "Password is missing!");
			}
			if ("".equals(user)) {
				Util.redirect(out, "index.jsp", "Username is missing!");
			}

			Account account = AccountsManager.getAccount(user);
			if (account.exists() && account.getPassword().equals(pass)) {
				HttpSession session = req.getSession();
				session.putValue("user", account);
				if (!account.isAdmin()) {
					Util.redirect(out, "SourceUpload.jsp", "Successful login.");
				} else {
					Util.redirect(out, "StartTournament.jsp",
							"Successful login.");
				}
			} else {
				Util.redirect(out, "index.jsp", "Username/Password mismatch!");
			}

		} catch (Exception e) {
			Util.redirect(out, "index.jsp", "Exception occured!");
			e.printStackTrace();
		}
	}
}