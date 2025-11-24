package com.mycom.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	@GetMapping("/orders")
	public String orderMain() {
		System.out.println("orders 로 갑니다");
		return "orders";
	}
	@GetMapping("/movies")
	public String movieMain() {
		System.out.println("movies 로 갑니다");
		return "movies";
	}
	@GetMapping("/customers")
	public String customerMain() {
		System.out.println("customers 로 갑니다");
		return "customers";
	}
//	@GetMapping("/sales")
//	public String saleMain() {
//		System.out.println("sales 로 갑니다");
//		return "sales";
//	}


}
