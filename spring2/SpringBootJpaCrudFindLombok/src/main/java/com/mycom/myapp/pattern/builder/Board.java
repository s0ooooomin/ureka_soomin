package com.mycom.myapp.pattern.builder;

// Builder pattern (inner class 버전)
// 생성자와 setter를 통해 객체를 만들고 필드 설정
// 필드 설정 먼저 -> 실제 객체
public class Board {
	private final String title;
	private final String content;
	private final String category;
	
	// Board 객체 구성을 builder에서 가져옴
	public Board(Builder builder) {
		this.title = builder.title;
		this.content = builder.content;
		this.category = builder.category;	
	}
	
	// Builder
	public static class Builder {
		private String title;
		private String content;
		private String category;
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		public Builder content(String content) {
			this.content = content;
			return this;
		}
		public Builder category(String category) {
			this.category = category;
			return this;
		}
		// Board 객체에 내용을 담아서 넘김
		public Board build() {
			return new Board(this);
		}
	}


	@Override
	public String toString() {
		return "Board [title=" + title + ", content=" + content + ", category=" + category + "]";
	}
	
	
	
}
