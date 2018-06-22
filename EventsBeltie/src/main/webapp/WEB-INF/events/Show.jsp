<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Show Event</title>
</head>
<body>
	<div class="wrapper">
		<a href="/dashboard">Go Back</a>
		<div class="main">
			<h1><c:out value="${edata.name}"/></h1>
			<h3>Host: <c:out value="${edata.host.fname}"/> <c:out value="${edata.host.lname}"/></h3>
			<h3>Date: <fmt:formatDate value="${edata.eventDate}" pattern="MM/dd/yyy"/></h3>
			<h3>Location: <c:out value="${edata.location}"/> <c:out value="${edata.eventstate}"/></h3>
			<div class="people">
				<h3>people query here</h3>
				<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Location</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ujoin}" var="u">
						<tr>
							<td><c:out value="${u.fname}"/> <c:out value="${u.lname}"/></td>
							<td><c:out value="${u.city}"/></td>
						</tr>
					</c:forEach>
				</tbody>
				</table>
			</div>
		</div>
		<div class="comments">
			<div>
				<c:forEach items="${edata.comments}" var="c">
					<h5><c:out value="${c.message}"/></h5>
				</c:forEach>				
			</div>
			<h4>Add a comment</h4>
			<form:form action="/events/${edata.id}" method="POST" modelAttribute="comment">
				<h4>
					<form:textarea path="message"/>
				</h4>
				<h5><form:errors path="message"/></h5>
				<input type="submit" value="Add Comment">
			</form:form>
		</div>
	</div>
</body>
</html>
