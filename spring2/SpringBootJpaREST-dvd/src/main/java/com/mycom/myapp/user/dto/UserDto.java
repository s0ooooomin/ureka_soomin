package com.mycom.myapp.user.dto;

import java.util.List;

import com.mycom.myapp.user.entity.UserRole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
	private Long id;
	private String name;
	private String phone;
	private String password;
	
	// Client에게 전달되는 UserDto에 UserRole 엔티티 전체를 필요X
	// entity와 dto가 별도로 구현 -> 각각의 BL에 변화될 수 있도록 처리
	private String role;
}
