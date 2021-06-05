package common;

/*
웹 어플리케이션 제작시 설정값은 인터페이스형 상수를 활용하기도 한다. 
인터페이스에 정의된 상수는 public static final로 정의되므로
객체 생성없이 클래스명으로 가져다 쓸 수 있기 때문이다.
 */
public interface BoardConfig {	
	//게시판 목록에서 한페이지에 출력할 게시물의 갯수
	int PAGE_PER_SIZE = 2;
	//한 블럭당 출력할 페이지번호의 갯수 
	int PAGE_PER_BLOCK = 2; 
}
