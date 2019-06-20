<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Register</title>
		
		<style type="text/css">
		<%@ include file="/WEB-INF/css/style.css" %>
		</style>
	</head>

	<body>
		<%@ include file="/WEB-INF/pages/header.jsp" %>
		<h1>
		Register
		</h1>
		
		<c:out value="${registerMessage}" />
		<br />
		<form action="register" method="post">

		  <span class="formLabel">First name</span><input type="text" name="firstName" value='<c:out value="${user.getFirstName()}"/>'  size="20"> <br />
		  <span class="formLabel">Last name</span><input type="text" name="lastName" value='<c:out value="${user.getLastName()}"/>'  size="20"> <br />
		  <span class="formLabel">EMail</span><input type="email" name="email" value='<c:out value="${user.getEmail()}"/>'  size="50"> <br />
		  <span class="formLabel">Nick</span><input type="text" name="nick" value='<c:out value="${user.getNick()}"/>' size="20"> <br />
		  <span class="formLabel">Password</span><input type="password" name="password"  size="20"> <br />
		 
		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Submit">
		  <input type="reset" name="metoda" value="Reset">
		</div>
		
		</form>

	</body>
</html>
