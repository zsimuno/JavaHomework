<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Glasanje</title>
<style type="text/css">
<%
	Object colorAttr = session.getAttribute("pickedBgCol");
	if (colorAttr != null) {
		out.print("body {background-color: " + colorAttr.toString() + ";}");
	} else {
		out.print("body {background-color: white;}");
	}
%>
</style>
</head>
<body>
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
		<c:forEach var="band" items="${bands}">
	  		<li><a href="glasanje-glasaj?id=${band.getID()}">${band.getName()}</a></li>
		</c:forEach>
	</ol>
</body>
</html>