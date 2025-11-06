package com.mycom.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class SessionController {
	// 원래 login은 post가 기본
	// 지금은 browser 테스트를 위해 get으로 처리
	@GetMapping("/login")
	public String login(String username, String password, HttpSession session) {
		
		if ("dskim".equals(username) && "1234".equals(password)) {
			// 맞으면 인증된 사용자 O
			session.setAttribute("username", username);
		}
		
		return "sessionTest1";
	}
	
	@GetMapping("/doSomething")
	public String doSomething() {
		return "sessionTest2";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "sessionTest3";
	}
}
