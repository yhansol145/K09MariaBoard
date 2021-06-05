<%@page import="model.MemberDTO"%>
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
MemberDTO memberDTO = dao.getMemberDTO(id, pw);
/*
	회원정보를 조회한 후 인증에 성공하면 DTO객체에 정보를
	저장한 후 반환하므로, 로그인 페이지에서 회원이름을
	출력할 수 있다.
*/
if(memberDTO.getId() != null){
	//로그인에 성공한 경우 session영역에 회원인증정보를 저장한다.
	session.setAttribute("USER_ID", memberDTO.getId());
	session.setAttribute("USER_PW", memberDTO.getPass());
	session.setAttribute("USER_NAME", memberDTO.getName());
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