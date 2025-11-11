package com.mycom.myapp.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

// @Component + implements Filter -> tomcat에 spring f/w가 알아서 필터 등록
@Component
public class MyFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse responese, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("MyFilter >> Before ");
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		// 통과
		chain.doFilter(request, responese);
		
		// 거절
//		response.다양한메소드
		
		System.out.println("MyFilter >> After");
	}
}
