<html>
<head>
<title>Backgammonator - Register</title>
</head>
<body>
<h2>Backgammonator - Register</h2>
<form method='POST' action="register" name="registerform"
	id="registerform">
<table>
	<tr>
		<td width='150px' style='vertical-align: top;'><br />
		<a href="/">Home</a> <br />
		<a href="Tutorial.jsp">Tutorials</a> <br />
		<a href="res/backgammonatorLibrary.jar">Library Jar</a> <br />
		</td>
		<td style='vertical-align: top;'><br />
		Register<br />
		</td>
	</tr>
	<tr>
		<td width='100px'>Username :</td>
		<td><input type="text" name="username" /></td>
	</tr>
	<tr>
		<td>Password :</td>
		<td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<td>Email :</td>
		<td><input type="text" name="email" /></td>
	</tr>
	<tr>
		<td>First Name :</td>
		<td><input type="text" name="firstname" /></td>
	</tr>
	<tr>
		<td>Last Name :</td>
		<td><input type="text" name="lastname" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><input type="submit" value="register" name="registerButton"
			onclick="document.startform.registerButton.value='Processing...';
        document.startform.registerButton.disabled=true;
        document.startform.submit();" />
		</td>
	</tr>
</table>
</form>
<%
	String message = request.getParameter("result");
	if (message != null) {
		out.println(message);
	}
%>
</body>
</html>