1. login
	: UserDetailsService 인터페이스를 구현한 구현체를 제공하고, spring security가 기본인증(user+console의 pw)이 아닌 사용자별 인증으로 정책전환
	현재 프로젝트는 DB access 대신 dskim/1234 로 하드코딩된 MyUserDetailsService 객체를 구현
   	PasswordEncoder 를 SecurityConfig 에 DI 설정, 이를 통해서 MyUserDetailsService 의 loadByUsername() 에서 password 설정에 사용
   	loadByUsername() 이 로그인 성공하면 UserDetails 객체를 리턴, 실패하면 UsernameNotFoundException 예외를 발생
   	
2. logout : /logout 자동처리
	SecurityConfig에서 설정
	
3. User Role 적용
	ADMIN, CUSTOMER
	ADMIN	: /admin/hello, /customer/hello
	CUSTOMER: /customer/hello
	<= SecurityConfig 에 정책 설정 & 사용자가 로그인할 때 (MyUserDetailsSevice - loadByUsername()에서) 사용자의 Role 적용
	
	
	