<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dash-Board</title>
</head>
<body>
	<div class="wrapper">
		<h1>Hello <c:out value="${user.fname}"/> <c:out value="${user.lname}"/></h1>
		<a href="/logout">Logout</a>
		<div class="events">
			<h5>Hear are some of the events in your state:</h5>
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Date</th>
						<th>Location</th>
						<th>Host</th>
						<th>Action/Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${excl}" var="e">
						<tr>
							<td><a href="/events/${e.id}"><c:out value="${e.name}"/></a></td>
							<td><fmt:formatDate value="${e.eventDate}" pattern="MM/dd/yyy"/></td>
							<td><c:out value="${e.location}"/></td>
							<td><c:out value="${e.host.fname}"/></td>
							<td><a href="/events/${e.id}/join">Join</a></td>
						</tr>
					</c:forEach>
					<c:forEach items="${join}" var="j">
						<tr>
							<td><a href="/events/${j.id}"><c:out value="${j.name}"/></a></td>
							<td><fmt:formatDate value="${j.eventDate}" pattern="MM/dd/yyyy"/></td>
							<td><c:out value="${j.location}"/></td>
							<td><c:out value="${j.host.fname}"/></td>
							<td>
								Joining 
								<form action="/events/cancel/${j.id}" method="POST">
									<input type="hidden" name="_method" value="delete">
							    	<input type="submit" value="Cancel">
						    	</form>
							</td>
						</tr>
					</c:forEach>
					<c:forEach items="${host}" var="h">
						<tr>
							<td><a href="/events/${h.id}"><c:out value="${h.name}"/></a></td>
							<td><fmt:formatDate value="${h.eventDate}" pattern="MM/dd/yyy"/></td>
							<td><c:out value="${h.location}"/></td>
							<td><c:out value="${h.host.fname}"/></td>
							<td>
								<a href="/events/${h.id}/edit"><button>Edit</button></a> 
								<form action="/events/${h.id}" method="POST">
									<input type="hidden" name="_method" value="delete">
							    	<input type="submit" value="Delete">
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="ofs">
			<h5>Hear are some of the events Not in your state:</h5>
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Date</th>
						<th>Location</th>
						<th>Host</th>
						<th>Action/Status</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${oexcl}" var="oe">
						<tr>
							<td><a href="/events/${oe.id}"><c:out value="${oe.name}"/></a></td>
							<td><fmt:formatDate value="${oe.eventDate}" pattern="MM/dd/yyy"/></td>
							<td><c:out value="${oe.location}"/></td>
							<td><c:out value="${oe.host.fname}"/></td>
							<td><a href="/events/${oe.id}/join">Join</a></td>
						</tr>
					</c:forEach>
					<c:forEach items="${ojoin}" var="oj">
						<tr>
							<td><a href="/events/${oj.id}"><c:out value="${oj.name}"/></a></td>
							<td><fmt:formatDate value="${oj.eventDate}" pattern="MM/dd/yyy"/></td>
							<td><c:out value="${oj.location}"/></td>
							<td><c:out value="${oj.host.fname}"/></td>
							<td>
								Joining 
								<form action="/events/cancel/${oj.id}" method="POST">
									<input type="hidden" name="_method" value="delete">
							    	<input type="submit" value="Cancel">
						    	</form>
							</td>
						</tr>
					</c:forEach>
					<c:forEach items="${ohost}" var="oh">
						<tr>
							<td><a href="/events/${oh.id}"><c:out value="${oh.name}"/></a></td>
							<td><fmt:formatDate value="${oh.eventDate}" pattern="MM/dd/yyy"/></td>
							<td><c:out value="${oh.location}"/></td>
							<td><c:out value="${oh.host.fname}"/></td>
							<td>
								<a href="/events/${oh.id}/edit"><button>Edit</button></a> 
								<form action="/events/${oh.id}" method="POST">
									<input type="hidden" name="_method" value="delete">
							    	<input type="submit" value="Delete">
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="form">
			<h1>Create an Event</h1>
			<h3><c:out value="${error}"/></h3>
			<form:form action="/event/new" method="POST" modelAttribute="event">
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
				<input type="submit" value="Create">
			</form:form>
		</div>
	</div>
</body>
</html>
