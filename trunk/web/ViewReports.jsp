<%@ page import="backgammonator.impl.webinterface.ReportsServlet"%>
<%@ page import="backgammonator.impl.webinterface.Util"%>
<%
Util.printHeader(request, out, "View Reports");
ReportsServlet.printReports(request, out, false);
Util.printFooter(out);
%>