package com.mycom.myapp.pattern.builder;

public class Test {

	public static void main(String[] args) {

		// 생성자와 setter 를 사용하지 X -> builder 패턴 사용해 객체 생성, 필드 설정
		Book book = Book.builder()
								.isbn("123")
								.title("도서제목")
								.author("홍길동")
								.description("도서설명")
								.price(17000);
		System.out.println(book);
		
		Board board = new Board.Builder()
								.title("게시판")
								.content("글내용")
								.category("IT")
								.build();
		System.out.println(board);
	}

}
