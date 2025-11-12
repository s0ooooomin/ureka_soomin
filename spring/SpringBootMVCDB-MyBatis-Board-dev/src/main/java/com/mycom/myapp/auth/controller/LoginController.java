package com.mycom.myapp.auth.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycom.myapp.auth.service.LoginService;
import com.mycom.myapp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/auth")
public class LoginController {
	private final LoginService loginService;
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	// session.setAttribute("userDto", userDto)
	// 1. 로그인 여부 확인
	// 2. 필요에 따라 백엔드에서 사용자 정보 활용
	@PostMapping("/login")
	public Map<String, String> login(UserDto Dto, HttpSession session) {
		Map<String, String> map = new HashMap<>();
		
		Optional<UserDto> optional = loginService.login(Dto);
		
//		// #1. isPresent() + get() : null 아닌지 확인 <-> isEmpty
//		if ( optional.isPresent() ) {
//			UserDto userDto = optional.get();
//			// session에 dto 저장
//			session.setAttribute("userDto", userDto);
//			map.put("result", "success");
//		}else {
//			map.put("result", "fail");
//		}
		
		// #2. ifPresentORElse (present면 전자, 아니라면 후자)
		optional.ifPresentOrElse(
				userDto -> {
					session.setAttribute("userDto", userDto);
					map.put("result", "success");
				}
				, () -> {
					map.put("result", "fail");
				} );
		
		return map;
	}
}
