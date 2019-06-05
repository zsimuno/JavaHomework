<%@ page import="java.util.Date" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%!
private String getAppTime(Object appStartTime) throws java.io.IOException {
		if (appStartTime == null)
			return "Can't print application uptime (Not available)!";

		Long elapsedTime = null;
		try {
			elapsedTime = System.currentTimeMillis() - Long.parseLong(appStartTime.toString());
		} catch (Exception e) {
			return "Can't print application uptime (Not readable)!";
		}

		long s = elapsedTime / 1000;
		long m = s / 60;
		long h = m / 60;
		long d = h / 24;
		return d + " days " + h % 24 + " hours " + m % 60 + " minutes " + s % 60 + " seconds " + elapsedTime % 1000 + " milliseconds";

	}
	%>
<!DOCTYPE html>
<html>
<head>
	<title>Application uptime</title>
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
	<%=getAppTime(application.getAttribute("appStartTime"))%>
</body>
</html>