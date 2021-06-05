package model1.board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

public class BoardDAO {
	
	//DAO의 기본 멤버변수
	Connection con; //DB연결
	Statement stmt; //정적쿼리 전송 및 실행
	PreparedStatement psmt; //쿼리전송 및 실행
	ResultSet rs; //select결과반환
	
	/*
	인자생성자1 : JSP에서 web.xml에 등록된 컨텍스트 초기화 파라미터를
		가져와서 생성자 호출 시 파라미터로 전달하여 DB에 연결한다.
	 */
	public BoardDAO(String driver, String url) {
		try {
			Class.forName(driver);
			String id = "kosmo_user";
			String pass = "1234";
			con = DriverManager.getConnection(url,id,pass);
				System.out.println("MariaDB 연결성공");
		}
		catch(Exception e) {
			System.out.println("MariaDB 연결시 예외발생");
		} 
		
	}
	 
	/*
	인자생성자2 : JSP에서 인수로 전달했던 초기화 파라미터를
		생성자 내에서 가져오기 위해 JSP에서는 application 내장객체를
		매개변수로 전달한다. 그러면 메소드 내에서 web.xml을 접근할 수 있다.
	 */
	public BoardDAO(ServletContext application) {
		try {
			String drv = application.getInitParameter("MariaJDBCDriver");
			String url = application.getInitParameter("MariaConnectURL");
			String id = application.getInitParameter("MariaUser");
			String pwd = application.getInitParameter("MariaPass");
			
			Class.forName(drv);
			con = DriverManager.getConnection(url,id,pwd);
				System.out.println("MariaDB 연결성공");
		}
		catch(Exception e) {
			System.out.println("MariaDB 연결시 예외발생");
		} 
		
	}
	
	//게시물의 갯수를 카운트
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		
		//count() 그룹함수를 통해 쿼리문 작성
		String query = "SELECT COUNT(*) FROM board ";
		//검색 파라미터가 있는 경우라면 where절을 추가한다.
		if(map.get("searchWord")!=null) {
			query += "WHERE "+ map.get("searchField") +" "
					+ " LIKE '%"+ map.get("searchWord") +"%'";
		}
		try {
			//Statement 객체를 생성
			stmt = con.createStatement();
			//쿼리문 실행 및 결과반환
			rs = stmt.executeQuery(query);
			//결과를 읽기위해 커서 이동
			rs.next();
			//count(*)를 통한 쿼리의 결과는 무조건 정수이므로 getInt()로 읽어옴
			totalCount = rs.getInt(1);
		}
		catch(Exception e) {
			System.out.println("게시물 카운트중 예외발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	//목록에 출력할 게시물을 조회하기 위한 메소드(Page처리 없음)
	public List<BoardDTO> selectList(Map<String, Object> map){
		
		//목록의 정렬순서를 보장하기 위해 List계열의 컬렉션을 사용한다.(set은 안된다!!)
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		
		//조회를 위한 쿼리문 작성
		String query = "SELECT * FROM board ";
		//검색어가 있는경우 where절 추가
		if(map.get("searchWord")!=null) {
			query += " WHERE "+ map.get("searchField") +" "
					+" LIKE '%"+ map.get("searchWord") +"%' ";
		}
		//목록은 항상 최근 게시물의 상단에 노출되므로 내림차순으로 정렬
		query += " ORDER BY num DESC";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			/*
			select한 결과를 반환하면 ResultSet객체를 통해 받는다.
			조회 결과는 몇개인지 알 수 없으므로 while문을 통해 갯수만큼
			반복하여 출력한다.
			 */
			while(rs.next()) {
				//결과셋 중 하나의 레코드를 저장하기 위해 DTO객체 생성
				BoardDTO dto = new BoardDTO();
				
				//레코드의 각 컬럼의 값을 읽어 setter를 통해 저장
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				//List컬렉션에 DTO객체 추가
				bbs.add(dto);
			}
		}
		catch(Exception e) {
			System.out.println("게시물 조회중 예외발생");
			e.printStackTrace();
		}
		
		return bbs;
	}
	
	//게시판 글쓰기 처리
	public int insertWrite(BoardDTO dto) {
		int result = 0;
		try {
			//인파라미터가 있는 insert 쿼리문 작성
			String query = "INSERT INTO board ( "
					+ " num,title,content,id,visitcount) "
					+ " VALUES ( "
					+ " seq_board_num.NEXTVAL, ?, ?, ?, 0)";
			//prepare객체 생성 후 인파라미터 설정
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getId());
			//쿼리문 실행
			result = psmt.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("게시물 입력중 예외발생");
			e.printStackTrace();
		}
		return result;
	}
	
