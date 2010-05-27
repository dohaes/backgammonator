package backgammonator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author georgi.b.andreev
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 950888998606711937L;

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection theConnection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306", "root", "root");
			Statement theStatement = theConnection.createStatement();
			ResultSet theResult = theStatement
					.executeQuery("select * from backgammonator.backgammonator");

			while (theResult.next()) {
				out.println(theResult.getString(1));
				out.println(theResult.getString(2));
				out.println("<br/>");
			}
			theResult.close();// Close the result set
			theStatement.close();// Close statement
			theConnection.close(); // Close database Connection
		} catch (Exception e) {
			out.println(e);
		}
	}
}