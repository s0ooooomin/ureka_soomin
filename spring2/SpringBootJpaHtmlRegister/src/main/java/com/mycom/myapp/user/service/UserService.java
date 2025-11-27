package com.mycom.myapp.user.service;

import com.mycom.myapp.user.dto.UserResultDto;
import com.mycom.myapp.user.entity.User;

public interface UserService {

	UserResultDto insertUser(User user);
	
}
