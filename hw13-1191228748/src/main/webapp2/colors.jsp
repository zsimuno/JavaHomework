<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Set app color</title>
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
	<ul>
		<li><a href="setcolor?color=white">WHITE</a></li>
		<li><a href="setcolor?color=red">RED</a></li>
		<li><a href="setcolor?color=green">GREEN</a></li>
		<li><a href="setcolor?color=cyan">CYAN</a></li>
	</ul>
</body>
</html>