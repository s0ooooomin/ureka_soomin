package com.mycom.myapp.auth.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.user.dto.UserDto;

@Mapper
public interface LoginDao {
	// 사용자는 userEmail, userPassword를 백엔드로 전달 (input)
	// dao는 userEmail로 사용자 검색 
	// -> 있음 -> UserDto 객체 채움
	// -> 없음 -> return null
	UserDto login(String userEmail);
	// UserResultDto 처럼 AuthResultDto를 만들고 처리할 순 있으나, 
	// optional 필드가 추가되고 Controller에서 꺼내 판단하는 복잡한 단계가 필요하므로 단순하게 처리

}

