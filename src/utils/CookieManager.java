package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {

	//쿠키를 생성할때 사용
	public static void makeCookie(HttpServletResponse response,
			String cName, String cValue, int cTime) {
		Cookie cookie = new Cookie(cName, cValue); //생성자를 통해 쿠키생성
		cookie.setPath("/"); //적용될 경로 지정
		cookie.setMaxAge(cTime); //쿠키의 유효시간을 초 단위로 설정
		response.addCookie(cookie); //응답헤더를 통해 사용자에게 쿠키 전송
	}
	
	//쿠키를 읽을때 사용
	public static String readCookie(HttpServletRequest request,
			String cName) {
		Cookie[] cookies = request.getCookies(); //생성된 모든 쿠키를 읽어온다.
		String getValue = "";
		if(cookies!=null) {
			for(Cookie c : cookies) { //쿠키의 갯수만큼 루프
				String cookieName = c.getName(); //쿠키의 이름
				String cookieValue = c.getValue(); //쿠키에 설정된 값
				//사용자가 읽고자 하는 쿠키명과 비교후 해당 값을 반환함
				if(cookieName.equals(cName)) {
					getValue = cookieValue;
				}
			}
		}
		return getValue;
	}
	//쿠키 삭제
	public static void deleteCookie(HttpServletResponse response,
			String cName) {
		/*
		 쿠키는 삭제하기 위한 메소드가 별도로 존재하지 않는다.
		 쿠키값을 빈값으로 만들고, 유혀시간을 0으로 설정하는것으로
		 쿠키는 삭제된다. 
		 */
		makeCookie(response, cName, "", 0);
	}
}
