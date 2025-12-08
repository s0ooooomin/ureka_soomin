package com.mycom.myapp.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 비동기 로그인 요청에 대해 실패했을 경우 전달되는 Handler
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest request, 
		HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		// response 실패
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			
			String jsonStr = """
					{"result":"failure"}
					""";
			
			response.getWriter().write(jsonStr);
				
	}
	
}
