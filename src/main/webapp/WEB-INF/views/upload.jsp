<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>
<h2>Upload a file.. </h2>
    <form:form method="POST" action="uploadImage"  enctype="multipart/form-data">
  
        Upload file:
        <input type="file" name="file" />
        <input type="submit" value="Upload" />
        <form:errors path="file" cssStyle="color: #ff0000;" />
    </form:form>
</body>
</html>