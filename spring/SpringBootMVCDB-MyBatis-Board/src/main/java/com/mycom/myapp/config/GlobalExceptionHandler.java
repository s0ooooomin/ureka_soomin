package com.mycom.myapp.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;


// @ControllerAdvice // AOP 개별 컨트롤러가 처리하지 않는 모든 예외 담당
public class GlobalExceptionHandler {
//	
//	// error.jsp 로 forwarding
//	@ExceptionHandler(Exception.class)
//	public String ExceptionHandler(Exception ex, Model model, HttpServletRequest request) { // 받아서 처리하고 싶은 예외들
//		model.addAttribute("exception", ex);
//		model.addAttribute("requestURI", request.getRequestURI());
//		
//		return "error";
//
//}
	 
	// error.jsp 로 forwarding
	@ResponseBody	
//	@ExceptionHandler(Exception.class)
	public Map<String, String> ExceptionHandler(Exception ex) { // 받아서 처리하고 싶은 예외들
		System.out.println("GlobalExceptionHandler ExceptionHandler");
		Map<String, String> map = new HashMap<>();
		map.put("result", "fail");
		return map;
		
	}
}