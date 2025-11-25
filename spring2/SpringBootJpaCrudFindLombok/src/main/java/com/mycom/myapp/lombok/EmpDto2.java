package com.mycom.myapp.lombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter		// 많이씀
@Getter		// 많이씀
@ToString 	// 많이씀
//@EqualsAndHashCode // 많이씀

// 많이 쓰는 어노테이션 5개 모아둔거
@Data

//@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor // 많이씀
@Builder

public class EmpDto2 {
	private int employeeId;
	private String firstName;
	private String hireDate;
	private String lastName;
	private String email;

	
//	private final int departmentId;
}
