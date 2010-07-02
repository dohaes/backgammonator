<%@ page import="backgammonator.impl.webinterface.DownloadSources"%>
<%@ page import="backgammonator.impl.webinterface.Util"%>
<%
Util.printHeader(request, out, "Download Sources", Util.ADMIN);
DownloadSources.printSources(request, out);
Util.printFooter(out);
%>