<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Glasanje</title>
<style type="text/css">
<%@ include file="/WEB-INF/css/style.css" %>
</style></head>
<body>
	<h1>${poll.getTitle() }</h1>
	<hr>
	<p>${poll.getMessage() }</p>
	<ol>
		<c:forEach var="option" items="${pollOptions}">
	  		<li>${option.getOptionTitle()} - <a href="glasanje-glasaj?id=${option.getId()}">VOTE</a></li>
		</c:forEach>
	</ol>
</body>
</html>