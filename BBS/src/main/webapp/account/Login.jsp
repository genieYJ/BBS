<%@ page import="utils.CookieManger" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String loginID = CookieManger.readCookie(request, "loginID");
String cookieCheck = "";
if (!loginID.equals("")) {
	cookieCheck = "checked";
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Save Login ID</title>
</head>
<body>
	<h2>Login</h2>
	<form action="IdSaveProcess.jsp" method="post">
		<label>ID</label>
		<input type="text" name="user_id" value="<%= loginID %>">
		<br>
		<label>Password</label>
		<input type="password" name="user_pw">
		<br>
		<input type="checkbox" name="save_check" id="save_check" value="Y" <%= cookieCheck %>>
		<label for="save_check">Remember ID</label>
		<br>
		<input type="submit" value="Login">
	</form>
</body>
</html>