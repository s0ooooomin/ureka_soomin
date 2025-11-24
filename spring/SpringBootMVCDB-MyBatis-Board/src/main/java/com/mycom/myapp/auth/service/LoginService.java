package com.mycom.myapp.auth.service;

import java.util.Optional;

import com.mycom.myapp.user.dto.UserDto;

// Login business logic 처리하는 경우
// 로그인 성공 또는 실패 모두 가능 (사용자 실수로 인한 실패 가능 <= 시스템오류는 아니지만 대응코드 필요)
// -> 호출하는 Controller에서 null 가능성 인지 필요 
// => optional로 메소드를 감쌈
// +) 호출하는 Controller에서 구체적인 optional 대응코드 작성
public interface LoginService {
	Optional<UserDto> login(UserDto userDto);
}
