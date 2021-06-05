<%@page import="model1.board.BoardDAO"%>
<%@page import="model1.board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!--  
글쓰기 페이지에서 오랫동안 머물러 세션이 소멸되는 경우가
발생할 수 있으므로, 쓰기 처리를 할때도 반드시
세션을 확인해야 한다.
-->
<%@ include file="./isLogin.jsp" %>
<%
request.setCharacterEncoding("UTF-8");
//폼값 받기
String title = request.getParameter("title");
String content = request.getParameter("content");

//폼값과 로그인 아이디를 저장하기 위한 DTO객체
BoardDTO dto = new BoardDTO();
dto.setTitle(title);
dto.setContent(content);

//session영역에 저장된 아이디는 String으로 변경 후 사용해야 한다.
dto.setId(session.getAttribute("USER_ID").toString());

//DAO객체 생성 및 쓰기처리를 위한 메소드 호출
BoardDAO dao = new BoardDAO(application);

/* 여러개의 게시물을 한꺼번에 입력할 때 사용하세요.
int iResult = 0;
for(int i=1 ; i <=100 ; i++){
	dto.setTitle(i+"번째 " +title);
	iResult = dao.insertWrite(dto);
}
*/

int iResult = dao.insertWrite(dto);
dao.close();
if(iResult==1){
	response.sendRedirect("ListSimple.jsp");
}
else{
	JSFunction.alertBack("글쓰기에 실패하였습니다.", out);
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