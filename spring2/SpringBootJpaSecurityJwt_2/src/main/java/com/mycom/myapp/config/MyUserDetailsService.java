package com.mycom.myapp.config;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.entity.UserRole;
import com.mycom.myapp.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// UserRepository 를 이용
        Optional<User> optionalUser = userRepository.findByEmail(email);
        
        if( optionalUser.isPresent()  ) {
        	User user = optionalUser.get();
        	List<UserRole> listUserRole = user.getUserRoles();
        	
			// 이전 코드에서 security 의 User 객체를 이용할 때는 Role 이름만으로 문자열 배열로 전달. 내부적으로 GrantedAuthority 전환
			// MyUserDetails 객체를 사용하려면 직접 변환 처리
        	
        	List<SimpleGrantedAuthority> authorities = 
        			listUserRole.stream()
        				.map(UserRole::getName)
        				.map( name -> "ROLE_" + name) // MyUserDetails 부터 "ROLE_" 사용
        				.map(SimpleGrantedAuthority::new)
        				.toList();
        	
        	
        	// 인증 후 리턴하는 userDetails 객체가 spring security 의 User 객체를 사용하고 있으므로
        	// 사용자 관련 추가정보를 repository 를 통해 얻어도 저장할 수 없다.
        	// Custom UserDetailsService 를 구현했듯 이, Customer UserDetails 를 구현해야 가능하다.
        	return MyUserDetails.builder()
        		.username(user.getEmail())
        		.password(user.getPassword()) // null 처리하면 X <= Spring Security 에서 userDetails 객체를 통해 인증을 처리한다.
//        		.roles(roleStrArray) // UserRole 이름만 전달 (이전 코드)
        		.authorities(authorities)
        		.id(user.getId()) // extra
        		.name(user.getName()) // extra
        		.email(user.getEmail()) // extra        		
        		.build();
        }

        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }	
}