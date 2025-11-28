package com.mycom.myapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.dto.StudentDto;
import com.mycom.myapp.dto.StudentResultDto;
import com.mycom.myapp.entity.Student;
import com.mycom.myapp.service.StudentServiceCrud;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api") // /api/v2.2 <= 버전 추가 가능
@RequiredArgsConstructor // serviceCrud 생성자 만들어줌
public class StudentControllerCRUD {

	// StudentServiceCrud DI (생성자는 @RequiredArgs~ 가 만들어줌)
	private final StudentServiceCrud studentServiceCrud;

	// 목록 get /students (get 방식의 /students == list. post 방식의 /students == insert)
	@GetMapping("/students")
	public StudentResultDto listStudent() {
		return studentServiceCrud.listStudent();
	}
	
	// 상세 get /students/123
	@GetMapping("/students/{id}")
	public StudentResultDto detailStudent(@PathVariable("id") Integer id) {
		return studentServiceCrud.detailStudent(id);
	}
	
	// 등록 post /stuents
	@PostMapping("/students")
	public StudentResultDto insertStudent(StudentDto studentDto) {
		return studentServiceCrud.insertStudent(studentDto);
	}
	
	// 수정 put /students
	@PutMapping("/students/{id}")
	public StudentResultDto updateStudent(@PathVariable("id") Integer id, StudentDto studentDto) {
		// Parameter StudentDto 객체의 id 필드가 비어있을 수도 있으니 명시적 처리
		// PathVaiable로 넘어오는 id를 우선시
		studentDto.setId(id); // 있든없든 틀린 게 있을 수 있으니 set
		return studentServiceCrud.updateStudent(studentDto);
	}
	
	// 삭제
	@DeleteMapping("/students/{id}")
	public StudentResultDto deleteStudent(@PathVariable("id") Integer id) {
		return studentServiceCrud.deleteStudent(id);
	}
	
	// 전체 건수, 페이징
	@GetMapping("/students/count")
	public StudentResultDto countStudent() {
		return studentServiceCrud.countStudent();
	}
	
	// 페이징
	@GetMapping("/students/page/{pageNumber}/{pageSize}")
	public StudentResultDto listStudent(
			@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize
			) {
		return studentServiceCrud.listStudent(pageNumber, pageSize);
	}
	
	
	
}
