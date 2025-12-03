1. spring security 의존성을 추가하고 app start
	Using generated security password: 3e880c3e-1f7f-46ab-bff5-866c06caaf12 ...
	This generated password is for development use only. Your security configuration must be updated before running your application in production.
2. localhost:8080 접속하면 미리 만든 index.html이 아닌, 처음 보는 form login 화면이 보임
	<= Spring Security 가 동작함을 확인.
	<= Security 관련 정책을 제공하지 않았기 때문에 기본 인증 적용된 상태. id 는 user, password 는 콘솔에 출력된 패스워드를 이용.

	<= Controler 에 @RestController 포함 추가하면 위 방식이 동작하지 않고 에러 메시지가 전달된다.
	<= Controler 에 @RestController 포함 추가하면 Spring Security 가 관여하는 컴포넌트가 추가되는 것임으로, 정책을 함께 제공 SecurityConfig

3. SecurityConfig에 정책을 포함한 SecurityFilterChain 객체를 DI 하도록 설정. app start 후 테스트
	가장 단순한 정책을 가진 SecurityFilterChain 객체를 생성 
	(모든 request에 대해 모두 permit)
	-> security login form 창 없이 index.html 로 연결됨
   => /admin/hello, /customer/hello 등 페이지 이동
   
   3-1. 모두 허락
   3-2. 모두 인증
   3-3. 일부 허락, 나머지 인증
   인증이 필요한 경우 formLogin 을 통한 인증 설정
	
	<= 현재 Chrome, Edge 브라우저에서 form login 을 수행하면 /well-known/.... 요청이 추가로 서버에 전달. 이것에 대한 처리가 없기 때문에 whitelable 페이지 에러가 보이나.
   로그인은 정상적으로 수행됨.