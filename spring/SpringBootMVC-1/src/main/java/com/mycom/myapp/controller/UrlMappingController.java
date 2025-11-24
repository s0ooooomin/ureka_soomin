package com.mycom.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UrlMappingController {

	@RequestMapping ( value="/m1", method=RequestMethod.GET)
	public void m1() {
		System.out.println("/m1");
	}
	
	@GetMapping("/m2")
	public void m2() {
		System.out.println("/get");
	}
	@PostMapping("/post")
	public void m3() {
		System.out.println("/post");
	}
	@PutMapping("/put")
	public void m4() {
		System.out.println("/put");
	}
	@DeleteMapping("/delete")
	public void m5() {
		System.out.println("/delete");
	}
	
	// Path Variable
	@GetMapping("/books/{bookId}")
	public void m6(@PathVariable("bookId") int bookId){
		System.out.println("/books");
	}

	// url : books/10/20
	@GetMapping("/books/{limit}/{offset}")
	public void m7(@PathVariable("limit") int limit, @PathVariable("offset") int offset) {
		System.out.println("/books");
		System.out.println(offset);
	}
	
//	@GetMapping("/books/{abc}")
//	public void m8(@PathVariable("abc") int abc) {
//		System.out.println("/books");
//		System.out.println(abc);
//	}
	
	@GetMapping(value={"/url1", "/url2"})
	public void m8() {
		System.out.println("/url1, /url2");
	}
	
	// sub url 포함 #1
	@PostMapping("/sub1/*")
	public void m9() {
		System.out.println("/sub1");
	}
	
	// sub url 포함 #2
	@PostMapping("/sub2/**")
	public void m10() {
		System.out.println("/sub1");
	}
	
	

}
