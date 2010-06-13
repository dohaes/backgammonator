<%@ page import="java.io.File" %>
<html>
<head>
<title>Backgammonator - Start Tournament</title>
</head>
<body>
<h2>Backgammonator - Start Tournament</h2>
<table>
<tr>
<td width='150px' style='vertical-align:top;'>
<a href="index.jsp">Home</a>
<br/>
<a href="Tutorial.jsp">Tutorials</a>
<br/>
<a href="Download.jsp">Downloads</a>
</td>
<td style='vertical-align:top;'>

<form method='POST' action='starttournament'
      name="startform" id="startform">
<table>
<tr>
<td width='150px' style='vertical-align:top;'>
<select multiple name="players" size="8" style="width:120px;">
<% File dir = new File("D:/uploads");
if (!dir.exists()) {
	dir.mkdir();
}
String[] players = dir.list();
for (int i = 0; i < players.length; i++) {
	out.println("<option value='" + players[i] + "'>" + players[i] + "</option>");
}
%>
</select>
</td>
<td style='vertical-align:top;'>
<input type='checkbox' name='logmoves' checked> Log moves</input><br/>
<input type='checkbox' name='plainrate' checked> Plain rate</input><br/>
<input type='text' name='groupscount' value='2' size="1"> Groups count</input><br/>
<input type='text' name='gamescount' value='3' size="1"> Games count</input><br/>
<input type='text' name='invalid' value='1' size="1"> Invalid game points</input><br/>
</td>
</tr>
</table>
<br/>
<input type='radio' name='type' value='BATTLE' checked>BATTLE</input>
<input type='radio' name='type' value='ELIMINATIONS'>ELIMINATIONS</input>
<input type='radio' name='type' value='GROUPS'>GROUPS</input><br/>
<br/>
<input type="submit" value="Start" name="startbutton"
        onclick="document.startform.startbutton.value='Processing...';
        document.startform.startbutton.disabled=true;
        document.startform.submit();" />
</form>
</td>
</tr>
</table>
</body>
</html>