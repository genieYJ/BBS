<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
session.removeAttribute("UserID");
session.removeAttribute("userName");

session.invalidate();

response.sendRedirect("Login.jsp");
%>