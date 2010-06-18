package backgammonator.impl.webinterface.tmp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backgammonator.impl.db.DBManager;

/**
 * @author georgi.b.andreev
 */
public final class CreateDatabase extends HttpServlet {

	private static final long serialVersionUID = -5241463414481622042L;

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			DBManager.createDB();
//			theStatement
//					.execute("INSERT INTO Account (username, password, isadmin, first, last) "
//							+ "VALUES ('georgi', 'andreev', 1, 'georgi', 'andreev')");
//			theStatement
//					.execute("INSERT INTO Account (username, password, isadmin, first, last) "
//							+ "VALUES ('andrei', 'penchev', 1, 'andrei', 'penchev')");
//			theStatement
//					.execute("INSERT INTO Account (username, password, isadmin, first, last) "
//							+ "VALUES ('stefan', 'tsvyatkov', 0, 'stefan', 'tsvyatkov')");
//			theStatement
//					.execute("INSERT INTO Account (username, password, isadmin, first, last) "
//							+ "VALUES ('elena', 'gramatova', 0, 'elena', 'gramatova')");

			// get current data
//			
//			ResultSet theResult = theStatement
//					.executeQuery("SELECT * FROM Account");
//			while (theResult.next()) {
//				int s = theResult.getMetaData().getColumnCount();
//				for (int i = 1; i <= s; i++) {
//					out.println(theResult.getString(i) + " ");
//				}
//				out.println("<br/>");
//			}
//
//			theResult.close();// Close the result set
//			theStatement.close();// Close statement
//			theConnection.close(); // Close database Connection
		} catch (Exception e) {
			out.println(e);
		}
	}
}