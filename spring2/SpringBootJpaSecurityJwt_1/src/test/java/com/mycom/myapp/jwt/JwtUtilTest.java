package com.mycom.myapp.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j	// logger (log 관련설정 - application.prop~ 에서 logging 지정)
@AutoConfigureMockMvc
public class JwtUtilTest {

	@Autowired	
	JwtUtil jwtUtil;
	
	@Test
	@Order(1)
	void testDI() {
		log.debug("testDI 시작");
		assertNotNull(jwtUtil);
		log.debug("testDI 끝");
	}
	
	@Test
	@Order(2)
	void testSecretKey() {
		log.debug("testSecretKey 시작");
		log.debug(jwtUtil.getSecretKeyStr());
		assertNotNull(jwtUtil.getSecretKey());
		log.debug("testSecretKey 끝");
	}
	
	String username = "dskim";
	List<String> roles = List.of("ROLE_CUSTOMER", "ROLE_ADMIN");
	
	
	// log 로부터 token 과 secretKeyStr 복사해서 jwt.io 에서 유효성 확인
	// token 을 복사해서 넣고 최초 invalid => secretKeyStr 넣고 valid 확인 
	// 주의 : jwt.io 에 token 이 복사된 후, secretKey 부분을 다른 입력을 넣으면 token 이 변하면서 valid 로 보인다.
	//       token 이 변하면 안되는 상황.
	// test secret key = asdf83sdfsdfasdfasdfefdfdfasr3r3efasfasdfASF43QW4A
	// 
	@Test
	@Order(3)
	void testCreateToken() {
		log.debug("testCreateToken 시작");
		log.debug(jwtUtil.createToken(username, roles));
		log.debug("testCreateToken 끝");
	}
	
	// token 은 이전 테스트 로그에서 복사
	// 주의 : tokenValidDuration 을 짭게 주면 expired 될 수 있다.
	//       <= Jwts.parse().verifyWith(secretKey) 에서 검증
	String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkc2tpbSIsInJvbGVzIjpbIlJPTEVfQ1VTVE9NRVIiLCJST0xFX0FETUlOIl0sImlhdCI6MTc2NTA3Mzg4MCwiZXhwIjoxNzY1MTYwMjgwfQ.HotMY5kToiA0DPA2BX0iiIJ_bur2-l-m92xk1ndH2mM";
				 // eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkc2tpbSIsInJvbGVzIjpbIlJPTEVfQ1VTVE9NRVIiLCJST0xFX0FETUlOIl0sImlhdCI6MTc2NTE2MTg3NSwiZXhwIjoxNzY1MjQ4Mjc1fQ.fk8ODb--xJ9fKm8tUCi-L1DlB0O0QZmzhbqaIfD1bso
	//	@Test
//	@Order(4)
//	void testGetUsernameFromToken() {
//		log.debug("testGetUsernameFromToken 시작");
//		assertEquals("dskim", jwtUtil.getUsernameFromToken(token));
//		log.debug("testGetUsernameFromToken 끝");
//	}
//	
//	@Autowired
//    private MockMvc mockMvc;
//	
//	@Test
//	@Order(5)
//	// header 에 token 을 담으면 
//	// Invalid CSRF token found for http://localhost/token 오류 발생하고 403 응답이 온다.
//	// post 에 .with(csrf()) 추가 <= SecurityConfig의 securityFilterChain() 에 csrf.disable() 이 있어도 추가해 줘야 한다.
//	//   <= MockMvc 내부적으로 csrf token 을 전달하도록 되어 있다.
//	void testGetTokenFromHeader() throws Exception{
//		log.debug("testGetTokenFromHeader 시작");
//        this.mockMvc.perform( 
//        		post("/token")
//        			.header("X-AUTH-TOKEN", token)
//        			.with(csrf())
//        		)     
//        		
//                .andExpect(status().isOk())
//                .andExpect(content().string(token));
//		log.debug("testGetTokenFromHeader 끝");
//	}
}
