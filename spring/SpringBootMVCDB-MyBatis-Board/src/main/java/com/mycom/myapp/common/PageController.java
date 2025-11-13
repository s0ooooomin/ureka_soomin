package com.mycom.myapp.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

	@GetMapping("/pages/register") // 이걸 갖는 부분의 코드로 이동
	public String register() {
		return "register"; // register.jsp 로 이동
	}
	
	// Runtime 에외 발생
	@GetMapping("/pages/login")
	public String login() {

//		NullPointerException (BoardServiceImpl에서 예외 발생 시 comment)
		// 페이지 이동시 예외 발생
//		String s = null;
//		s.length();
		
		return "login";
	}
	@GetMapping("/pages/board")
	public String board() {
		return "board";
	}
	// logout
	@GetMapping("/pages/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	// /error forwarding은 fw가 처리 but! 예외처리는 fw가 X
	// 아래 코드는 fw 예외처리자가 처리
	// 이 컨트롤러에서 예외가 발생하면 이 메소드에서 처리한다.
	// error.jsp + 데이터(model, ...)
//	@ExceptionHandler(Exception.class)
	@ExceptionHandler(ClassCastException.class)
	public String pageExceptionHandler(Exception ex, Model model, HttpServletRequest request) { // 받아서 처리하고 싶은 예외들
		System.out.println("pageExceptionHandler");
		
		model.addAttribute("exception", ex);
		model.addAttribute("requestURI", request.getRequestURI());
		
		return "error";
		
	}
	
}
