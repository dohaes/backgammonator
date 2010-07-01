<%@ page import="backgammonator.impl.webinterface.Util"%>
<% Util.printHeader(request, out, "Register", Util.HOME); %>
<form method='POST' action="register">
<table class='internal'>
<tr>
	<td width='130px'>Username </td>
	<td><input type="text" name="username" /></td>
</tr>
<tr>
	<td>Password </td>
	<td><input type="password" name="password" /></td>
</tr>
<tr>
	<td>Retype password</td>
	<td><input type="password" name="password1" /></td>
</tr>
<tr>
	<td>Email </td>
	<td><input type="text" name="email" /></td>
</tr>
<tr>
	<td>First Name </td>
	<td><input type="text" name="firstname" /></td>
</tr>
<tr>
	<td>Last Name </td>
	<td><input type="text" name="lastname" /></td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td><input class='button' type="submit" value="Register" /></td>
</tr>
</table>
</form>
<% Util.printFooter(out); %>