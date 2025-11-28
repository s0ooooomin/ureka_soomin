package com.mycom.myapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class StudentResultDto {
	private String result; 			// 작업 success, fail
	private StudentDto dto; 	// 상세 1건
	
	private List<StudentDto> list; 	// 조건에 맞는 목록
	private long count; 			// 조건에 맞는 목록 전체 건수
}
