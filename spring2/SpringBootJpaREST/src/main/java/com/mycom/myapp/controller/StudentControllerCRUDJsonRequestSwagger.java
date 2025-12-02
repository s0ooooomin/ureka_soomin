package com.mycom.myapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mycom.myapp.dto.StudentDto;
import com.mycom.myapp.dto.StudentResultDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="JSON Student CRUD REST API", description="Student 등록/수정/삭제/상세 조회 기능을 REST API로 제공합니다" )
public interface StudentControllerCRUDJsonRequestSwagger {
	// 목록 get /students (get 방식의 /students == list. post 방식의 /students == insert)
	@Operation(summary="학생 목록", description="전체 학생 목록을 로드합니다.")
	@GetMapping("/students")
	public StudentResultDto listStudent();
	
	// 상세 get /students/123
	@Operation(summary="학생 상세", description="개별 학생을 조회합니다.")
	@GetMapping("/students/{id}")
	public StudentResultDto detailStudent(@PathVariable("id") Integer id);
	
	// 등록 post /stuents
	@Operation(summary="학생 등록", description="신규 학생을 등록합니다.")
	@PostMapping("/students")
	public StudentResultDto insertStudent(@RequestBody StudentDto studentDto);
}
