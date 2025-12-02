package com.mycom.myapp.webapp.springboottest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycom.myapp.user.controller.UserController;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import com.mycom.myapp.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// Spring Web Application 코드 테스트
// src/main/java/com/mycom/myapp 이하 코드들을 테스트
// Controller가 http request, response 동작하는 환경
//모든 코드를 테스트할 수 있지만, 빠르다.
// console에서 Spring Boot WebApplication 을 위한 내장톰캣이 동작 -> 테스트 코드 진행 후 자동종료

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // order 어노테이션 추가를 위한 어노테이션
@Slf4j
public class DITest {
	// DI 테스트
	@Autowired // 테스트 코드에서는 그냥 필드 injection
	UserController userController;
	
	@Autowired
	UserService userService;
	
	@Autowired
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
//		assertNotNull(userController.insertUser(user)); // user 객체가 넘어오는지 확인
//		assertEquals("success", userController.insertUser(user).getResult());

		// #2.
		UserResultDto userResultDto = userController.insertUser(user);
		assertNotNull(userResultDto); // userResultDto 객체가 넘어오는지 확인
		assertEquals("success", userResultDto.getResult());
		
		log.debug("testDIInsertUser() 종료!");
//		Hibernate: insert into user_role (name) values (?)
//		Hibernate: insert into user (email,name,password) values (?,?,?)
//		User(id=6, name=테스트유저, email=테스트이메일, password=테스트패스워드)
//		Hibernate: insert into user_user_roles (user_id,user_roles_id) values (?,?)

	}
	
	
	
}





