package com.mycom.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// POJO + @Controller <= url mapping 등 기존 controller servlet 과 같은 역할 수행
// DispatcherServlet이 Tomcat 시작되면 load -> Sptring Framework 기본 작업 수행
//	1. DI 대상 확인
//	2. @Controller의 @Getmapping 작업 (annotation확인) -> handler mapping 자료구조 구성 -> ...
//	3. View Resolver 자료구조 -> @Controller 의 특정 method 수행 -> Model 준비 -> 리턴값으로 jsp forwarding
@Controller
public class HelloController {

	@GetMapping()
	public String index() {
		System.out.println("/index 요청됨");
		return "index";
	}
	@GetMapping("/hello")
	public String hello() {
		System.out.println("/hello 요청됨");
		return "hello";
	}
	@GetMapping("/hello2")
	public void hello2() {
		System.out.println("/hello2 요청됨");
	}
	@GetMapping("/hello3")
	public String hello3() {
		System.out.println("/hello3 요청됨");
		return "hello3";

	}
}
