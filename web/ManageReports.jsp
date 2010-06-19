<%@ page import="backgammonator.impl.webinterface.ReportsServlet"%>
<%@ page import="backgammonator.impl.webinterface.Util"%>
<%
Util.printHeader(request, out, "Manage Reports");
ReportsServlet.printReports(request, out, true);
Util.printFooter(out);
%>