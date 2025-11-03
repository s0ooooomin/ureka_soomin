package com.mycom.myapp.di.javaConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // java configuration으로 DI 기술
public class CalcConfiguration {
	@Bean
	Calculator calculator() { // method 이름으로 DI 설정
		return new Calculator();
	}
}
