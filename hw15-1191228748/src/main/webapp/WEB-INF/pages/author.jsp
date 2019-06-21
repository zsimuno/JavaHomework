<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>${nick } - Author Page</title>
<style type="text/css">
<%@ include file="/WEB-INF/css/style.css" %>
</style></head>
<body>
<%@ include file="/WEB-INF/pages/header.jsp" %>

<c:choose>
   <c:when test="${entries.isEmpty()}">
     No entries yet.
   </c:when>
   <c:otherwise>
     <h2>Entries:</h2>
	<ul>
		<c:forEach var="entry" items="${entries}">
			<li><a href="${nick }/${entry.getId() }">${entry.getTitle() }</a></li>
		</c:forEach>
	</ul>	
   </c:otherwise>
</c:choose>


<c:if test="${sessionScope['current.user.nick'] == nick}">
		<li><a href="${nick }/new">New entry.</a>
		
</c:if>
</body>
</html>