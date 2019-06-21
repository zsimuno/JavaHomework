<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="java.text.Format"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>${blogEntry.getTitle() }</title>
<style type="text/css">
<%@ include file="/WEB-INF/css/style.css" %>
</style></head>
<body>
<%@ include file="/WEB-INF/pages/header.jsp" %>


<h1>${blogEntry.getTitle() }</h1>
<pre>${blogEntry.getText() }</pre>
<hr>
<pre> Created at: ${blogEntry.getCreatedAt()} Last modified: ${blogEntry.getLastModifiedAt()} </pre>


<c:if test="${sessionScope['current.user.nick'] == nick}">		
		<a  href="edit?id=${blogEntry.getId()}"> Edit </a>
		<br>
</c:if>


<c:choose>
   <c:when test="${blogEntry.comments.isEmpty()}">
     No comments yet.
   </c:when>
   <c:otherwise>
   
     <ul>
      <c:forEach var="e" items="${blogEntry.comments}">
      	<hr>
        <li>
	        <div style="font-weight: bold">
	        [<c:out value="${e.usersEMail}"/>] 
	        	<c:out value="${e.postedOn}"/>
	        </div>
	        <div style="padding-left: 10px;">
	        	<c:out value="${e.message}"/>
	        </div>
        </li>
      </c:forEach>
      </ul>	
   </c:otherwise>
</c:choose>


<c:if test="${!blogEntry.comments.isEmpty()}">
      
</c:if>

	
<c:out value="${commentMessage}" />
	
	<form action="" method="post">
<c:if test="${sessionScope['current.user.id'] == null}">
	Email:<input type="email" name="email" required/><br>
</c:if>
		Comment:<br>
		<textarea name="comment" rows="5" cols="50" required></textarea>
		<div class="formControls">
		  <input type="submit" name="metoda" value="Comment">
		</div>
	</form>	

	
</body>
</html>