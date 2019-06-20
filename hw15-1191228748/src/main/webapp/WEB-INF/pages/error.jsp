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
	<h1>Error!</h1>

	<p><%=request.getAttribute("errorMessage")%></p>
	
</body>
</html>