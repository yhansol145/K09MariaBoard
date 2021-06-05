package utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

public class JSFunction {
	
	//JS를 통해 경고창을 띄우고 페이지 이동한다.  
	public static void alertLocation(String msg, String url, JspWriter out) {
		try {
			String str = ""
					+ "<script>"
					+ "  alert('"+msg+"');  "
					+ "  location.href='"+url+"';  "
					+ "</script>";
			out.println(str);
		}
		catch(Exception e) {}
	}
	//경고창을 띄우고 뒤로 이동한다. 
	public static void alertBack(String msg, JspWriter out) {
		try {
			String str = ""
					+ "<script>"
					+ "  alert('"+msg+"');  "
					+ "  history.back(); "
					+ "</script>";
			out.println(str);// history.go(-1) 을 사용해도 된다. 
		}
		catch(Exception e) {}
	}
	 
	
	/*
	서블릿에서 Javascript를 출력하기 위한 메소드
	 */
	public static void alertLocation(HttpServletResponse resp, String msg, String url) {
		String str = "";
		try {
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = resp.getWriter();	
			str = "<script>"
					+ "  alert('"+msg+"');  "
					+ "  location.href='"+url+"';  "
					+ "</script>";			
			writer.print(str);
		}
		catch(Exception e) {}		
	}	
	public static void alertBack(HttpServletResponse resp, String msg) {
		String str = "";
		try {
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = resp.getWriter();	
			str = "<script>"
					+ "  alert('"+msg+"');  "
					+ "  history.back(); "
					+ "</script>";
			writer.print(str);
		}
		catch(Exception e) {}
	} 
	
//	public static void alertOpenerReloadClose(HttpServletResponse resp, String msg) {
//		String str = "";
//		try {
//			resp.setContentType("text/html;charset=UTF-8");
//		}
//	}
}

