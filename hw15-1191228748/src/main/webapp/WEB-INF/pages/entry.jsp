<%@page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry"%>
<%@page import="java.text.Format"%>
<%@page import="java.text.SimpleDateFormat"%>
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

<h1>Blog entry:</h1>
<h2>${entry.getTitle() }</h2>
<pre>${entry.getText() }</pre>
<p> Created at: ${entry.getCreatedAt()} </p>
<p> Last modified: ${entry.getLastModifiedAt()} </p>


<c:if test="${sessionScope['current.user.id'] == nick}">		
		<form action="/edit" method="post">
		<input type="hidden" name="id" value="${entry.getId() }">
		<div class="formControls">
		  <input type="submit" name="metoda" value="Edit">
		</div>
		</form>
		
</c:if>


<c:if test="${!blogEntry.comments.isEmpty()}">
      <ul>
      <c:forEach var="e" items="${blogEntry.comments}">
        <li>
	        <div style="font-weight: bold">
	        [User=<c:out value="${e.usersEMail}"/>] 
	        	<c:out value="${e.postedOn}"/>
	        </div>
	        <div style="padding-left: 10px;">
	        	<c:out value="${e.message}"/>
	        </div>
        </li>
      </c:forEach>
      </ul>
</c:if>

<c:if test="${sessionScope['current.user.id'] != null}">	
<c:out value="${commentMessage}" />	
	<form action="" method="post">
		<textarea name="comment" ></textarea>
		<div class="formControls">
		  <input type="submit" name="metoda" value="Comment">
		</div>
	</form>	
</c:if>
	
</body>
</html>