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
	
	// 1. 
	@Bean
	SecurityFilterChain filterChain ( 
			HttpSecurity http ,
			MyAuthenticationSuccessHandler successHandler,
			MyAuthenticationFailureHandler failureHandler
			) throws Exception {
		return http
				.authorizeHttpRequests( request -> request
						.requestMatchers(
								"/login", "/", "/login.html",  // login.html이 없는이유? 아래 formLogin에서 loginPage().permitAll();
								"/index.html", 
								"/.well-known/**",
								"/csrf-token",
								"/register.html", "/register",
								"/board.html", "/board",
								"/users"
								
						 ).permitAll()
						.requestMatchers("/customer/**").hasAnyRole("CUSTOMER", "ADMIN") // Role 기반 접근 설정
						.requestMatchers("/admin/**").hasRole("ADMIN") // Role 기반 접근 설정
						
						.anyRequest().authenticated()
						)
				// 기본적용 ( csrf on )
				.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) )
				.formLogin( form -> 
						form
						.loginPage("/login.html")		// 자체 login.html 로 이동
						.loginProcessingUrl("/login")	// spring security 의 기본 login(post)으로 설정
						.successHandler(successHandler)			// login 성공 처리자
						.failureHandler(failureHandler)			// login 실패 처리자
						// success 하면 이곳의 url로 감. why 주석처리? client가 결과에 따라 페이지 처리하겠다.
						//아래 코드는 백엔드가 결정(redirect)
//						.defaultSuccessUrl("/") 
						.permitAll()
						
						) 		
				.logout( logout -> logout.permitAll() ) 	// logout
				.build(); 
	}

}
