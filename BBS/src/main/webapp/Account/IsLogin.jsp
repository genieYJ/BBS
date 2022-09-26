<%@ page import="utils.JSFunction" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("UserID") == null) {
		JSFunction.alertLocation("로그인 후 이용해주세요", "../Account/Login.jsp", out);
		return;
	}
%>