<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Blog entry editor</title>
<style type="text/css">
<%@ include file="/WEB-INF/css/style.css" %>
</style></head>
<body>
<%@ include file="/WEB-INF/pages/header.jsp" %>
	<c:if test="${sessionScope['current.user.nick'] == nick}">
		<h1>New entry:</h1>
		
		<form action="" method="post">
		  <input type="hidden" name="id" value='<c:out value="${entry.id }"></c:out>' /> 
		  <span class="formLabel">Title</span><input type="text" name="title" size="20"  value='<c:out value="${entry.getTitle()}"/>' required> <br>
		  <span class="formLabel">Text</span><textarea name="text" cols="35" rows="15" required><c:out value="${entry.getText()}"/></textarea>
		
		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Submit">
		  <input type="reset" name="metoda" value="Reset">
		</div>
		</form>
		
	</c:if>
</body>
</html>