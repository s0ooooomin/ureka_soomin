package com.mycom.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
//	// #1 Security Filter Chain
//	// 모든 요청(request.anyRequest()) 에 대해 허락(permitAll())
//	// 가장 단순한 정책을 가진 SecurityFilterChain 객체를 생성
//	// -> security form 창 없이 index.html 로 연결됨
//	@Bean
//	SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception {
//		return http
//				.authorizeHttpRequests( request -> request.anyRequest().permitAll() )
//				.build(); 
//		
//	
//	}

	// #2 Security Filter Chain - authenticated
	// 모든 요청(request.anyRequest()) 에 대해 인증필요 (authenticated())
	// 인증이 필요한 설정은 로그인 방식 지정 (formLogin)
	// -> security form 창 없이 index.html 로 연결됨
//	@Bean
//	SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception {
//		return http
//				.authorizeHttpRequests( request -> {
//					request.requestMatchers("/login", "/", "/index.html").permitAll();
//					request.anyRequest().authenticated();
//				})
//				.formLogin( Customizer.withDefaults() )
//				.build(); 
//	}
	@Bean
	SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception {
		return http
				.authorizeHttpRequests( request -> request.anyRequest().authenticated())
				.formLogin( Customizer.withDefaults() )
				.build(); 
	}

	// #2 
	// 



}
