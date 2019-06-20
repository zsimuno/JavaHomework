<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Error</title>
<style type="text/css">
<%@ include file="/WEB-INF/css/style.css" %>
</style></head>
<body>
<%@ include file="/WEB-INF/pages/header.jsp" %>
<h2>Entries:</h2>
<ul>
	<c:forEach var="entry" items="${entries}">
			<li><a href="/${entry.getId() }" target="_blank">${entry.getTitle() }</a></li>
	</c:forEach>
</ul>	

<c:if test="${sessionScope['current.user.id'] == nick}">
		<li><a href="/new">New entry.</a>
		
</c:if>
</body>
</html>