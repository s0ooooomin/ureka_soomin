1. SpringBootJpaSecurity_5 모든 파일 복사

2. MyUserDetails 추가
	- security User 를 사용하지 않고 우리만의 UserDetails 클래스를 정의해서 더 다양한 필드를 관리
	- MyUserDetails implements UserDetails
		- id, email 추가 관리
	- MyUserDetailsService
		- security User 대신 MyUserDetails 객체를 사용
		  - 사용자의 role 을 이름의 배열 대신 GrantedAuthority 객체의 배열로 처리
		    - 'ROLE_' prefix 를 사용
		    
3. 로그인 할 때 사용자를 정보를 session 에 담아서 처리
	로그인 처리를 Spring Security 가 처리. 지금 상황은 ??? MyUserDetails 에 포함시켜서 Spring Security 에 전달
	- UserController 에 
		@AuthenticationPrincipal MyUserDetails userDetails 를 사용하는 메소드 추가. 로그인 후 확인