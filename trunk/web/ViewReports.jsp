<%@ page import="backgammonator.impl.webinterface.ReportsServlet"%>
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
    <% ReportsServlet.printReports(request, out, false); %>
    </td>
  </tr>
</table>
</body>
</html>