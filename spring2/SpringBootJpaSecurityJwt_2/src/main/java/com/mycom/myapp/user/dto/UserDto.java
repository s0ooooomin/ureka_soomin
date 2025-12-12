package com.mycom.myapp.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	private Long id;
	private String name;
	private String email;
	private String password;
	
	// client 에게 전달되는 UserDto 에 UserRole 엔티티 전체를 필요 X
	// Entity 와 Dto 가 별도로 구현되어서 각각의 B.L 의해 변화될 수 있도록 처리.
	private List<String> roles;
}
