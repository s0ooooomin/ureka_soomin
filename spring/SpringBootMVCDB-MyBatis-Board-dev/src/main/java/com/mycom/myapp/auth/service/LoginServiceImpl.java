package com.mycom.myapp.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycom.myapp.auth.dao.LoginDao;
import com.mycom.myapp.user.dto.UserDto;

@Service
public class LoginServiceImpl implements LoginService {

	private final LoginDao loginDao;
	public LoginServiceImpl(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	@Override
	public Optional<UserDto> login(UserDto userDto) {
		UserDto dto = loginDao.login(userDto.getUserEmail());
		
		// 입력받은 dto의 이메일가지고 login (select where email)했는데 null => 지금 없는거임
		if (dto != null & userDto.getUserPassword().equals(dto.getUserPassword())) {
			dto.setUserPassword(null); // 비밀번호는 지우고 session 저장할거 넘김 (session저장은 controller에서) 
			return Optional.of(dto); // 성공
		}
		return Optional.empty();	// 실패
	}
	
}
