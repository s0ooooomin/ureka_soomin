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
import com.mycom.myapp.user.controller.UserController;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;
import com.mycom.myapp.user.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

// Login Test는 데이터변화X. RegisterTest는 신규데이터 insert -> DB변화O
// DB 변화없이 테스트데이터는 rollback 원할경우 transactional
@SpringBootTest
public class RegisterTest {
	// #1. repository 테스트
	@Autowired
	private UserRepository userRepository;
	
	
	@Test
	@Transactional 
	// @Test + @Transactional : 테스트 과정 중 생긴 모든 변화 rollback (붙은 메소드 내 내용 전부)
	// 최초 홍길동 insert, @Transactional 추가 후 이길동 insert -> rollback -> test는 성공. 데이터는 rollback
	public void testRegisterRepository() {
		User user = new User();
		user.setName("이길동");
		user.setEmail("hong@gil.dong");
		user.setPassword("password");
		
		User saveUser = userRepository.save(user); // user가 DB에 insert 되고 영속화된 savedUser를 return -> savedUser를 not null 인지 확인
		assertNotNull(saveUser);
	}
	
	// #2. service 테스트
	@Autowired
	private UserService userService;
	
	@Test
	@Transactional
	public void testRegisterService() {
		User user = new User();
		user.setName("이길동");
		user.setEmail("hong@gil.dong");
		user.setPassword("password");
		
		UserResultDto userResultDto = userService.insertUser(user);
		// 1) 성공/실패 : userResultDto.result == success 인지 확인
		assertEquals("success", userResultDto.getResult());
		// 2) userResultDto.userDto == not null 확인
	}
	
	// #3. controller 테스트
	@Autowired
	private UserController userController;
	
	@Test
	@Transactional
	public void testRegisterController() {
		User user = new User();
		user.setName("이길동");
		user.setEmail("hong@gil.dong");
		user.setPassword("password");
		
		UserResultDto userResultDto = userController.insertUser(user);

		// 1) 성공/실패 : userResultDto.result == success 인지 확인
		assertEquals("success", userResultDto.getResult() ); 
	}
	
	
}