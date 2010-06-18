<%@ page import="backgammonator.impl.webinterface.Util"%>
<% Util.printHeader(out, "Home", Util.HOME); %>
<% Util.printMessage(request, out); %>
<form method='POST' action='login'>
<table>
	<tr>
		<td width='100px'>Username :</td>
		<td><input type="textfield" name="username" /></td>
	</tr>
	<tr>
		<td>Password :</td>
		<td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><input type="submit" value="Login" /> <a
			href="Register.jsp">Register</a></td>
	</tr>
</table>
</form>
<br />
TODO: remove this when user management is ready.
<br />
<a href="SourceUpload.jsp">Source Upload</a> <br />
<a href="ViewReports.jsp">View Reports</a> <br />
<br />
<a href="StartTournament.jsp">Start Tournament</a><br />
<a href="ManageReports.jsp">Manage Reports</a><br />
<a href="ManageRegistrations.jsp">Manage Registrations</a><br />
<% Util.printFooter(out); %>