package com.mycom.myapp.user.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.entity.UserRole;
import com.mycom.myapp.user.repository.UserRepository;
import com.mycom.myapp.user.repository.UserRoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	
	// 패스워드 암호화를 위해 SecurityConfig 에 @Bean 설정되어 있는 PasswordEncoder DI
	private final PasswordEncoder passwordEncoder;
	
	@Transactional 
	@Override
	public UserResultDto insertUser(UserDto userDto) {
		UserResultDto userResultDto = new UserResultDto();
		
		try {
			// 사용자 등록 시, "CUSTOMER" 를 기본 Role 로 처리
			// 새로운 Role 아닌 DB 에서 가져와서 신규 사용자와 연결
			List<UserRole> userRoles = List.of(userRoleRepository.findByName("CUSTOMER"));
			
			User user = User.builder() // 영속화 X
							.name(userDto.getName())
							.email(userDto.getEmail())
							.password(passwordEncoder.encode(userDto.getPassword())) // 패스워드 암호화 후 저장
							.userRoles(userRoles) // userRoles 는 findByName() 을 통해서 영속화
							.build();

			User savedUser = userRepository.save(user); // 전달받은 User 객체는 save() 를 통해 영속화
			System.out.println(savedUser);
			
			userResultDto.setResult("success");		
		}catch(Exception e) {
			e.printStackTrace();
			// 우리의 코드 진행에 영향을 미치지 않고, transaction 을 관리하는 관리자에게 현재 transaction 을 rollback 시켜달라는 정중한 요청
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			userResultDto.setResult("fail");		
		}		
		return userResultDto;
	}

}
