package backgammonator.impl.webinterface;

import java.io.IOException;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import backgammonator.impl.db.AccountsManager;
import backgammonator.lib.db.Account;

/**
 * Login represents the servlet with which contestants login into the system
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
			String pass = req.getParameter("password");

			if ("".equals(user) || "".equals(pass)) {
				unSuccLogin(out, "Please enter your username and password!");
			}

			Account acount = AccountsManager.getAccount(user);
			if (acount.exists() && acount.getPassword().equals(MD5(pass))) {
				HttpSession session = req.getSession();
				session.putValue("username", user);
				if (!acount.isAdmin()) afterSucLogin(out, "Hello " + user);
				else afterSucLoginAdmin(out, "Successful login");
			} else {
				unSuccLogin(out, "Username/Password mismatch!");
			}

		} catch (Exception e) {
			unSuccLogin(out, "Exception!");
			e.printStackTrace();
		}
	}

	private void unSuccLogin(PrintWriter out, String message) {
		out
				.print("<body><form name='hiddenForm' action='index.jsp' method='POST'>");
		out.print("<input type='hidden' name='result' value='" + message
				+ "' ></input> </form> </body>");
		out.print("<script language='Javascript'>");
		out.print("document.hiddenForm.submit();");
		out.print("</script>");
	}

	private void afterSucLogin(PrintWriter out, String message) {
		out
				.print("<body><form name='hiddenForm' action='SourceUpload.jsp' method='POST'>");
		out.print("<input type='hidden' name='result' value='" + message
				+ "' ></input> </form> </body>");
		out.print("<script language='Javascript'>");
		out.print("document.hiddenForm.submit();");
		out.print("</script>");
	}

	private void afterSucLoginAdmin(PrintWriter out, String message) {
		out
				.print("<body><form name='hiddenForm' action='StartTournament.jsp' method='POST'>");
		out.print("<input type='hidden' name='result' value='" + message
				+ "' ></input> </form> </body>");
		out.print("<script language='Javascript'>");
		out.print("document.hiddenForm.submit();");
		out.print("</script>");
	}

	private static String MD5(String pass) throws NoSuchAlgorithmException {
		MessageDigest m = null;
		m = MessageDigest.getInstance("MD5");
		byte[] data = pass.getBytes();
		m.update(data, 0, data.length);
		BigInteger i = new BigInteger(1, m.digest());
		return String.format("%1$032X", i);
	}
}
