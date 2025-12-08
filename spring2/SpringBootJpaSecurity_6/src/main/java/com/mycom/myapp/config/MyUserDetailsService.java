package com.mycom.myapp.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.entity.UserRole;
import com.mycom.myapp.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// entity.User 와 SpringSecurity 의 User 가 클래스 이름이 동일. 주의하기!!
@Service
@RequiredArgsConstructor // DI
public class MyUserDetailsService implements UserDetailsService{

	// spring security에 DI 요청
	// +) SecurityConfig 에서 DI 설정
	private final PasswordEncoder passwordEncoder; // (초기화는 @required~ 가 해줌)
	private final UserRepository userRepository;
	
	// Spring Security 가 로그인 성공/실패 여부를 판단 how?
	// loadUserByUserName() 이 올바른 UserDetails 객체를 리턴 ? 예외가 발생 ? 으로 판단

//	// 이전 버전
//	@Override
//	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//		
//		Optional<User> optionalUser = userRepository.findByEmail(email);
//		
//		if(optionalUser.isPresent()) {
//			User user = optionalUser.get(); // 영속화
//			List<UserRole> listUserRole = user.getUserRoles();
//			List<String> roleStrList = new ArrayList<>();
//			listUserRole.forEach( userRole -> roleStrList.add( userRole.getName() ) );
//			String[] roleStrArray = roleStrList.toArray(new String[0]); // 각각을 String으로 바꿔서 배열로 만듦
//			
//			return org.springframework.security.core.userdetails.User.builder()
//					.username(user.getEmail())		// 로그인 id = Email
//					.password(user.getPassword())	// 로그인 pw = password
//					.roles(roleStrArray)			// String[]로 이름만 전달하지만, 내부적으로 GrantedAuthority 로 변환 처리.
//					.build();
//		}else {
//			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
//		}
//	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		if(optionalUser.isPresent()) {
			User user = optionalUser.get(); // 영속화
			List<UserRole> listUserRole = user.getUserRoles();
			
			// 이전 코드에서 security의 User 객체를 이용할 때는 Role 이름만 문자열배열로 전달 - 내부적으로 GrantedAuthority 전환
			// MyUserDetails 객체 사용 위해 직접 변환 처리 (simpleAuthority 객체 한 번에 만들기. stream 이용해서)
			List<SimpleGrantedAuthority> authorities =
					listUserRole.stream()
						.map(UserRole::getName)
						.map(name -> "ROLE_" + name) 		// role 이름에 "ROLE_" prefix 붙여줌
						.map(SimpleGrantedAuthority::new) 	// 생성자 호출
						.toList();
						
			return MyUserDetails.builder()
					.username(user.getEmail())		// 사용자가 로그인 시 이메일 입력
					.password(user.getPassword())
					.authorities(authorities) 		// roles() 대신 직접 GrantedAuthority 의 필드 적용
					.id(user.getId())
					.name(user.getName())
					.email(user.getEmail())
					.build();
			
		}else {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
	}
	
	

}
