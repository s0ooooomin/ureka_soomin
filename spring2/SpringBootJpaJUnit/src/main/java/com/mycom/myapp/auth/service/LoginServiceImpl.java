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
	public UserResultDto login(String email, String password) {
		UserResultDto userResultDto = new UserResultDto();
		
		// #1. email -> findByEmail()
		// select 2번 실행
		// User, UserRole은 관계 테이블 user_user_role에 id로 연결되어있음
		// +) findByEmail을 통해서 User를 fetch할때 (가져올때) EAGER라고 해도
		// 먼저 User id 확보 -> user_user_roles select
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		// #2. 3번 사용자 -> hardcoding value로 기본 제공되는 findById(3)
		// User id로 찾으면 이미 user_user_roles에 필요한 id get! -> 바로 join (EAGER fetch)
//		Optional<User> optionalUser = userRepository.findById(email);
		
		// #3. findByEmail() + JPQL (@Query)
//		Optional<User> optionalUser = userRepository.findById(email);
		
		
		if( optionalUser.isPresent() ) {
			User user = optionalUser.get();
			
			if(user.getPassword().equals(password)) { // 로그인 성공
				// UserRole 객체들 -> UserRole 의 이름(String) 만 추출
				List<String> roles = new ArrayList<>();
				user.getUserRoles().forEach( userRole -> roles.add(userRole.getName()));
				
				// User 엔티티 객체 -> UserDto 객체 
				// password 는 생략 ( Front 까지 전달 )
				UserDto userDto = UserDto.builder()
									.id(user.getId())
									.name(user.getName())
									.email(user.getEmail())
									.roles(roles)
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
