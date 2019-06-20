<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<div class="header">
<a href="<%=request.getContextPath()%>/servleti/main">Home</a>
<div>
<c:choose>
	<c:when test="${sessionScope['current.user.id'] == null}">
	Not logged in
	</c:when>
	<c:otherwise>
	${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}
	(<a href="<%=request.getContextPath()%>/servleti/logout">logout</a>)
	</c:otherwise>
</c:choose>
</div>
</div>