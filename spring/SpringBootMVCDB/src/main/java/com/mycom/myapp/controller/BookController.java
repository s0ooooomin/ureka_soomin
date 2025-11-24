package com.mycom.myapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycom.myapp.dto.BookDto;
import com.mycom.myapp.service.BookService;

// index.html -> books.jsp 이동 처리 (html 페이지 요청)
// books.jsp 이후 데이터
@Controller
public class BookController {

	@Autowired
	BookService bookService;
	
	// books.jsp 페이지 이동하기
	@GetMapping("/books")
	public String bookMain() {
		System.out.println("/books");
		return "books";
	}
	
	// 이동 후
	
	// Get
	// 도서목록 data
	@GetMapping("/books/list")
	@ResponseBody
	public List<BookDto> listBook() {
		System.out.println("/books/list");
		List<BookDto> bookList = bookService.listBook();
//		return "books"; // 이렇게 하면 또 books.jsp 로 감
		return bookList;
	}
	// 도서상세 data
	@GetMapping("/books/detail/{bookId}")
	@ResponseBody
	public BookDto detailBook(@PathVariable Integer bookId) {
		System.out.println("/books/detail");
		BookDto bookDto = bookService.detailBook(bookId);
		return bookDto;
	}
	
	// Post
	// 도서등록
	@PostMapping("/books/insert")
	@ResponseBody
	public Map<String, String> insertBook(BookDto bookDto) {
		int ret = bookService.insertBook(bookDto);
		Map<String, String> map = new HashMap<>();
		if (ret == 1) {
			map.put("result", "success");
		}else {
			map.put("result", "fail");
		}
		return map;
	}
	// 도서수정
	@PostMapping("/books/update")
	@ResponseBody
	public Map<String, String> updateBook(BookDto bookDto) {
		int ret = bookService.updateBook(bookDto);
		Map<String, String> map = new HashMap<>();
		if (ret == 1) {
			map.put("result", "success");
		}else {
			map.put("result", "fail");
		}
		return map;
	}
	// 도서삭제
	@GetMapping("/books/delete/{bookId}")
	@ResponseBody
	public Map<String, String> deleteBook(@PathVariable int bookId) {
		int ret = bookService.deleteBook(bookId);
		Map<String, String> map = new HashMap<>();
		if (ret == 1) {
			map.put("result", "success");
		}else {
			map.put("result", "fail");
		}
		return map;
	}
}
