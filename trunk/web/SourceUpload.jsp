<%@ page import="backgammonator.impl.webinterface.Util"%>
<%@ page import="backgammonator.impl.common.Backgammonator"%>
<% Util.printHeader(request, out, "Source Upload", Util.USER, true); %>
<form method='POST' enctype='multipart/form-data' action='sourceupload' name="submitform"
  id="submitform">File to upload: <input type="file" name="filename" /> <br />
<br />
Programming language: <input type="radio" name="language" value="java" checked>Java</input> <input
  type="radio" name="language" value="c">C/C++ </input> <br />
<br />
<input type="checkbox" name="validate" value="yes" checked>Validate </input> <br />
<br />
<input class='button' type="submit" value="Upload" name="submitbutton"
    disabled="<%= Backgammonator.isUploadBlocked() %>"    
    onclick="document.submitform.submitbutton.value='Processing...';
    document.submitform.submitbutton.disabled=true;
    document.submitform.submit();" />
</form>
<% Util.printFooter(out); %>