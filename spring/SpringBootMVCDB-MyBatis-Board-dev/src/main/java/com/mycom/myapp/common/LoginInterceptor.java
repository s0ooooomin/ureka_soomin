package com.mycom.myapp.common;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.myapp.user.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// WebMvcConfig를 통해 정책 등록
@Component
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("LoginInterceptor >> " + request.getRequestURI());

		// session의 login이 success인지 확인
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userDto");
//		System.out.println("LoginInterceptor >> preHandle : login " + login);
		
		// 로그인 X -> 로그인 필요한 곳 : 거절 & 로그인 페이지로 이동
		if (userDto == null) {
			response.sendRedirect("/pages/login");
			return false;
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
	
		System.out.println("LoginInterceptor >> postHandle");

	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
		System.out.println("LoginInterceptor >> afterCompletion");

	}
}
