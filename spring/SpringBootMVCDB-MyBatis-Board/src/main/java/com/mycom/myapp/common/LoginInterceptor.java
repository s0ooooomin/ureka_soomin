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

// 페이지 요청과 데이터요청 구분 필요
// 로그인이 필요한 게시판에 로그인 -> 세션 타임아웃 -> 데이터 요청 시 login 페이지로 이동 필요
// interceptor가 막았으니 이동해야함을 응답해야 함
// front는 data 요청 시 http header에 ajax=true 보내기

@Component
public class LoginInterceptor implements HandlerInterceptor{

	private final String jsonStr = "{\"result\":\"login\"}";
	
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
			// 거절
			
			if ("true".equals(request.getHeader("ajax"))) {
				// #1. 데이터 요청 (이미 board에 있었는데 세션 만료 후 지속 사용할 경우)
				System.out.println("LoginInterceptor >> ajax request 거절!");
				response.getWriter().write(jsonStr);
			}else {
				// #2. 페이지 요청 (board로 들어갔는데 로그인X 일 경우)
				System.out.println("LoginInterceptor >> page request 거절!");
				response.sendRedirect("/pages/login");
				
			}
			return false;
			
		}
		// 통과
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
