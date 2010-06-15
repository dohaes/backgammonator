<%@ page import="java.io.File"%>
<%@ page import="java.io.FilenameFilter"%>
<%@ page import="backgammonator.util.BackgammonatorConfig"%>
<html>
<head>
<title>Backgammonator - View Reports</title>
</head>
<body>
<h2>Backgammonator - View Reports</h2>
<table>
  <tr>
    <td width='150px' style='vertical-align: top;'><br />
    <a href="/">Home</a> <br />
    <a href="Tutorial.jsp">Tutorials</a> <br />
    <br />
    <a href="res/backgammonLibrary.jar">Library Jar</a> <br />
    <a href="res/backgammonDemo.jar">Demo Jar</a> <br />
    <a href="res/samplePlayers.jar">Sample Players</a> <br />
    <br />
    <a href="StartTournament.jsp">Start Tournament</a><br />
    <a href="ViewReports.jsp">View Reports</a><br />
    <a href="ManageRegistrations.jsp">Manage Registrations</a><br />
    </td>
    <td style='vertical-align: top;'><br />
    View Delete Publish<br />
          <%
		File dir = new File(BackgammonatorConfig.getProperty(
				"backgammonator.tournament.loggerOutputDir", "reports"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String[] tournaments = dir.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".html");
			}
		});
		String url = BackgammonatorConfig.getProperty(
				"backgammonator.tournament.loggerOutputDir", "reports")
				.replaceAll("\\\\", "/")
				+ "/";
		for (int i = 0; i < tournaments.length; i++) {
			out.println("<a href='" + url + tournaments[i] + "' target='_blank'>"
					+ tournaments[i] + "</a>");
		}
          %>
    </td>
  </tr>
</table>
</body>
</html>