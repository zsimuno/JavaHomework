<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Error while getting powers document</title>
</head>
<body>
	<h1>Error while getting powers document!</h1>

	<p><%=request.getAttribute("error")%></p>
</body>
</html>