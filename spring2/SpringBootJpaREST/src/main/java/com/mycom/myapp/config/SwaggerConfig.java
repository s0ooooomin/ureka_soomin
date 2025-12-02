package com.mycom.myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	OpenAPI openApi() {
		return new OpenAPI()
						.components(new Components())
						.info(apiInfo());
						
	}
	
	private Info apiInfo () {
		return new Info()
				.title("학생관리api") // title 설정
				.description("rest api로 구현된 학생관리 기능을 테스트합니다.")
				.version("-v0.9");
	}
}
