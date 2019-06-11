<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Rezultati</title>
<style type="text/css">
table.rez, td {
	text-align: center;
	border: 1px solid black;
}
</style>
</head>
<body>
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table class="rez">
		<thead>
			<tr>
				<th>Opcija</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${results}">
				<tr>
					<td>${result.getOptionTitle() }</td>
					<td>${result.getVotesCount() }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" />
	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>
	<h2>Razno</h2>
	<p>Linkovi pobjedničkih opcija:</p>
	<ul>
	<c:forEach var="winner" items="${winners}">
		<li><a href="${winner.getValue() }"
			target="_blank">${winner.getKey() }</a></li>
			</c:forEach>

		
	</ul>
</body>
</html>