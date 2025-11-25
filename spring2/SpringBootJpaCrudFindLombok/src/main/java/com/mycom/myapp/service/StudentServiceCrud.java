package com.mycom.myapp.service;

import java.util.List;
import java.util.Optional;

import com.mycom.myapp.entity.Student;

// 코드 예제 간단함을 위해 Student <-> StudentDto 생략
// StunetDto 생성 X
// returnType은 StudentRepository의 메소드 리턴과 동일하게 구현 (학습 목적)
public interface StudentServiceCrud {
	// 목록
	List<Student> listStudent();
	// 상세
	Optional<Student> detailStudent(int id);
	
	// 등록 수정 삭제
	// Entity return
	Student insertStudent(Student student); // 영속화(persist)된 객체 리턴
	Optional<Student> updateStudent(Student student);	// id 일치X -> return null
	void deleteStudent(int id);
	
	// 전체 건수, 페이징
	long countStudent();
	List<Student> listStudent(int pageNumber, int pageSize);

}
