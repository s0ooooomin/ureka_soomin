package com.mycom.myapp.webapp.webmvctest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcExtensionsKt;

import com.mycom.myapp.user.controller.UserController;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import com.mycom.myapp.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// Spring Web Application 코드테스트
// src/main/java/com/mycom/myapp 中 controller
// 컨트롤러를 제외한 코드는 가상의 코드를 만들어서 테스트 진행
// 일부 코드만 테스트할 수 있지만, 느리다.

@WebMvcTest(UserController.class) // 특정 컨트롤러 클래스 명시X -> 전체 DI 모두를 대상으로 삼음
@Slf4j
public class DITest {
	// DI 테스트
	
	@Autowired
	MockMvc mockMvc; // 빠르게 하기 위한 가짜 객체
	
	@MockBean
	UserController userController;
	
	@MockBean
	UserService userService;
	
	@MockBean
	UserRepository userRepository;
	
	@Test
	@Order(1)
	void TestDI1() {
		log.info("testDI() 시작!");
		assertNotNull(userController);
		assertNotNull(userService);
		assertNotNull(userRepository);
		log.warn("testDI() 종료!");
	}
	
	@Test
	@Order(2)
	void TestDIAll() {
		log.info("testDIAll() 시작!");
		assertAll("DI 묶음테스트",
				() -> assertNotNull(userController),
				() -> assertNotNull(userRepository),
				() -> assertNotNull(userRepository)
				);
		log.debug("testDIAll() 종료!");
	}
	
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;
	
	@Test
	@Order(3)
	// loggin level = debug
	void TestDISessionRequest() {
		log.info("testDISessionRequest() 시작!");
		assertAll("DI 묶음테스트",
				() -> assertNotNull(session),
				() -> assertNotNull(request)
				);
		log.debug("testDISessionRequest() 종료!");
	}

	// userController insertUser() test
	@Test
	@Order(4)
	void TestDIInsertUser() {
		log.info("testDIInsertUser() 시작!");

		User user = new User();
		user.setName("테스트유저");
		user.setPassword("테스트패스워드");
		user.setEmail("테스트이메일");
		
		// #1. 
//			assertNotNull(userController.insertUser(user)); // user 객체가 넘어오는지 확인
//			assertEquals("success", userController.insertUser(user).getResult());

		// #2.
		UserResultDto userResultDto = userController.insertUser(user);
		assertNotNull(userResultDto); // userResultDto 객체가 넘어오는지 확인
		assertEquals("success", userResultDto.getResult());
		
		log.debug("testDIInsertUser() 종료!");
		
		// @SpringBootTest와 달리 @WebMvcTest + @MockMvc 를 이용한 테스트는 실패
		// 가짜 객체이기 때문에 insert같은 건 할 수 없음?

	}
}
