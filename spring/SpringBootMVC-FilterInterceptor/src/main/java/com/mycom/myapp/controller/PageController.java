package com.mycom.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

// jsp 사용X html O (webapp 이런 거 x resources o)
@Controller
public class PageController {

	@GetMapping("/admin")
	public String admin(HttpSession session) {
		session.setAttribute("login", "fail");
		return "/admin/admin.html";
	}
	@GetMapping("/no-login")
	public String noLogin() {
		return "/no-login.html";
	}
	@GetMapping("/login")
	public String login(HttpSession session) {
		session.setAttribute("login", "success");
		return "/login.html";
	}
}
