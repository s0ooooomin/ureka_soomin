package com.mycom.myapp.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;
import com.mycom.myapp.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
	private UserRepository userRepository;

	@Override
	public UserResultDto login(String email, String password) {
		UserResultDto userResultDto = new UserResultDto();
		
		Optional<User> optionalUser = userRepository.findByEmail(email);
		System.out.println(optionalUser);
		if ( optionalUser.isPresent() ) {
			User user = optionalUser.get();
			System.out.println(user);
			
			if(user.getPassword().equals(password)) {
				List<String> roles = new ArrayList<>();
				user.getRoles().forEach(role -> roles.add(role.getName()) );
				
				UserDto userDto = UserDto.builder()
									.id(user.getId())
									.name(user.getName())
									.email(user.getEmail())
									.roles(roles)
									.build();
				
				userResultDto.setUserDto(userDto);
				userResultDto.setResult("success");
			}else {
				userResultDto.setResult("fail");
			}
		}else {
			userResultDto.setResult("fail");
		}
		
		return userResultDto;
	
	}
	
	
}
