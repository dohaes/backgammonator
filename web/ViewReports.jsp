<%@ page import="backgammonator.impl.webinterface.ReportsServlet"%>
<%@ page import="backgammonator.impl.webinterface.Util"%>
<%
Util.printHeader(out, "View Reports", Util.USER);
ReportsServlet.printReports(request, out, false);
Util.printFooter(out);
%>