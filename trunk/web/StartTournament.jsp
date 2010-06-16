<%@ page import="java.io.File"%>
<%@ page import="backgammonator.util.BackgammonatorConfig"%>
<html>
<head>
<title>Backgammonator - Start Tournament</title>
</head>
<body>
<h2>Backgammonator - Start Tournament</h2>
<table>
  <tr>
    <td width='150px' style='vertical-align: top;'><br />
    <a href="/">Home</a> <br />
    <a href="Tutorial.jsp">Tutorials</a> <br />
    <br />
    <a href="res/backgammonLibrary.jar">Library Jar</a> <br />
    <a href="res/backgammonDemo.jar">Demo Jar</a> <br />
    <a href="res/samplePlayers.jar">Sample Players</a> <br />
    <br />
    <a href="StartTournament.jsp">Start Tournament</a><br />
    <a href="ManageReports.jsp">View Reports</a><br />
    <a href="ManageRegistrations.jsp">Manage Registrations</a><br />
    </td>
    <td style='vertical-align: top;'><br />
    <%
        String message = request.getParameter("result");
    			if (message != null) {
    				out.println(message + "<br/><br/>");
    			}
    %>
    <form method='POST' action='starttournament' name="startform" id="startform">
    <table>
      <tr>
        <td width='150px' style='vertical-align: top;'><select multiple name="players" size="8"
          style="width: 120px;">
          <%
              File dir = new File(BackgammonatorConfig.getProperty(
          					"backgammonator.web.uploadDir", "uploads").replace('/',
          					File.separatorChar));
          			if (!dir.exists()) {
          				dir.mkdirs();
          			}
          			String[] players = dir.list();
          			for (int i = 0; i < players.length; i++) {
          				out.println("<option value='" + players[i] + "'>" + players[i]
          						+ "</option>");
          			}
          %>
        </select></td>
        <td style='vertical-align: top;'><input type='checkbox' name='logmoves' checked>
        Log moves</input><br />
        <input type='text' name='groupscount' value='2' size="1"> Groups count</input><br />
        <input type='text' name='gamescount' value='3' size="1"> Games count</input><br />
        </td>
      </tr>
    </table>
    <br />
    <input type='radio' name='type' value='BATTLE' checked>BATTLE</input> <input type='radio'
      name='type' value='ELIMINATIONS'>ELIMINATIONS</input> <input type='radio' name='type'
      value='GROUPS'>GROUPS</input><br />
    <br />
    <input type="submit" value="Start" name="startbutton"
      onclick="document.startform.startbutton.value='Processing...';
        document.startform.startbutton.disabled=true;
        document.startform.submit();" />
    </form>
    <br />
    </td>
  </tr>
</table>
</body>
</html>