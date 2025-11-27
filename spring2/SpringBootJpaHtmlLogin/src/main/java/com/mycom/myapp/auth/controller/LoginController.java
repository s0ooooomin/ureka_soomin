package com.mycom.myapp.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.auth.service.LoginService;
import com.mycom.myapp.user.dto.UserResultDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // loginservice DI
public class LoginController {
	
	private final LoginService loginService;
	
	@PostMapping("/login")
	public UserResultDto login(
		@RequestParam("email") String email,
		@RequestParam("password") String password,
		HttpSession session
			) {
		UserResultDto userResultDto = loginService.login(email, password);
		if (userResultDto.getResult().equals("success")) {
			session.setAttribute("userDto", userResultDto.getUserDto());
		} // fail일 때는 아무것도 안 함
		
		 return userResultDto;
	}
	
	@GetMapping("/logout")
	public UserResultDto logout(HttpSession session) {
		UserResultDto userResultDto = new UserResultDto();
		
		try {
			session.invalidate();
			userResultDto.setResult("success");

		} catch (IllegalStateException e) { // Exception e
			e.printStackTrace();
			userResultDto.setResult("fail");
		}
		
		return userResultDto;
	}
}
