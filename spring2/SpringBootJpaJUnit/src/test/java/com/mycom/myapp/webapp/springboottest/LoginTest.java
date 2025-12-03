package com.mycom.myapp.webapp.springboottest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mycom.myapp.auth.controller.LoginController;
import com.mycom.myapp.auth.service.LoginService;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@SpringBootTest
public class LoginTest {
	// #1. repository 테스트
	@Autowired
	private UserRepository userRepository;

	@Test
	public void testLoginRepository() {
		Optional<User> optionalUser = userRepository.findByEmail("테스트이메일");
		assertTrue(optionalUser.isPresent());	
		// 결과 : 성공
//		Hibernate: select u1_0.id,u1_0.email,u1_0.name,u1_0.password from user u1_0 where u1_0.email=?
//		Hibernate: select ur1_0.user_id,ur1_1.id,ur1_1.name from user_user_roles ur1_0 join user_role ur1_1 on ur1_1.id=ur1_0.user_roles_id where ur1_0.user_id=?
	}
	
	// #2. service 테스트
	@Autowired
	private LoginService loginService;
	
	@Test
	public void testLoginService() {
		UserResultDto userResultDto = loginService.login("테스트이메일", "테스트패스워드");
		// 1) 성공/실패 : userResultDto.result == success 인지 확인
		// 2) userResultDto.userDto == not null 확인
		assertNotNull(userResultDto.getUserDto());
		assertEquals("success", userResultDto.getResult());
	}
	
	// #3. controller 테스트
	@Autowired
	private LoginController loginController;
	@Autowired
	private HttpSession session;
	
	@Test
	public void testLoginController() {
		UserResultDto userResultDto = loginController.login("테스트이메일", "테스트패스워드", session);

		// 1) 성공/실패 : userResultDto.result == success 인지 확인
//		assertEquals(userResultDto.getResult(), "success"); expect랑 이렇게 바뀌어도 되나
		assertEquals("success", userResultDto.getResult() ); 
		// 2) userResultDto.userDto == not null 확인
		assertNotNull(session.getAttribute("userDto"));
	}
	
	
}