package com.mycom.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	// PasswordEncoder DI
	// why? MyUserDetailsService에서 UserDetails 객체를 생성하는 과정에서 필요.
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	// #1. UserDetailSevice 적용에 맞는 formLogin
	// #2. Logout
	// (#3. Role 적용X)
//	@Bean
//	SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception {
//		return http
//				.authorizeHttpRequests( request -> {
//					request.requestMatchers("/login", "/", "/index.html").permitAll();
//					request.anyRequest().authenticated();
//				})
//				.formLogin( form -> form
//									.defaultSuccessUrl("/") // success 하면 이곳의 url로 감		
//									.permitAll()
//						
//				) 		
//				.logout( logout -> logout.permitAll() ) 	// logout
//				.build(); 
//	}
	
	// #1. UserDetailSevice 적용에 맞는 formLogin
	// #2. Logout
	// #3. Role 적용 O
	// Authentication 로그인 (인증된 사용자인가) -> Authorization 적용 (동일하게 로그인했어도 접근권한 확인 - role)
	// HttpSeurity http 메소드체인 적용 ( 중복되는 request들 제거 + 감싸고 있던 {} 제거 )
	@Bean
	SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception {
		return http
				.authorizeHttpRequests( request -> request
					.requestMatchers("/login", "/", "/index.html").permitAll()
					.requestMatchers("/customer/**").hasAnyRole("CUSTOMER", "ADMIN") // Role 기반 접근 설정
					.requestMatchers("/admin/**").hasRole("ADMIN") // Role 기반 접근 설정
					
					.anyRequest().authenticated()
				)
				.formLogin( form -> form
						.defaultSuccessUrl("/") // success 하면 이곳의 url로 감		
						.permitAll()
						) 		
				.logout( logout -> logout.permitAll() ) 	// logout
				.build(); 
	}

}
