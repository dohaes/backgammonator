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
    <a href="res/backgammonatorLibrary.jar">Library Jar</a> <br />
    <br />
    <a href="SourceUpload.jsp">Source Upload</a> <br />
    <a href="ViewReports.jsp">View Reports</a> <br />
    </td>
    <td style='vertical-align: top;'><br />
    <%
        File dir = new File(BackgammonatorConfig.getProperty("backgammonator.logger.outputDir",
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
        String url = BackgammonatorConfig.getProperty("backgammonator.web.reports",
                                                      "reports").replaceAll("\\\\", "/");
        out.println("<table>");
        for (int i = 0; i < tournaments.length; i++)
        {
            out.println("<tr><td> <a href='" + url + "/" + tournaments[i] + "' target='_blank'> "
                        + tournaments[i] + " </a> </td></tr> ");
        }
        out.println("</table>");
    %>
    </td>
  </tr>
</table>
</body>
</html>