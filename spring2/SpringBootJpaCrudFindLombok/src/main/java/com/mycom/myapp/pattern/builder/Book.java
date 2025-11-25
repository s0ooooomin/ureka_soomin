package com.mycom.myapp.pattern.builder;

// Builder pattern
// 생성자와 setter를 통해 객체를 만들고 필드 설정
public class Book {
	private String isbn;
	private String title;
	private String author;
	private String description;
	private int price;

	private Book() {}
	
	// public 생성자 대체
	public static Book builder() {
		return new Book();
	}
	
	// public setter 대체
	public Book isbn(String isbn) {
		this.isbn = isbn;
		return this;
	}
	public Book title(String title) {
		this.title = title;
		return this;
	}
	public Book author(String author) {
		this.author = author;
		return this;
	}
	public Book description(String description) {
		this.description = description;
		return this;
	}
	public Book price(int price) {
		this.price = price;
		return this;
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", author=" + author + ", description=" + description
				+ ", price=" + price + "]";
	}
	
	
}
