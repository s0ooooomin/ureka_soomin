JWT Auth 방식
   Form Login 방식에 비해, 우리의 코드가 흐름에 관여한다.
   token 발생 및 확인만 한다.
   	로그인 성공해서 token 을 받아도 customer/hello 등 접근 X ( 토큰을 전송하지 않아서 )
0. SpringBootJpaSecurity_6 의 static 폴더 복사
	index.html, login.html 중심 테스트 (나머지는 필요 x)
	
1-1. LoginController	
	auth package 를 만들고 그 안에 만든다.
	/auth/login mapping 을 LoginService 의 login() 을 호출한다.
		Spring Security form login 은 SecurityConfig 에서 직접 매핑 및 처리
		=> jwt 는 직접 처리해 줘야 한다.
1-2. LoginServiceImpl
	AuthenticationManager 객체를 통한 인증을 처리
	SecurityConfig 에 @Bean 등록
	
2. login.html
	요청 url 을 /auth/login 으로 변경
	백엔드에서 보낸 token 을 console.log() 로 확인
3. JwtAuthenticationFilter
	jwt 패키지에 추가
	client 가 전송한 token 유효성 검증
		1. 토큰 유효성만 검증
		2. 토큰 유효성 검증 + db 검증 수행
    	
3. SpringSecurity
	/auth/** 허락 추가
   	  
   	MyAuthenticationSuccessHandler, MyAuthenticationFailureHandler 삭제
   	httpBasic disable
   	formLogin disable
   	csrf disable
		- Cookie 대신 sessionStorage 사용
	session 사용 x
4. LoginServiceImpl
	1. AuthenticationManager DI
	2. AuthenticationManager 객체를 이용해서 authentication 진행
          AuthenticationManager 의 authenticate(  UsernamePasswordAuthenticationToken ( username, password )  ) 을 통해 Authentication 객체를 얻는다.
                내부적으로  AuthenticationProvider ( default 로 DaoAuthenticationProvider ) 를 이용하는데 이 과정에서 UserDetailsService 의 loadUserByUsername() 이 사용된다.
          Authentication.객체의 getPrincipal() 를 통해 UserDetails 객체를 얻고 이를 이용해 Token 발행을 한다.
	3. Token 을 LoginResultDto 에 담아서 리턴, Controller 를 통해 Client 에게 전달
          Client login.html 은 token 을 sessionStorage 에 authToken 이름으로 저장 ( 아직 )
5. UserDetailsService
	1. loadUserByUsername() 수정
		- ROLE_ 제외
        - token 으로 jwt.io 에서 확인했을 때 ROLE_ 빼고 나오도록