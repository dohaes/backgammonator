<html>
<head>
<title>Source Upload</title>
</head>
<body>

<h2>Source Upload</h2>

<br/>

<% 
String message = request.getParameter("result");
if (message != null) out.println(message + "<br/><br/>"); 
%>

<form method='POST' enctype='multipart/form-data' action='sourceupload'
      name="submitform" id="submitform" >

File to upload: <input type="file" name="filename" />

<br/><br/>

Programming language: 
<input type="radio" name="language" value="java" checked>Java</input>
<input type="radio" name="language" value="c">C/C++ </input>

<br/><br/>
<input type="checkbox" name="validate" value="yes" checked>Validate </input>

<br/><br/>

<input type="submit" value="Upload" name="submitbutton"
        onclick="document.submitform.submitbutton.value='Processing...';
        document.submitform.submitbutton.disabled=true;
        document.submitform.submit();" />
</form>

</body>
</html>
