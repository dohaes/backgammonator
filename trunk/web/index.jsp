<%@ page import="backgammonator.impl.webinterface.Util"%>
<% Util.printHeader(request, out, "Login", Util.HOME); %>
<form method='POST' action='login'>
<table class='internal'>
	<tr>
		<td width='130px'>Username</td>
		<td><input type="textfield" name="username" /></td>
	</tr>
	<tr>
		<td>Password</td>
		<td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><input type="submit" value="Login" class='button'/></td>
	</tr>
</table>
</form>
TODO remove : <a href="CreateDB.jsp">Create DB</a>
<% Util.printFooter(out); %>