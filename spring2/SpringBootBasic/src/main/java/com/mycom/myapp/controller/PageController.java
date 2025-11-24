package com.mycom.myapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// Spring MVC <= 일반적으로 jsp를 사용하는 프로젝트를 의미
// JSP를 사용하지 않는 SpringBoot 프로젝트도 내부적으로는 MVC패턴사용 (DispatcherServlet거쳐감)
@Controller // 기본적으로 return을 static에서 찾아서 return 함 (페이지 넘어감)
public class PageController {
	
	@GetMapping("/")
	public String home() {
		System.out.println("/");
		return "home.html";
	}
	
	@GetMapping("/login")
	public String login() {
		System.out.println("/login");
		return "login.html";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println(username);
		System.out.println(password);
//		return "main.html";
		// POST 등 요청은 redirect로 처리
		return "redirect:main.html";
	}
	
	// Ajax 로그인 요청
	@GetMapping("/login2")
	public String login2() {
		System.out.println("/login2");
		return "login2.html";
	}
	@PostMapping("/login2")
	@ResponseBody
	public Map<String, String> login2(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println(username);
		System.out.println(password);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
	
	
}
