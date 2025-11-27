package com.mycom.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mycom.myapp.entity.Student;
import com.mycom.myapp.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class StudentServiceCRUDImpl implements StudentServiceCrud{

	// StudentRepository DI
	private final StudentRepository studentRepoitory; // lombok에서 final -> @RequiredArgsConstructor
	@Override
	public List<Student> listStudent() {
		// TODO Auto-generated method stub
		return studentRepoitory.findAll();
	}

	@Override
	public Optional<Student> detailStudent(int id) {
		// TODO Auto-generated method stub
		return studentRepoitory.findById(id);
	}

	// save()
	//  전달되는 엔티티 객체에 id 가 있으면 ( select 한 결과가 존재 ) select - update (merge)
	//  전달되는 엔티티 객체에 id 가 있으면 ( select 한 결과가 존재 X ) 오류 : Row was updated or deleted by another transaction
	//  전달되는 엔티티가 @GeneratedValue 를 사용하고 id 가 있으면 select - update 를 수행하려고 한다.
	//  이 때, select 결과가 없는 경우, 이전 버전에서는 insert 를 진행했으나, 
	//  현재 6.6.X 버전에서는 예외를 발생시킨다. 보다 엄격한 룰 적용. 
	//  id 를 포함한 엔티티를 insert 하려면 @GeneratedValue 를 제거하고 사용한다.
	//  이럴 경우, id 를 통해 select 후 없으면 insert 진행한다.
	
	//  전달되는 엔티티 객체에 id 가 없으면 insert (persist)
	@Override
	public Student insertStudent(Student student) {
		// TODO Auto-generated method stub
		return studentRepoitory.save(student);
	}

	@Override
	public Optional<Student> updateStudent(Student student) {
		// TODO Auto-generated method stub
		// # 무조건 save 호출
		return Optional.of(studentRepoitory.save(student));
		// # id 있는지 확인 후 save 호출
//		Optional<Student> existingStudent = studentRepoitory.findById(student.getId());
//		if (existingStudent.isPresent()) { // 아이디가 이미 있다면 (find 했을 경우)
//			return Optional.of(studentRepoitory.save(student));
//		}
//		
//		return Optional.empty();
//	
//		Hibernate: select s1_0.id,s1_0.email,s1_0.name,s1_0.phone from student s1_0 where s1_0.id=?
		// <= 있는지 확인하는 변수를 만들기 위한 findById 에 의한 처리
		
//		Hibernate: select s1_0.id,s1_0.email,s1_0.name,s1_0.phone from student s1_0 where s1_0.id=?
//		Hibernate: update student set email=?,name=?,phone=? where id=?
		// <= 있는 id save 처리. select->update
		
//		Hibernate: select s1_0.id,s1_0.email,s1_0.name,s1_0.phone from student s1_0 where s1_0.id=?
		// <= 없는 id save 처리. select->null
	
	}

	@Override
	public void deleteStudent(int id) {
		studentRepoitory.deleteById(id);
	}

	@Override
	public long countStudent() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Student> listStudent(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Student> page = studentRepoitory.findAll(pageable);
		return page.toList();
	}
	
}
