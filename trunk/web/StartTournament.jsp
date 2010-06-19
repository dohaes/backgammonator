<%@ page import="backgammonator.impl.webinterface.Util"%>
<%@ page import="backgammonator.impl.webinterface.StartTournamentServlet"%>
<% Util.printHeader(request, out, "Start Tournament", Util.ADMIN); %>
<form method='POST' action='starttournament' name="startform" id="startform">
<table>
  <tr>
    <td width='150px' style='vertical-align: top;'><select multiple name="players" size="8"
      style="width: 120px;">
      <% StartTournamentServlet.printPlayers(out); %>
    </select></td>
    <td style='vertical-align: top;'><input type='checkbox' name='logmoves' checked> Log
    tournament</input><br />
    <input type='text' name='groupscount' value='1' size="1"> Groups count</input><br />
    <input type='text' name='gamescount' value='1' size="1"> Games count</input><br />
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
<% Util.printFooter(out); %>