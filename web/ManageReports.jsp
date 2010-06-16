<%@ page import="java.io.File"%>
<%@ page import="java.io.FilenameFilter"%>
<%@ page import="backgammonator.util.BackgammonatorConfig"%>
<html>
<head>
<title>Backgammonator - Manage Reports</title>
</head>
<body>
<h2>Backgammonator - Manage Reports</h2>
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
    <a href="ManageReports.jsp">View Reports</a><br />
    <a href="ManageRegistrations.jsp">Manage Registrations</a><br />
    </td>
    <td style='vertical-align: top;'><br />
    <%
        String message = request.getParameter("result");
        if (message != null)
        {
            out.println(message + "<br/><br/>");
        }
        File dir = new File(BackgammonatorConfig.getProperty("backgammonator.tournament.loggerOutputDir",
                                                             "reports"));
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        String[] tournaments = dir.list(new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return name.endsWith(".html");
            }
        });
        String url = BackgammonatorConfig.getProperty("backgammonator.tournament.loggerOutputDir",
                                                      "reports").replaceAll("\\\\", "/");
        out.println("<table>");
        for (int i = 0; i < tournaments.length; i++)
        {
            out.println("<tr><td> <a href='" + url + "/" + tournaments[i] + "' target='_blank'> "
                        + tournaments[i] + " </a> </td><td> <a href='reports?tid=" + tournaments[i]
                        + "&op=delete'> delete </a> </td></tr> ");
        }
        out.println("</table>");
    %>
    </td>
  </tr>
</table>
</body>
</html>