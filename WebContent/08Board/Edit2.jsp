<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./isLogin.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원제 게시판</title>
<script type="text/javascript">
	
	//쓰기 폼에 빈값이 있는지를 확인해주는 함수
	function formValidate(f){
		if(f.title.value==""){
			alert("제목을 입력하세요");
			f.title.focus();
			return false;
		}
		if(f.content.value==""){
			alert("내용을 입력하세요");
			f.content.focus();
			return false;
		}
	}
</script>
</head>
<body>
	<h2>회원제 게시판 - 수정하기(Edit)</h2>
	<form name="writeFrm" method="post" action="EditProcess.jsp"
		onsubmit="return formValidate(this);">
		
	<input type="hidden" name="num" value="수정할 게시물의 일련번호" />
	<!-- 
		게시판 테이블인 board의 컬럼명과 input태그의 name속성값은
		똑같이 맞춰주는것이 개발에 유리하다.
	-->
	<table border="1" width="90%">
		<tr>
			<td>제목</td>
			<td>
				<input type="text" name="title" style="width:90%;" />
			</td>
		</tr>
		
		<tr>
			<td>내용</td>
			<td>
				<textarea name="content" style="width:90%; height:100px;"></textarea>
			</td>
		</tr>
		
		<tr>
			<td colspan="2" align="center">
				<button type="submit">작성완료</button>
				<button type="reset">RESET</button>
				<button type="button" onclick="location.href='list.jsp';">
					리스트바로가기
				</button>
			</td>
		</tr>
	</table>
		</form>
</body>
</html>