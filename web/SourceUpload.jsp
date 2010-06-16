<html>
<head>
<title>Backgammonator - Source Upload</title>
</head>
<body>
<h2>Backgammonator - Source Upload</h2>
<table>
  <tr>
    <td width='150px' style='vertical-align: top;'><br />
    <a href="/">Home</a> <br />
    <a href="Tutorial.jsp">Tutorials</a> <br />
    <a href="res/backgammonatorLibrary.jar">Library Jar</a> <br />
    <br />
    <a href="SourceUpload.jsp">Source Upload</a> <br />
    <a href="ViewReports.jsp">View Reports</a> <br />
    </td>
    <td style='vertical-align: top;'><br />
    <%
        String message = request.getParameter("result");
    			if (message != null) {
    				out.println(message + "<br/><br/>");
    			}
    %>
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
    <br />
    </td>
  </tr>
</table>
</body>
</html>