package com.mycom.myapp.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// WebMvcConfig를 통해 정책 등록
@Component
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("LoginInterceptor >> preHandle");

		// session의 login이 success인지 확인
		HttpSession session = request.getSession();
		String login = (String) session.getAttribute("login");
		System.out.println("LoginInterceptor >> preHandle : login " + login);
		
		if ("success".equals(login)) return true;
		
		response.getWriter().write("need login");
		return false;
		// 통과
//		return true;
		
		// 거절
//		return false;
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
