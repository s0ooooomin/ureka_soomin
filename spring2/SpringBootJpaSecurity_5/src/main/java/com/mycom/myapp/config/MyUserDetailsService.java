package com.mycom.myapp.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	// 현재 (하드코딩) dskim/1234 -> 변경 (DB조회) user table

	// 이전 버전
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		
//		if ("dskim".equals(username)) {
//			return User.builder() // spring security의 user (entity 아님)
//					.username("dskim")
//					.password(passwordEncoder.encode("1234")) // 1234 (X) passwordEncode
////					.roles("ADMIN")
//					.roles("CUSTOMER")
////					.roles("ADMIN", "CUSTOMER")
//					.build();
//		}else {
//			throw new UsernameNotFoundException("User Not Found!!");
//		}
//	}

	// 이전 버전
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		if(optionalUser.isPresent()) {
			User user = optionalUser.get(); // 영속화
			// @OneToMany (fetch=FetchType.EAGER)
			List<UserRole> listUserRole = user.getUserRoles();
			
			// user -> userDto 처럼 user -> UserDetails 객체를 만들어서 return 
			// 패스워드 비교 <= Spring Security가 알아서. 리턴한 UserDetails 객체의 password와 사용자가 로그인 시점에 전달한 password를 비교해서 처리
			// security의 user 가 이름이 겹쳐서(entity와) 풀네임으로.
			
			// listUserRole에 포함된 USerRole 각각의 role 이름을 String[] 만들어서 security의 user 객체에 전달
			List<String> roleStrList = new ArrayList<>();
			listUserRole.forEach( userRole -> roleStrList.add( userRole.getName() ) );
			String[] roleStrArray = roleStrList.toArray(new String[0]); // 각각을 String으로 바꿔서 배열로 만듦
			
			return org.springframework.security.core.userdetails.User.builder()
					.username(user.getEmail())		// 로그인 id = Email
					.password(user.getPassword())	// 로그인 pw = password
					.roles(roleStrArray)			// String[]로 이름만 전달하지만, 내부적으로 GrantedAuthority 로 변환 처리.
					.build();
		}else {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
	}
	
	

}
