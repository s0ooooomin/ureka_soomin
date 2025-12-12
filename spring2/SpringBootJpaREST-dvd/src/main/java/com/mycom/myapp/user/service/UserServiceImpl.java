package com.mycom.myapp.user.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
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
	
	
	@Transactional // 원자성 (되면 다 되고, 안 되면 다 rollback)
	@Override 	// Parameter 원래는 UserDto로 받아서 User로 변경하는 작업 필요. 
				// BUT 오늘은 심플하게 걍 entity 바로 왔다갔다
	public UserResultDto insertUser(UserDto userDto) {
		UserResultDto userResultDto = new UserResultDto();
		User user = User.builder()
						.name(userDto.getName())
						.phone(userDto.getPhone())
						.password(userDto.getPassword())
						.build();
		UserRole userRole = userRoleRepository.findByName("USER");
		user.setUserRole(userRole);						
		
		try {
			
			User savedUser = userRepository.save(user);
			System.out.println(savedUser);

			userResultDto.setUserDto(userDto);
			userResultDto.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			userResultDto.setResult("fail");
		}
		
		return userResultDto;
	}

}
