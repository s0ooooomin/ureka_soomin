package com.mycom.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.entity.Student;

// Spring Data Jpa의 시작 : 제공되는 Interface를 상속받기
// 이를 통해 Student에 대한 기본적인 CRUD는 자동화 처리됨
// 이 인터페이스를 구현하는 클래스를 생성할필요X
public interface StudentRepository extends JpaRepository<Student, Integer>{

	// #1. 기본적인 CRUD 등은 별도의 메소드 선언 없이 상속받은 인터페이스를 통해 구현완료됨

	// #2. 검색용도
	List<Student> findByName(String name);
	List<Student> findByEmailAndPhone(String email, String phone);
	List<Student> findByEmailOrPhone(String email, String phone);

	List<Student> findByNameStartingWith(String name);
	List<Student> findByEmailEndingWith(String email);
	List<Student> findByPhoneContaining(String phone);
	List<Student> findByNameLike(String name);

	List<Student> findAllByOrderByNameDesc(); // 모든 것을 Name Desc로 정렬

	List<Student> findByIdBetween(int from, int to); // 모든 것을 Name Desc로 정렬
	
	
}
