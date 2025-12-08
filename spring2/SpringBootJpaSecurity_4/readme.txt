1. SpringBootJpaSecurity_3 버전 파일 복사

2. 자체 login.html 이 로그인 과정을 비동기 요청으로 처리
	- 로그인을 비동기 요청으로 처리 : fetch (get 방식)
	- 이에 대응하여 SecurityConfig에 로그인 성공/실패 처리자 작성&전달
		로그인 성공 시 가던 기본 url 은 주석
   - MyAuthenticationSuccessHandler, MyAuthenticationFailureHandler 작성 후, 
        (response 에서 응답코드, 결과 json 응답 처리)
     SecurityConfig 의 filterChain() 에 파라미터로 추가하고, 
     formLogin 의 successHandler(), successHandler, failureHandler 에 연결.	
   - MyUserDetailsService 는 변화 X
   - 로그인 성공은 / 로 페이지 이동, 실패는 에러 메세지 표시

3. 회원가입 기능 추가
 - SpringBootJpaHtmlRegister 의 user package 를 복사
     회원가입 기본코드를 수정
       
        UserDto, User
        	@Data
			@Builder
			@NoArgsConstructor
			@AllArgsConstructor
		UserRepository
			단순 버전
		UserService, UserServiceImpl
			"CUSTOMER" role 을 기본 사용자 role 
				DB 로 부터 가져와서 User 연결
			UserResultDto insertUser(User -> UserDto)
			private final PasswordEncoder passwordEncoder; DI
			User Entity bulid 시
				.password(passwordEncoder.encode(userDto.getPassword()))
		 UserController 
		 	@PostMapping("") // post /users
		 	UserResultDto insertUser(User -> UserDto)
		 	 - register.html
     	 username -> name
     	 email 추가
     	 비동기로 /users post 요청	
     
4. PageController 추가
5. board.html 추가
6. index.html 링크 추가
