<%@ page import="backgammonator.impl.webinterface.Util"%>
<%@ page
	import="backgammonator.impl.webinterface.ManageRegistrationsServlet"%>
<%
	Util.printHeader(request, out, "Manage Registrations", Util.ADMIN);
%>
<form method='POST' action='manageregistrations' name="manageform"
	id="startform">
<table class='internal'>
	<tr>
		<td width='150px' style='vertical-align: top;'>
		<select multiple
			name="registrations" size="8" style="width: 120px;">
			<%
				ManageRegistrationsServlet.printRegistrations(out);
			%>
		</select>
		</td>
	</tr>
</table>
<br />
<input class='button' type="submit" value="Delete" name="submitbutton"
	onclick="document.manageform.submitbutton.value='Processing...';
    document.manageform.submitbutton.disabled=true;
    document.manageform.submit();" />
</form>
<%
	Util.printFooter(out);
%>