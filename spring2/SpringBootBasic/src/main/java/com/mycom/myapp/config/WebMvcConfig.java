package com.mycom.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // root 밑의 sub domain까지 모두 적용
			.allowedOrigins("http://127.0.0.1:5500")
			.allowCredentials(true)
			.allowedHeaders("*")
			.allowedMethods("GET","POST","PUT","DELETE", "OPTIONS");

	}
}
