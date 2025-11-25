package com.mycom.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.entity.Student;

// Spring Data Jpa의 시작 : 제공되는 Interface를 상속받기
// 이를 통해 Student에 대한 기본적인 CRUD는 자동화 처리됨
// 이 인터페이스를 구현하는 클래스를 생성할필요X
public interface StudentRepository extends JpaRepository<Student, Integer>{


}
