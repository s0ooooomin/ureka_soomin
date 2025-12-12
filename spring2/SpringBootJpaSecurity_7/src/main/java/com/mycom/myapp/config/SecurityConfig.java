package com.mycom.myapp.config;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
	
	// PasswordEncoder DI
	// MyUserDetailsService 에서 UserDetails 객체를 생성하는 과정에서 필요.
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// #1
	@Bean
	SecurityFilterChain filterChain(
			HttpSecurity http,
			MyAuthenticationSuccessHandler successHandler,
			MyAuthenticationFailureHandler failureHandler			) throws Exception{
		return http
				.authorizeHttpRequests( request -> request
					.requestMatchers(
						"/",
						"/index.html",
						"/assets/**",  // 새로 추가
						"/.well-known/**",
						"/login", // login.html 이 필요 없는 이유는 formLogin 에서 .loginPage() 및 .permitAll() 로 미리 권한 부여
						"/register",
						"/register.html", // board, board.html 은 인증이 필요, 권한과는 무관
						"/users/**",
						"/csrf-token"
					).permitAll() // 일부 요청
					.requestMatchers("/customer/**").hasAnyRole("CUSTOMER", "ADMIN") // Role 기반 접근
					.requestMatchers("/admin/**").hasRole("ADMIN") // Role 기반 접근
					.anyRequest().authenticated() // 나머지 요청
				)
				// csrf on ( 기본 적용 )
//				.csrf(csrf -> csrf.spa()) // security version 7
				.csrf((csrf) -> csrf
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())   
						// 이게 없으면 csrf 재발급 되지 않고 기존 csrf 쿠키 삭제 응답이 전해진다. 
						// Set-cookie XSRF-TOKEN=; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:10 GMT; Path=/
						.csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())            
					)
				.formLogin( 
					form -> form
							.loginPage("/login.html")  // 자체 login.html 설정
							.loginProcessingUrl("/login") // spring security 의 기본 login (post) 으로 설정
							.successHandler(successHandler) // 로그인 성공 시 처리자
							.failureHandler(failureHandler) // 로그인 실패 시 처리자
							// client 가 비동기 login 요청 <- client 가 결과에 따라 page 또는 데이터 처리를 하겠다.
							// 아래 코드는 백엔드가 결정(redirect)
//							.defaultSuccessUrl("/")   
							.permitAll()
				)
//				.logout(logout -> logout.permitAll()) // csrf on 상태에서 get /logout 자동 처리 X
				.logout(logout -> logout
			            .logoutUrl("/logout") // 기본값도 /logout
			            // MyAuthen~Handler 처럼 MyLogoutSuccessHandler 를 만들 수도 있지만, 아래 처럼 람다식 등을 통해 코드 내 직접 처리도 가능
			            .logoutSuccessHandler((request, response, authentication) -> {
			                response.setStatus(HttpServletResponse.SC_OK);
			                response.setContentType("application/json");
			                response.getWriter().write( 
			                		"""
			        				{"result":"success"}
			        				"""
			                );
			            })
			        )
				
				.build();
	}
}

// 로그인 후 최초 CsrfController 에서 받은 csrf-token 쿠키는 삭제된다. Client 에서 로그인 후 CsrfController 에 재 요청하는 방법도 있지만
// 아래의 코드로 자동 재발급 쿠키를 만든다.
// Spring Security 6.4 공식 문서 제안 코드
// https://docs.spring.io/spring-security/reference/6.4/servlet/exploits/csrf.html
final class SpaCsrfTokenRequestHandler implements CsrfTokenRequestHandler {
	private final CsrfTokenRequestHandler plain = new CsrfTokenRequestAttributeHandler();
	private final CsrfTokenRequestHandler xor = new XorCsrfTokenRequestAttributeHandler();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken) {
		/*
		 * Always use XorCsrfTokenRequestAttributeHandler to provide BREACH protection of
		 * the CsrfToken when it is rendered in the response body.
		 */
		this.xor.handle(request, response, csrfToken);
		/*
		 * Render the token value to a cookie by causing the deferred token to be loaded.
		 */
		csrfToken.get();
	}

	@Override
	public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
		String headerValue = request.getHeader(csrfToken.getHeaderName());
		/*
		 * If the request contains a request header, use CsrfTokenRequestAttributeHandler
		 * to resolve the CsrfToken. This applies when a single-page application includes
		 * the header value automatically, which was obtained via a cookie containing the
		 * raw CsrfToken.
		 *
		 * In all other cases (e.g. if the request contains a request parameter), use
		 * XorCsrfTokenRequestAttributeHandler to resolve the CsrfToken. This applies
		 * when a server-side rendered form includes the _csrf request parameter as a
		 * hidden input.
		 */
		return (StringUtils.hasText(headerValue) ? this.plain : this.xor).resolveCsrfTokenValue(request, csrfToken);
	}
}