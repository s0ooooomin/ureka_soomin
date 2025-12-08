package com.mycom.myapp.user.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	
	// 패스워드 암호화를 위해 @Bean 설정되어있는 PasswordEncoder DI
	private final PasswordEncoder passwordEncoder;
	
	@Transactional // 원자성 (되면 다 되고, 안 되면 다 rollback)
	@Override 	// Parameter 원래는 UserDto로 받아서 User로 변경하는 작업 필요. 
	public UserResultDto insertUser(UserDto userDto) {
		UserResultDto userResultDto = new UserResultDto();
		
		try {

			// 사용자 등록 시 (회원가입register) "CUSTOMER"를 기본 ROLE로 설정
			// 새로운 Role 아닌 DB에서 가져와서 신규사용자와 연결 (전처럼 testrole X)
			List<UserRole> userRoles = List.of( userRoleRepository.findByName("CUSTOMER") );
			
			// param userDto -> user
			User user = User.builder()
							.name(userDto.getName())
							.email(userDto.getEmail())
							.password(passwordEncoder.encode( userDto.getPassword() )) // 비밀번호 암호화해서 저장
							.userRoles(userRoles) // 이미 위에서 영속화(find~)시켜서 userDto 필요X
							.build();
			
			User savedUser = userRepository.save(user);
			System.out.println(savedUser);
			
			userResultDto.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			userResultDto.setResult("fail");
		}
		
//		userResultDto.setResult("success");
		return userResultDto;
	}

}
