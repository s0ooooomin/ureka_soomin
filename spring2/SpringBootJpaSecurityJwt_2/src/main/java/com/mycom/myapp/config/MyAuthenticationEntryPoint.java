package com.mycom.myapp.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 인증 과정에서 발생한 예외에 대해 
// SecurityConfig 에 등록하고 {"result":"login"} 응답
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
    	HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException 
     // Abstract superclass for all exceptions related to an Authentication object 
     // being invalid for whatever reason.
    ) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 401
        
        String jsonStr = """
        		{"result":"login"}
        		""";
        response.getWriter().write(jsonStr);
    }
}
