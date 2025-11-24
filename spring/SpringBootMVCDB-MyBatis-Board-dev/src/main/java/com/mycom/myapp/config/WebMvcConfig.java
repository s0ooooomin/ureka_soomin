package com.mycom.myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mycom.myapp.common.LoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	// LoginInterceptor 를 registry에 등록
	// LoginInterceptor 적용 정책 기술 -> 등록 
	// Interceptor는 Controller에 대한 요청 + Controller에서 페이지요청 두가지 다 적용됨

	@Autowired
	private LoginInterceptor loginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	
		// 1. 모두 적용 - 예외 두기
		registry.addInterceptor(loginInterceptor)
			.addPathPatterns("/**")	// 모든 경로에 LogInterceptor 적용
			.excludePathPatterns(
				"/"
				, "/index.html"
				, "/assets/**"
				, "/pages/login", "/pages/register.html"
				, "/auth/**"
			);
			
		
//		// 2. 필요한 것에만 적용
//		registry.addInterceptor(loginInterceptor)
//			.addPathPatterns("/admin/**"); 		// log 1번만
			
	}
}
