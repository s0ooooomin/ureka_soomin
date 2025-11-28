package com.mycom.myapp.service;

import java.util.List;
import java.util.Optional;

import com.mycom.myapp.dto.StudentDto;
import com.mycom.myapp.dto.StudentResultDto;
import com.mycom.myapp.entity.Student;

public interface StudentServiceCrud {
	// 목록
	StudentResultDto listStudent();
	// 상세
	StudentResultDto detailStudent(int id);
	
	// 등록 수정 삭제
	// Entity return
	StudentResultDto insertStudent(StudentDto studentDto);
	StudentResultDto updateStudent(StudentDto studentDto);
	StudentResultDto deleteStudent(int id);
	
	// 전체 건수, 페이징
	StudentResultDto countStudent();
	StudentResultDto listStudent(int pageNumber, int pageSize);

}
