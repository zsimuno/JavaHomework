<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Glasanje</title>
</head>
<body>
	<h1>${poll.getTitle() }</h1>
	<p>${poll.getMessage() }</p>
	<ol>
		<c:forEach var="option" items="${pollOptions}">
	  		<li><a href="glasanje-glasaj?id=${option.getId()}">${option.getOptionTitle()}</a></li>
		</c:forEach>
	</ol>
</body>
</html>