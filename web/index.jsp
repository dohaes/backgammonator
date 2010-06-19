<%@ page import="backgammonator.impl.webinterface.Util"%>
<% Util.printHeader(request, out, "Login", Util.HOME); %>
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
TODO remove : <a href="CreateDB.jsp">Create DB</a>
<% Util.printFooter(out); %>