<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
	<head>
		<title>webapp2 - index</title>
	</head>
		<style type="text/css">
		<%
		Object colorAttr = session.getAttribute("pickedBgCol");		
		if (colorAttr != null) {
			out.print("body {background-color: " + colorAttr.toString() + ";}");
		}%>
		</style>
	</head>
	<body>
	
		<a href="colors.jsp">Background color chooser</a> <br>
		<a href="trigonometric?a=0&b=90">Trigonometric functions</a> <br>
	
		<form action="trigonometric" method="GET">
			Početni kut:<br> <input type="number" name="a" min="0" max="360"
				step="1" value="0"><br> Završni kut:<br> <input
				type="number" name="b" min="0" max="360" step="1" value="360"><br>
			<input type="submit" value="Tabeliraj"><input type="reset"
				value="Reset">
		</form>
		<br>
		
		<a href="appinfo.jsp">Show application uptime.</a> <br>
		<a href="powers?a=1&b=100&n=3">Get excel table.</a> <br>
	</body>
</html>