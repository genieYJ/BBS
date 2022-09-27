<%@ page import="board.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include  file="../Account/IsLogin.jsp"%>
<%

request.setCharacterEncoding("utf-8");

String num = request.getParameter("num");

BoardDTO dto = new BoardDTO();
BoardDAO dao = new BoardDAO(application);
dto = dao.selectView(num);

String sessionID = session.getAttribute("UserID").toString();

int delResult = 0;

if (sessionID.equals(dto.getId())) {
	dto.setNum(num);
	delResult = dao.deletePost(dto);
	dao.close();
	
	if (delResult == 1) {
		JSFunction.alertLocation("삭제되었습니다", "List.jsp", out);
	} else {
		JSFunction.alertBack("삭제를 실패했습니다", out);
	}
} else {
	JSFunction.alertBack("본인만 삭제할 수 있습니다", out);
	
	return;
}
%>