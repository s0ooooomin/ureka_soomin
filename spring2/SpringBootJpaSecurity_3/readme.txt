1. SpringBootJpaSecurity_2 버전 파일 복사, /.well-known/** 처리

2. Spring Security가 제공하는 기본 로그인 페이지 -> 자체 로그인페이지 login.html 제공
	- SecurityConfig 의 formLogin 설정 변경
	- 자체 로그인페이지(login.html)가 보이지만, 오류도 나오지 않고 login 도 되지 않는다.
	=> csrf (cross site request forgery..? , cors와 별개) 설정 필요
		spring security가 제공하는 기본로그인페이지와 그 처리에는 이미 포함되어있음
		
		- csrf 설정
		 1. 무시 
		 	.csrf(csrf -> csrf.disable() ) // #1. 무시 (csrf off)
		 	Application - Cookie : sessionid만 
		 
		 2. 기본설정
		 	.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) ) 
		 	Application - Cookie : sessionid + XSRF-TOKEN(추가인증 느낌)
		 	로그인 요청 시 XSRF-TOKEN 함께 전송
	        로그인 후 모든 요청에 XSRF-TOKEN 을 전송 <= 브라우저 수행
	        
	        로그아웃 /logout (get) 처리 X
	        로그인 실패 시 /login?error
	
	
	