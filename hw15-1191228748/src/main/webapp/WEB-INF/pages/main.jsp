<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Main page</title>
		
		<style type="text/css">
		<%@ include file="/WEB-INF/css/style.css" %>
		</style>
	</head>

	<body>
	<%@ include file="/WEB-INF/pages/header.jsp" %>
		<c:out value="${loginMessage}" />
		
		<c:if test="${sessionScope['current.user.id'] == null}">
		<h1>Login:</h1>
		
		<form action="main" method="post">
		
		  <span class="formLabel">Nick</span><input type="text" name="nick" value='<c:out value="${nick}"/>' required size="20">
		  <span class="formLabel">Password</span><input type="password" name="password" required size="20">
		
		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Submit">
		  <input type="reset" name="metoda" value="Reset">
		</div>
		</form>
		
		<p>
		Want to register? <a href="register" >Register here</a>
		</p>
		
		</c:if>
		
		
		
		
		<h2>List of registered authors:</h2>
		<ul class="authors">
		<c:forEach var="author" items="${registeredAuthors}">
	  		<li class="author"> 
	  		<a href="author/${author.getNick()}">${author.getNick()}</a>
	  		(${author.getFirstName()} ${author.getLastName()})</li>
		</c:forEach>
		</ul>

	</body>
</html>
