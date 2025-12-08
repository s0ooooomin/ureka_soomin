package com.mycom.myapp.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.config.MyUserDetails;
import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController // controller역할 + 응답 json
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	// post 방식으로 /users 요청 -> insertUser
	@PostMapping("") 
	public UserResultDto insertUser(UserDto userDto) { // 원래는 UserDto를 받아서 UserService로 전달해야함
		return userService.insertUser(userDto);
	}
	
	@GetMapping("/info")
	public UserDto info(@AuthenticationPrincipal MyUserDetails userDetails) {
		return UserDto.builder()
				.name(userDetails.getName())	// 개선필요사항 : name에는 email이 들어가있음
				.id(userDetails.getId())
				.email(userDetails.getEmail())
				.roles(userDetails.getAuthorities().stream().map(authority -> authority.getAuthority()).toList() )
				.build();
	}
}
