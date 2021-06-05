<%@page import="utils.JSFunction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	/*
	session 영역에 회원인증 정보가 저장되어 있는지 확인한다.
	만약 로그인에 성공 했다면 USER_ID라는 속성값이 저장되어 있을것이다.
	*/
	if(session.getAttribute("USER_ID")==null){
		//로그인 정보가 없다면 로그인 페이지로 이동한다.
		JSFunction.alertLocation("로그인 후 이용해주십시오.",
				"../06Session/Login.jsp", out);
		
		/*
		Javascript와 JSP가 같이 있으면 JSP코드가 우선적으로 실행되므로
		반드시 return을 통해 실행을 중지해야 한다.
		*/
		return;
	}
%>  
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>