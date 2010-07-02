<%@ page import="backgammonator.impl.webinterface.Util"%>
<%@ page import="backgammonator.impl.common.Backgammonator"%>
<%@ page import="java.io.PrintWriter"%>
<% Util.printHeader(request, new PrintWriter(out), "Source Upload", Util.USER, true); %>
<form method='POST' enctype='multipart/form-data' action='sourceupload' name="submitform"
  id="submitform">File to upload: <input type="file" name="filename" /> <br />
<br />
Programming language: <input type="radio" name="language" value="java" checked>Java</input> <input
  type="radio" name="language" value="c">C/C++ </input> <br />
<br />
<input type="checkbox" name="validate" value="yes" checked>Validate </input> <br />
<br />
<input class='button' type="submit" value="Upload" name="submitbutton"
    "<%= (Backgammonator.isUploadBlocked() ? " disabled " : "") %>"    
    onclick="document.submitform.submitbutton.value='Processing...';
    document.submitform.submitbutton.disabled=true;
    document.submitform.submit();" />
</form>
<div class="props">
<b>Environment Information<br/><br/></b>
OS: <%=Util.getOS()%><br/><br/>
<%=Util.getJVMVersion()%><br/>
<%=Util.getGCCVersion()%>
</div>
<% Util.printFooter(out); %>