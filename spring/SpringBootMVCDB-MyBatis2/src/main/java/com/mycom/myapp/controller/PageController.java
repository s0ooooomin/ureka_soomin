package com.mycom.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


// jsp를 통한 html 페이지 처리를 담당
@Controller
public class PageController {
	
	@GetMapping("/emps")
	public String emps() {
		return "emps";
	}
	
	@GetMapping("/salaries")
	public String salaries() {
		return "salaries";
	}
	
	@GetMapping("/stores")
	public String stores() {
		return "stores";
	}
	
	
	
}
