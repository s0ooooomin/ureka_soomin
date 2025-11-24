package com.mycom.myapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.dto.BookDto;

@Mapper // 아래 interface가 mybatis에 의해 구현체가 만들어질 대상
public interface BookDao {
	List<BookDto> listBook();
	BookDto detailBook(int bookId);
	int insertBook(BookDto bookDto);
	int updateBook(BookDto bookDto);
	int deleteBook(int bookId);
}
