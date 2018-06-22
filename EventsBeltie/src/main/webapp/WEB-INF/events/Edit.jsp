<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="wrapper">
		<h1><c:out value="${event.name}"/></h1>
		<div class="form">
			<h2>Edit Event</h2>
			<h3><c:out value="${error}"/></h3>
			<form:form action="/events/${event.id}/edit" method="POST" modelAttribute="event">
				<input type="hidden" name="_method" value="put">
				<form:hidden path="id" />
				<form:hidden path="users" />
				<h4>
					<form:label path="name">Name</form:label>
					<form:input path="name"/>
				</h4>
				<h5><form:errors path="name"/></h5>
				<h4>
					<label>Event Date</label>
					<input type="date" name="eventdate"/>
				</h4>
				<h4>
					<form:label path="location">Location</form:label>
					<form:input path="location"/>
				</h4>
				<h5><form:errors path="location"/></h5>
				<h4>
					<form:select path="eventstate">
						<form:option value="CA">CA</form:option>
						<form:option value="WA">WA</form:option>
						<form:option value="AZ">AZ</form:option>
						<form:option value="NV">NV</form:option>
						<form:option value="OR">OR</form:option>
					</form:select>
				</h4>
				<input type="submit" value="Edit">
			</form:form>
		</div>
	</div>
</body>
</html>
