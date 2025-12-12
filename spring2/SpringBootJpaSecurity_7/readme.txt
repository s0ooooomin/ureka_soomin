1. SpringBootJpaSecurity_6 모든 파일 복사

2. MyUserDetails 추가
    - security User 를 사용하지 않고 우리만의 UserDetails 클래스를 정의해서 더 다양한 필드를 관리
    - MyUserDetails implements UserDetails
        - id, email 추가 관리
    - MyUserDetailsService
        - security User 대신 MyUserDetails 객체를 사용
          - 사용자의 role 을 이름의 배열 대신 GrantedAuthority 객체의 배열로 처리
            - ROLE_ prefix 를 사용
            
3. 로그인 할 때 사용자를 정보를 session 에 담아서 처리
    로그인 처리를 Spring Security 가 처리. 지금 상황은 ??? MyUserDetails 에 포함시켜서 Spring Security 에 전달
    - UserController 에 
        @AuthenticationPrincipal MyUserDetails userDetails 를 사용하는 메소드 추가. 로그인 후 확인
        
4. 로그인 후 csrf-token 쿠키 삭제 확인
    - 페이지 이동 코드 주석 처리 후 Network 텝에서 아래 내용 확인
      Set-cookie XSRF-TOKEN=; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:10 GMT; Path=/
    - 재발급을 위해 CsrfController 에 요청하는 방법도 있지만, Spring Security 공식 문서에서 제안한 방법으로 자동 재발급 쿠기를 받는 방법 진행
    - 로그인 이후, post, put, delete, patch 등 변화가 생기는 요청에 대해서는 csrf-token 을 반드시 보내야 한다. 그렇지 않으면 403 Forbidden 
        - 보내는 방법
            1. 헤더에 X-XSRF-TOKEN
            2. 파라미터에 _csrf
        - fetch 코드가 복잡해 지고, 누락 가능성이 있으므로 공통 javascript 로 백엔드 요청 함수를 작성해서 사용
            - board.controller.BoardController
            - common.js in board.html
5. SecurityConfig 에 /assets/** 를 permitAll() 에 추가
            
6. 로그아웃
    - csrf on 상태는 get /logout 으로 자동 logout X
    - post 요청 + csrf-token 으로 처리
    - 대응하는 백엔트 코드 SecurityConfig 필요.          
        