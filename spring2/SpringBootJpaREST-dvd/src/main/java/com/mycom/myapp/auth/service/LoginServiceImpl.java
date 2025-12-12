package com.mycom.myapp.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

	private final UserRepository userRepository;
	
	@Override
	public UserResultDto login(String phone, String password) {
		UserResultDto userResultDto = new UserResultDto();
		
		Optional<User> optionalUser = userRepository.findByPhone(phone);
		
		if( optionalUser.isPresent() ) {
			User user = optionalUser.get();
			
			if(user.getPassword().equals(password)) { // 로그인 성공
				// User 엔티티 객체 -> UserDto 객체 
				// password 는 생략 ( Front 까지 전달 )
				UserDto userDto = UserDto.builder()
									.id(user.getId())
									.name(user.getName())
									.phone(user.getPhone())
									.role(user.getUserRole().toString())
									.build();
				
				userResultDto.setUserDto(userDto);
				userResultDto.setResult("success");
			}else {
				userResultDto.setResult("fail");
			}
		}else {
			userResultDto.setResult("fail");
		}
		return userResultDto;
	}

}
