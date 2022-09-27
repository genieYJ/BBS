<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FileUpload</title>
</head>
<script>
	function validForm(form) {
		if(form.name.value == "") {
			alert("작성자를 입력하세요");
			form.name.focus();
			return false;
		}
		if(form.title.value == "") {
			alert("제목을 입력하세요");
			form.title.foucs();
			return false;
		}
		if(form.attachedFile.value == "") {
			alert("첨부파일은 필수 입력입니다");
			retuen false;
		}
	}
</script>
<body>
	<h3>파일 업로드</h3>
	<span style="color: red;">${errMsg }</span>
	<form name="fileForm" method="post" enctype="multipart/form-data" action="UploadProcess.jsp" onsubmit="return validForm(this);">
		<label for="name">작성자</label>
		<input type="text" name="name" id="name" value="admin">
		<br>
		<label for="title">제목</label>
		<input type="text" name="title" id="title">
		<br>
		<label for="cate">카테고리(선택사항)</label>
		<input type="checkbox" name="cate" value="사진" checked="checked">사진
		<input type="checkbox" name="cate" value="과제">과제
		<input type="checkbox" name="cate" value="워드">워드
		<input type="checkbox" name="cate" value="음원">음원
		<br>
		<label for="attachedFile">첨부파일</label>
		<input type="file" name="attachedFile" id="attachedFile">
		<br>
		<input type="submit" value="전송하기">
	</form>
</body>
</html>