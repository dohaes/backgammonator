package backgammonator.impl.webinterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import backgammonator.impl.db.DB;
import backgammonator.lib.db.Account;
import backgammonator.lib.db.AccountsManager;

/**
 * ManageRegistrationsServlet represents the servlet with which tha admin manage
 * the registrations
 */
public final class ManageRegistrationsServlet extends HttpServlet {

	private static final long serialVersionUID = 9174306858493853786L;
	static AccountsManager accMan;

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		System.out.println("1");
		PrintWriter out = res.getWriter();
		if (accMan == null) accMan = DB.getDBManager().getAccountsManager();
		System.out.println("2");
		try {
			System.out.println("3");
			String[] players = req.getParameterValues("registrations");
			Account account = null;
			System.out.println("4:" + players.length);
			for (int i = 0; i < players.length; i++) {
				account = accMan.getAccount(players[i]);
				if(account != null) account.delete();
			}
			Util.redirect(out, Util.MANAGE_REGISTRATIONS,
					"Registrations deleted successfully.");
			return;
		} catch (Exception e) {
			Util.redirect(out, Util.MANAGE_REGISTRATIONS,
					"Registrations was not deleted successfully."
							+ e.getMessage());
			return;
		}
	}

	/**
	 * Prints all registrations in option form
	 * 
	 * @param out the output stream.
	 * @throws IOException if an IO error occurs.
	 */
	public static void printRegistrations(JspWriter out) throws IOException {
		if (accMan == null) accMan = DB.getDBManager().getAccountsManager();
		List<Account> allAccounts = accMan.getAllAccounts();
		Iterator<Account> iter = allAccounts.iterator();
		Account account;
		while (iter.hasNext()) {
			account = iter.next();
			out.println("<option value='" + account.getUsername() + "'>"
					+ account.getUsername() + "</option>");
		}
	}
}