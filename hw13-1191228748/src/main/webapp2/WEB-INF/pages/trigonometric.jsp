<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<head>
	<title>Trigonometric values</title>
</head>
<style type="text/css">
table, td {
	border: 1px solid black;
	padding: 5px;
}
</style>
</head>
<body>
<table>
  <tr>
  	<th>x</th>
    <th>sin(x)</th>
    <th>cos(x)</th>
  </tr>
  <c:forEach var="val" items="${trigValues}">
  	<tr>
  		<td>${val.getAngle()}°</td>
		<td>${val.getSin()}</td>
		<td>${val.getCos()}</td>
	</tr>
</c:forEach>
</table>

	
</body>
</html>