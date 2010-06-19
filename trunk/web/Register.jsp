<%@ page import="backgammonator.impl.webinterface.Util"%>
<% Util.printHeader(request, out, "Register");
   Util.printMessage(request, out); %>
<form method='POST' action="register" name="registerform" id="registerform">
<table>
<tr>
	<td width='100px'>Username : </td>
	<td><input type="text" name="username" /></td>
</tr>
<tr>
	<td>Password : </td>
	<td><input type="password" name="password" /></td>
</tr>
<tr>
	<td>Email : </td>
	<td><input type="text" name="email" /></td>
</tr>
<tr>
	<td>First Name : </td>
	<td><input type="text" name="firstname" /></td>
</tr>
<tr>
	<td>Last Name : </td>
	<td><input type="text" name="lastname" /></td>
</tr>
<tr>
	<td>&nbsp;</td>
	<td><input type="submit" value="Register" name="registerButton"
		onclick="document.registerform.registerButton.value='Processing...';
    	document.registerform.registerButton.disabled=true;
    	document.registerform.submit();" />
	</td>
</tr>
</table>
</form>
<% Util.printFooter(out); %>