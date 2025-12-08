1. SpringBootJpaSecurity_4 모든 파일 복사

2. MyUserDetailsService 수정
	- dskim / 1234 로 hardcoding 된 인증을 user table access 하도록 변경
	- private final UserRepository userRepository 추가
	  userRepository 의 findByEmail() 을 통해 이메일 기준 로그인 진행 코드
	  User 와 UserRole 연관관계 설정 -> @OneToMany(fetch=FetchType.EAGER)
	  GrantedAuthority 대신 Role 의 이름만으로 String[] security User 의 빌딩과정에서 roles () 전달.
	  UserDetails 객체를 return.
	  비밀번호 검증은 Spring Security 가 UserDetails 객체의 password 와 사용자가 입력한 password 를 비교 처리
	  
	 