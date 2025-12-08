package com.mycom.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
public class SecurityConfig {
	
	// PasswordEncoder DI
	// why? MyUserDetailsService에서 UserDetails 객체를 생성하는 과정에서 필요.
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	// 1. security 제공 기본 로그인 페이지 사용 ( SpringSecurity_2 복사 + ".well-known/**" )
	// #1. UserDetailSevice 적용에 맞는 formLogin + #2. Logout + #3. Role 적용 O
//	@Bean
//	SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception {
//		return http
//				.authorizeHttpRequests( request -> request
//					.requestMatchers("/", "/index.html", "/.well-known/**").permitAll()
//					.requestMatchers("/customer/**").hasAnyRole("CUSTOMER", "ADMIN") // Role 기반 접근 설정
//					.requestMatchers("/admin/**").hasRole("ADMIN") // Role 기반 접근 설정
//					
//					.anyRequest().authenticated()
//				)
//				.formLogin( form -> form
//						.defaultSuccessUrl("/") // success 하면 이곳의 url로 감		
//						.permitAll()
//						) 		
//				.logout( logout -> logout.permitAll() ) 	// logout
//				.build(); 
//	}

	// 2. 자체 로그인 페이지 login.html 사용
	// #1. UserDetailSevice 적용에 맞는 formLogin + #2. Logout + #3. Role 적용 O
	@Bean
	SecurityFilterChain filterChain ( HttpSecurity http ) throws Exception {
		return http
				.authorizeHttpRequests( request -> request
						.requestMatchers(
								"/login", "/", 
								"/index.html", 
								"/.well-known/**",
								"/csrf-token"
								
						 ).permitAll()
						.requestMatchers("/customer/**").hasAnyRole("CUSTOMER", "ADMIN") // Role 기반 접근 설정
						.requestMatchers("/admin/**").hasRole("ADMIN") // Role 기반 접근 설정
						
						.anyRequest().authenticated()
						)
				// #1. 무시 ( csrf off )
//				.csrf(csrf -> csrf.disable() )
				// #2. 기본적용 ( csrf on )
				.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) )
				.formLogin( form -> 
						form
						.loginPage("/login.html")		// 자체 login.html 로 이동
						.loginProcessingUrl("/login")	// spring security 의 기본 login(post)으로 설정
						.defaultSuccessUrl("/") // success 하면 이곳의 url로 감		
						.permitAll()
						) 		
				.logout( logout -> logout.permitAll() ) 	// logout
				.build(); 
	}

}
