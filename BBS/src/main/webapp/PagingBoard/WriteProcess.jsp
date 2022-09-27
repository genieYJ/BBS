<%@ page import="board.BoardDAO" %>
<%@ page import="board.BoardDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../Account/IsLogin.jsp" %>
<%
request.setCharacterEncoding("utf-8");

String title = request.getParameter("title");
String content = request.getParameter("content");
	
BoardDTO dto = new BoardDTO();
dto.setTitle(title);
dto.setContent(content);
dto.setId(session.getAttribute("UserID").toString());
	
BoardDAO dao = new BoardDAO(application);
int iResult = dao.inserWrite(dto);
dao.close();

if (iResult == 1) {
	response.sendRedirect("List.jsp");
} else {
	JSFunction.alertBack("글쓰기에 실패했습니다", out);
}
%>