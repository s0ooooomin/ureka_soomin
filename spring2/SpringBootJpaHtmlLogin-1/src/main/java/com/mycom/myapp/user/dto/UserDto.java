package com.mycom.myapp.user.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

	private Long id;
	private String name;
	private String password;
	private String email;
	
	private List<String> roles;
}
