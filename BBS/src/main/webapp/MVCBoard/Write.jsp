<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
<script>
function validForm(form) {
	if (form.name.value == "") {
		alert("작성자를 입력하세요");
		form.name.focus();
		return false;
	}
	if (form.title.value == "") {
		alert("제목을 입력하세요");
		form.title.focus();
		return false;
	}
	if (form.content.value == "") {
		alert("내용을 입력하세요");
		form.content.focus();
		return false;
	}
	if (form.pass.value == "") {
		alert("비밀번호를 입력하세요");
		form.pass.focus();
		return false;
	}
}
</script>
</head>
<body>
	<h2>파일 첨부형 게시판 - 글쓰기(Write)</h2>
	<form name="writeFrm" method="post" enctype="multipart/form-data" 
	action="../MVCBoard/Write.do" onsubmit="return validForm(this);">
		<table border="1" width="90%">
			<tr>
				<td><label for="name">작성자</label></td>
				<td><input type="text" name="name" id="name" style="width:150px;"></td>
			</tr>
			<tr>
				<td><label for="title">제목</label></td>
				<td><input type="text" name="title" id="title" style="width:90%;"></td>
			</tr>
			<tr>
				<td><label for="content">내용</label></td>
				<td><textarea name="content" id="content" style="width:90%;height:100px;"></textarea></td>
			</tr>
			<tr>
				<td><label for="ofile">첨부파일</label></td>
				<td><input type="file" name="ofile" id="ofile"></td>
			</tr>
			<tr>
				<td><label for="pass">비밀번호</label></td>
				<td><input type="password" name="pass" id="pass" style="width:100px;"></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">작성 완료</button>
					<button type="reset">RESET</button>
					<button type="button" onclick="location.href='../MVCBoard/List.do';">목록 바로가기</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>