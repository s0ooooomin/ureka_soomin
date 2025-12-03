package com.mycom.myapp.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service // service layer 중 한개. (그래서 서비스패키지에 넣는 사람도 있음)
@RequiredArgsConstructor // DI
public class MyUserDetailsService implements UserDetailsService{

	// spring security에 DI 요청
	// +) SecurityConfig 에서 DI 설정
	private final PasswordEncoder passwordEncoder; // (초기화는 @required~ 가 해줌)
	
	// 기본 인증 대신 사용자별 인증을 구현
	// parameter username 을 통해서 DB Access 통해 username 에 해당하는 User의 기본 정보를 UserDetails 객체로 만들어 리턴한다.

	// #1) DB Access X, dskim/1234를 username/password로 사용
	// 1-1. Role 적용 전
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	
//		if ("dskim".equals(username)) {
//			return User.builder() // spring security의 user (entity 아님)
//						.username("dskim")
//						.password(passwordEncoder.encode("1234")) // 1234 (X) passwordEncode
//						.build();
//			
//		}else {
//			throw new UsernameNotFoundException("User Not Found!!");
//		}
//		
//	}
	
	// 1-2. Role 적용 후
	// 현재는 하드코딩, 나중에는 사용자별 ROLE을 DB ACCESS 하여 처리해야함
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if ("dskim".equals(username)) {
			return User.builder() // spring security의 user (entity 아님)
					.username("dskim")
					.password(passwordEncoder.encode("1234")) // 1234 (X) passwordEncode
					.roles("ADMIN")
					.build();
			
		}else {
			throw new UsernameNotFoundException("User Not Found!!");
		}
		
	}

}
