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
		<h1>
		<c:choose>
		<c:when test="${zapis.id.isEmpty()}">
		Novi kontakt
		</c:when>
		<c:otherwise>
		Login:
		</c:otherwise>
		</c:choose>
		</h1>

		<form action="author" method="post">
		
		<div>
		 <div>
		  <span class="formLabel">Nick</span><input type="text" name="nick" value='<c:out value="${zapis.prezime}"/>' size="20">
		 </div>
		 <c:if test="${zapis.imaPogresku('prezime')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('prezime')}"/></div>
		 </c:if>
		</div>
		
		<div>
		 <div>
		  <span class="formLabel">Password</span><input type="password" name="password" value='<c:out value="${zapis.prezime}"/>' size="20">
		 </div>
		 <c:if test="${zapis.imaPogresku('prezime')}">
		 <div class="greska"><c:out value="${zapis.dohvatiPogresku('prezime')}"/></div>
		 </c:if>
		</div>

		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Submit">
		  <input type="Reset" name="metoda" value="Reset">
		</div>
		
		</form>
		
		<a href="register" >Register</a>
		
		
		<h2>List of registered authors:</h2>
		<ul class="authors">
		<c:forEach var="author" items="${registeredAuthors}">
	  		<li class="author">${author.getNick()} (${author.getFirstName()} ${author.getLastName()})</li>
		</c:forEach>
		</ul>

	</body>
</html>
