<%@ page import="backgammonator.impl.webinterface.Util"%>
<% Util.printHeader(out, "Source Upload", Util.USER); %>
<% Util.printMessage(request, out); %>
<form method='POST' enctype='multipart/form-data' action='sourceupload' name="submitform"
  id="submitform">File to upload: <input type="file" name="filename" /> <br />
<br />
Programming language: <input type="radio" name="language" value="java" checked>Java</input> <input
  type="radio" name="language" value="c">C/C++ </input> <br />
<br />
<input type="checkbox" name="validate" value="yes" checked>Validate </input> <br />
<br />
<input type="submit" value="Upload" name="submitbutton"
    onclick="document.submitform.submitbutton.value='Processing...';
    document.submitform.submitbutton.disabled=true;
    document.submitform.submit();" />
</form>
<% Util.printFooter(out); %>