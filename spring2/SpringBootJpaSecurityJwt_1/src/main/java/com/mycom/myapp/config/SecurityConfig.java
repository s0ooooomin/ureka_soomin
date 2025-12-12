package com.mycom.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// JwtUtil 테스트를 위한 설정으로 간단
@Configuration
public class SecurityConfig {
	
	@Bean
    SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers(
                		"/", 
                		"/index.html", 
                		"/token/**"
                ).permitAll() 
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
