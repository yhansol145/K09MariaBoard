<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="../common/CommonLink.jsp" %>

<h2>로그인 페이지</h2>

<!-- 
	로그인에 실패한 경우 request영역에 속성을 저장한 후
	로그인 페이지로 포워드 하면 그 영역이 공유되어 아래
	메세지가 출력된다.
-->
<span style="color:red; font-size:1.5em;">		
	<%=request.getAttribute("ERROR_MSG")==null ?
		"" : request.getAttribute("ERROR_MSG") %>
</span>


<%
/*
session영역에 회원인증 정보가 없다면 로그아웃 상태이므로
로그인 폼을 출력한다.
*/
if(session.getAttribute("USER_ID")==null){
%>
	<script>
	//로그인 폼의 입력값을 검증하는 JS함수 정의
	function loginValidate(fn){
		if(!fn.user_id.value){
			alert("아이디를 입력하세요");
			fn.user_id.focus();
			return false;
		}
		if(fn.user_pw.value==""){
			alert("패스워드를 입력하세요");
			fn.user_pw.focus();
			return false;
		}
	}
	</script>
	
	<!-- 
		로그인 1단계 : LoginProcess.jsp
		로그인 2단계 : LoginProcessDTO.jsp
		로그인 3단계 : LoginProcessMap.jsp
	-->
	<form action="LoginProcessMap.jsp" method="post" name="loginFrm"
		onsubmit="return loginValidate(this);">
	<table border="1">
		<tr>
			<td>아이디</td>
			<td> 
				<input type="text" name="user_id" tabindex="1" />
			</td>
		</tr>
		<tr>
			<td>패스워드</td>
			<td>
				<input type="password" name="user_pw" tabindex="2" />
			</td>			
		</tr>
		<tr>
			<td colspan="2" style="text-align:center;">
				<input type="submit" value="로그인하기" tabindex="4" />
			</td>			
		</tr>
	</table>		
	</form>
<% }else{ 
//세션영역에 회원인증 정보가 있을때이므로 로그인이 된 상태
%>
	<!-- 로그인에 성공했을때 출력되는 화면 -->
	<table border='1'>
		<tr>
			<td style="text-align:center;">
				<%=session.getAttribute("USER_NAME") %> 회원님, 
					로그인 하셨습니다.
				<br />
				즐거운 시간 보내세요 ^^*
				<br />
				<a href="Logout.jsp">[로그아웃]</a>
			</td>
		</tr>
	</table>
<% } %>
	
</body>
</html>