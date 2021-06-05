<%@page import="model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//폼값으로 전송된 파라미터를 받아옴
String id = request.getParameter("user_id");
String pw = request.getParameter("user_pw");

//web.xml의 컨텍스트 초기화 파라미터 읽어옴
String drv = application.getInitParameter("JDBCDriver");
String url = application.getInitParameter("ConnectionURL");

//DAO객체 생성 및 DB연결
MemberDAO dao = new MemberDAO(drv, url);

//폼값으로 받은 아이디, 패스워드를 통해 로그인 처리 메소드 호출
boolean isMember = dao.isMember(id, pw);

/*
	해당 메소드는 count()를 사용하므로 로그인 시 사용한
	아이디, 패스워드 외의 정보는 추가로 얻어올 수 없다.
	단지 회원 존재 유무만 확인할 수 있다.
*/
if(isMember==true){
	//로그인에 성공한 경우 session영역에 회원인증정보를 저장한다.
	session.setAttribute("USER_ID", id);
	session.setAttribute("USER_PW", pw);
	//로그인 페이지로 이동
	response.sendRedirect("Login.jsp");
}
else{
	//로그인 실패시 request영역에 속성을 저장한다.
	request.setAttribute("ERROR_MSG", "넌 회원이 아니시군 꺼지거라");
	//로그인 페이지로 포워드(전달) 한다.
	request.getRequestDispatcher("Login.jsp").forward(request, response);
}
%>