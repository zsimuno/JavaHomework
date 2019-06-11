<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Polls</title>
	</head>
	<body>
		<ol>
			<c:forEach var="poll" items="${polls}">
		  		<li><a href="glasanje?pollID=${poll.getId()}">${poll.getTitle()} </a> - ${poll.getMessage()} </li>
			</c:forEach>
		</ol>
	
	</body>
</html>