	//게시물 조회하기
	public BoardDTO selectView(String num) {
		//조회한 하나의 레코드를 저장할 DTO객체 생성
		BoardDTO dto = new BoardDTO();
		
		//회원테이블과 게시판 테이블을 조인하여 조회함. 회원의 이름을 가져오기 위함
		String query = "SELECT B.*, M.name " +
				" FROM member M INNER JOIN board B " +
				" 	ON M.id=B.id " +
				" WHERE num=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			rs = psmt.executeQuery();
			/*
			매개변수로 전달된 일련번호를 통해 조회하므로
			결과는 무조건 1개만 나오게 된다. 따라서 if문으로
			반환된 결과가 있는지만 확인하면 된다.
			 */
			if(rs.next()) {
				dto.setNum(rs.getString(1));
				dto.setTitle(rs.getString(2));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString(6));
				dto.setName(rs.getString("name"));
			}
		}
		catch(Exception e) {
			System.out.println("게시물 상세보기중 예외발생");
			e.printStackTrace();
		}
		return dto;
	}
	
	//조회수 증가
	public void updateVisitCount(String num) {
		String query = "UPDATE board SET "
				+ " visitcount=visitcount+1 "
				+ " WHERE num=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, num);
			psmt.execute();
		}
		catch(Exception e) {
			System.out.println("게시물 조회수증가중 예외발생");
			e.printStackTrace();
		}
	}
	
	//게시물 수정 처리
	public int updateEdit(BoardDTO dto) {
		int result = 0;
		try {
			String query = "UPDATE board SET "
					+ " title=?, content=? "
					+ " WHERE num=?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getNum());
			result = psmt.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("게시물 수정중 예외발생");
			e.printStackTrace();
		}
		return result;
	}
	
	//게시물 삭제처리
	public int deletePost(BoardDTO dto) {
		int result = 0;
		try {
			String query = "DELETE FROM board WHERE num=?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getNum());
			result = psmt.executeUpdate();
		}
		catch(Exception e) {
			System.out.println("게시물 삭제중 예외발생");
			e.printStackTrace();
		}
		return result;
	}
	
	//게시판 목록 출력시 페이지 처리
	public List<BoardDTO> selectListPage(Map<String,Object>map){
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		/*
		목록의 페이지 처리를 위해 레코드의 구간을 between으로 정해
		조회함.
		1번 : board테이블의 게시물을 일련번호의 내림차순으로 정렬
		2번 : 1번의 조회결과에 rownum(순차적인 가상번호)를 부여함
		3번 : 2번의 조회결과를 between으로 구간을 정해 조회함
		※ 만약 게시판이 아닌 다른 테이블을 조회하고 싶다면 1번 쿼리문에서
		테이블명만 변경하면 된다.
		 */
		String query = " "
				+" SELECT * FROM ( "
				+" 	SELECT Tb.*, ROWNUM rNum FROM ( "
				+" 	  SELECT * FROM board ";
		if(map.get("searchWord")!=null) {
			query +=" WHERE "+ map.get("searchField") +" "
					+" LIKE '%"+ map.get("searchWord") +"%' ";
		}
		query += " "
				+"	ORDER BY num DESC "
				+"	) Tb "
				+" ) "
				+" WHERE rNum BETWEEN ? AND ?";
		System.out.println("페이지쿼리:"+query);
		System.out.println(query);
		
		try {
			psmt = con.prepareStatement(query);
			//between절의 start와 end값을 인파라미터 설정
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				bbs.add(dto);
			}
		}
		catch(Exception e) {
			System.out.println("게시물 조회중 예외발생");
			e.printStackTrace();
		}
		return bbs;
	}
	
	/*
	데이터베이스 연결을 해제할때 사용하는 메소드.
	 */
	public void close() {
		try {
			if(rs!=null) rs.close();
			if(psmt!=null) psmt.close();
			if(con!=null) con.close();
		}
		catch(Exception e) {
			System.out.println("Oracle 자원반납시 예외발생");
		}
	}
}
