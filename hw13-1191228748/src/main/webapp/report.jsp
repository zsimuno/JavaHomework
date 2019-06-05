<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>OS usage report.</title>
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
	<h1>OS usage</h1>
	<p>Here are the results of OS usage in survey that we completed.</p>
	<img src="reportImage"  alt="Usage report"/>
</body>
</html>