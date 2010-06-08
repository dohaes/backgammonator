<html>
<head>
<title>Source Upload</title>
</head>
<body>

<h2>Source Upload</h2>

<br/>

<%! private final static String UPLOADS = "D:/uploads"; %>
<!-- TODO use the user name of the contestant -->
<%! private final static String USER = "test_user"; %>

<form method='POST' enctype='multipart/form-data' action='sourceupload'>

File to upload: <input type="file" name="filename" />

<br/><br/>

Programming language: 
<input type="radio" name="language" value="java" checked>Java</input>
<input type="radio" name="language" value="c">C/C++ </input>

<br/><br/>
<input type="checkbox" name="validate" value="yes">Validate </input>

<br/><br/>

<input type=submit value=Upload />
</form>

</body>
</html>
