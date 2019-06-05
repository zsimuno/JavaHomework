<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<title>webapp2 - index</title>
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
	
		<a href="colors.jsp">Background color chooser</a> <br>
		<a href="trigonometric?a=0&b=90">Trigonometric functions</a> <br><br>
	
		<form action="trigonometric" method="GET">
			Početni kut:<br> <input type="number" name="a" min="0" max="360"
				step="1" value="0"><br> Završni kut:<br> <input
				type="number" name="b" min="0" max="360" step="1" value="360"><br>
			<input type="submit" value="Tabeliraj"><input type="reset"
				value="Reset">
		</form>
		<br> <br>
		<a href="stories/funny.jsp">Read a funny short story.</a> <br>
		<a href="report.jsp">OS usage report.</a> <br>
		<a href="powers?a=1&b=100&n=3">Get excel table.</a> <br>
		<a href="appinfo.jsp">Show application uptime.</a> <br>
		<a href="glasanje">Vote on your favorite band.</a> <br>
		
	</body>
</html>