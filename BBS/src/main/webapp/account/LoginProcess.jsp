<%@ page import="membership.MemberDTO" %>
<%@ page import="membership.MemberDAO" %>
<%@ page import="utils.CookieManger" %>
<%@ page import="utils.JSFunction" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
String userID = request.getParameter("user_id");
String userPW = request.getParameter("user_pw");
String save_check = request.getParameter("save_check");

String oracleDriver = application.getInitParameter("OracleDriver");
String oracleURL = application.getInitParameter("OracleURL");
String oracleId = application.getInitParameter("OracleId");
String oraclePwd = application.getInitParameter("OraclePwd");

MemberDAO dao = new MemberDAO(oracleDriver, oracleURL, oracleId, oraclePwd);
MemberDTO memberDTO = dao.getMemberDTO(userID, userPW);
dao.close();

if (memberDTO.getId() != null) {
	session.setAttribute("UserID", memberDTO.getId());
	session.setAttribute("UserName", memberDTO.getName());
	if (save_check != null && save_check.equals("Y")) {
		CookieManger.makeCookie(response, "loginID", userID, 86400);
	} else {
		CookieManger.deleteCookie(response, "loginID");
	}
	response.sendRedirect("Login.jsp");
} else {
	request.setAttribute("LoginErrMsg", "로그인 오류입니다");
	request.getRequestDispatcher("Login.jsp").forward(request, response);
}
%>