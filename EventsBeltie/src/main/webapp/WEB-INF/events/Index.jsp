<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dash-Board</title>
</head>
<body>
	<div class="wrapper">
		<h1>Welcome!</h1>
		<div class='registration'>
			<h2>Register</h2>
			<p><form:errors path="user.*"/></p>
		    <form:form method="POST" action="/registration" modelAttribute="user">
		        <p>
		        	<form:label path="fname">First Name:</form:label>
		        	<form:input path="fname"/>
	        	</p>
	        	<p>
	        		<form:label path="lname">Last Name:</form:label>
	        		<form:input path="lname"/>
        		</p>
		        <p>
		            <form:label path="email">Email:</form:label>
		            <form:input type="email" path="email"/>
		        </p>
		        <p>
		        	<form:label path="city">City</form:label>
		        	<form:input path="city"/>
	        	</p>
	        	<p>
	        		<form:select path="state">
						<form:option value="CA">CA</form:option>
						<form:option value="WA">WA</form:option>
						<form:option value="AZ">AZ</form:option>
						<form:option value="NV">NV</form:option>
						<form:option value="OR">OR</form:option>
					</form:select>
		        <p>
		            <form:label path="password">Password:</form:label>
		            <form:password path="password"/>
		        </p>
		        <p>
		            <form:label path="passconf">Password Confirmation:</form:label>
		            <form:password path="passconf"/>
		        </p>
		        <input type="submit" value="Register!"/>
		    </form:form>	
		</div>
		<div class="login">
			<h1>Login</h1>
		    <p><c:out value="${error}" /></p>
		    <form method="post" action="/login">
		        <p>
		            <label type="email" for="email">Email</label>
		            <input type="text" id="email" name="email"/>
		        </p>
		        <p>
		            <label for="password">Password</label>
		            <input type="password" id="password" name="password"/>
		        </p>
		        <input type="submit" value="Login!"/>
		    </form>    		
		</div>
	</div>
</body>
</html>
