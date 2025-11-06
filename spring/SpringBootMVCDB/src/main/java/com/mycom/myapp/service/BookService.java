package com.mycom.myapp.service;

import java.util.List;

import com.mycom.myapp.dto.BookDto;

public interface BookService {
	// BookDao 의 interface 그대로 복사
	List<BookDto> listBook();
	BookDto detailBook(int bookId);
	int insertBook(BookDto bookDto);
	int updateBook(BookDto bookDto);
	int deleteBook(int bookId);
}
