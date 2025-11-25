package com.mycom.myapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.entity.Student;
import com.mycom.myapp.service.StudentServiceCrud;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/students")
@RequiredArgsConstructor // serviceCrud 생성자 만들어줌
public class StudentControllerCRUD {

	// StudentServiceCrud DI (생성자는 @RequiredArgs~ 가 만들어줌)
	private final StudentServiceCrud studentServiceCrud;

	// 프론트의 응답(json)에 entity(student)를 직접 사용X
	// 대신, ~Dto, ~ApiResponse 등에 Dto(StudentDto)를 포함한다.
	// 교육 목적상 간단히 하기 위해 현재는 entity(student) 직접 사용
	@GetMapping("/list")
	public List<Student> listStudent() {
		return studentServiceCrud.listStudent();
	}
	
	@GetMapping("/detail/{id}")
	public Optional<Student> detailStudent(@PathVariable("id") Integer id) {
		return studentServiceCrud.detailStudent(id);
	}
	
	// 등록
	@PostMapping("/insert")
	public Student insertStudent(Student student) {
		return studentServiceCrud.insertStudent(student);
	}
	
	// 수정
	@PostMapping("/update")
	public Optional<Student> updateStudent(Student student) {
		return studentServiceCrud.updateStudent(student);
	}
	
	// 삭제
	@GetMapping("/delete/{id}")
	public void deleteStudent(@PathVariable("id") Integer id) {
		studentServiceCrud.deleteStudent(id);
	}
	
	// 전체 건수, 페이징
	@GetMapping("/count")
	public long countStudent() {
		return studentServiceCrud.countStudent();
	}
	// 전체 건수, 페이징
	@GetMapping("/page")
	public List<Student> listStudent(
			@RequestParam("pageNumber") Integer pageNumber,
			@RequestParam("pageSize") Integer pageSize) {
		return studentServiceCrud.listStudent(pageNumber, pageSize);
	}
	
	
	
	
	
}
