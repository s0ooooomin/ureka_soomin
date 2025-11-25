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
