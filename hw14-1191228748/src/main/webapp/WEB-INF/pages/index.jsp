<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Polls</title>
<style type="text/css">
<%@ include file="/WEB-INF/css/style.css" %>
</style></head>
<body>
	<h1>Polls</h1>
	<hr>
	<ol>
		<c:forEach var="poll" items="${polls}">
			<li><a href="glasanje?pollID=${poll.getId()}">${poll.getTitle()}
			</a></li>
		</c:forEach>
	</ol>

</body>
</html>