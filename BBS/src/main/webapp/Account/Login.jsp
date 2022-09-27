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
	<span style="color: red;font-size: 1.2rem;">
		<%= request.getAttribute("LoginErrMsg") == null
			? "" : request.getAttribute("LoginErrMsg")
		%>
	</span>
	<% if (session.getAttribute("UserID") == null) { %>
	<script>
		function validForm(form) {
			if (!form.user_id.value) {
				alert("아이디를 입력하세요");
				return false;
			}
			if (!form.user_pw.value) {
				alert("비밀번호를 입력하세요");
				return false;
			}
		}
	</script>
	<form action="LoginProcess.jsp" method="post" name="loginFrm" onsubmit="return validForm(this);">
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
	<% } else { %>
	<%= session.getAttribute("UserName") %> 회원님, 로그인 하셨습니다. <br>
	<a href="../Board/List.jsp">[Board]</a>
	<a href="Logout.jsp">[Logout]</a>
	<% } %>
</body>
</html>