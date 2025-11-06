package com.mycom.myapp.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycom.myapp.dto.CarDto;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ParamController {
	@GetMapping("/param1")
	public void m1(HttpServletRequest request) { // request 객체를 직접 받아 처리
		System.out.println(request.getParameter("bookId"));
		System.out.println(request.getParameter("bookName"));
	}
	@GetMapping("/param2")
	public void m2(String bookId) { // 파라미터 변수화
		System.out.println(bookId);
	}
	@GetMapping("/param3")
	public void m3(Integer bookId, String bookName) { // int -> Integer (null 처리 가능)
		System.out.println(bookId);
		System.out.println(bookName);
	}
	
	@PostMapping("/param4")
	public void m4(@RequestParam(required = false) String bookName) { // RequestParam 은 필요한 파라미터임을 알리는것
		System.out.println(bookName);
	}
	@PostMapping("/param5")
	public void m5(@RequestParam(name="bookName2") String bookName) { // bookName2로 넘어오는 파라미터들을 bookName으로 바꿔줌
		System.out.println(bookName);
	}

	// dto
	// 기본 생성자 호출 -> getter&setter 이용해서 dto 객체의 필드 변경
	@PostMapping("/car")
	public void m6(CarDto carDto) { 
		System.out.println(carDto);
	}
	@PostMapping("/car2")
	public void m7(CarDto carDto) { 
		System.out.println(carDto);
	}

	// Dto는 field 변수 고정
	// Map은 가변적 parameter
	@PostMapping("/map")
	public void m8(@RequestParam Map<String, String> params) { 
		System.out.println(params.get("abc"));
		System.out.println(params.get("def"));
		System.out.println(params.get("xyz"));
	}
	
	@GetMapping("/header")
	public void m9 (
		@RequestHeader(value="User-Agent") String userAgent,	
		@RequestHeader(value="Accept") String accept,
		@RequestHeader(value="API-KEY") String apiKey
	) {
		System.out.println(userAgent);
		System.out.println(accept);
		System.out.println(apiKey);
	}
	
	
	
}
