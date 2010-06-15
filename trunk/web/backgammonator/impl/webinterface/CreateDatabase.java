package backgammonator.impl.webinterface;

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
			Class.forName("com.mysql.jdbc.Driver");
			Connection theConnection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306", "root", "root");
			Statement theStatement = theConnection.createStatement();

			theStatement.execute("DROP DATABASE Backgammonator");
			theStatement.execute("CREATE DATABASE Backgammonator");
			theStatement.execute("CREATE TABLE Account ( "
					+ "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, user varchar(32) NOT NULL UNIQUE,"
					+ "pass varchar(32) NOT NULL, first varchar(32), "
					+ "LastName varchar(32) )");

			// get current data
			ResultSet theResult = theStatement
					.executeQuery("select * from Backgammonator.Account");
			while (theResult.next()) {
				int s = theResult.getFetchSize();
				for (int i = 0; i < s; i++) {
					out.println(theResult.getString(i) + " ");
				}
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