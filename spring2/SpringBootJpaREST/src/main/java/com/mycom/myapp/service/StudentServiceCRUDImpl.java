package com.mycom.myapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mycom.myapp.dto.StudentDto;
import com.mycom.myapp.dto.StudentResultDto;
import com.mycom.myapp.entity.Student;
import com.mycom.myapp.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class StudentServiceCRUDImpl implements StudentServiceCrud{

	// StudentRepository DI
	private final StudentRepository studentRepoitory; // lombok에서 final -> @RequiredArgsConstructor
	@Override
	public StudentResultDto listStudent() {
		StudentResultDto studentResultDto = new StudentResultDto();
		
		try {
			List<StudentDto> studentDtoList = new ArrayList<>();
			List<Student> studentList = studentRepoitory.findAll(); // 일단 다 찾아서 student(entity)로 된 list 생성
			
			// entity 객체들이 담긴 list foreach -> studentDtoList에 하나씩 정보를 담음
			studentList.forEach(student -> {
				StudentDto studentDto = StudentDto.builder()
											.id(student.getId())
											.name(student.getName())
											.email(student.getEmail())
											.phone(student.getPhone())
											.build();
				studentDtoList.add(studentDto);
			});
			
			studentResultDto.setList(studentDtoList);
			studentResultDto.setResult("success");
			
		} catch (Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		return studentResultDto;
	}

	@Override
	public StudentResultDto detailStudent(int id) {
		StudentResultDto studentResultDto = new StudentResultDto();
		try {
			Optional<Student> optionalStudent = studentRepoitory.findById(id);
			optionalStudent.ifPresentOrElse(
					student -> {
						StudentDto studentDto = StudentDto.builder()
								.id(student.getId())
								.name(student.getName())
								.email(student.getEmail())
								.phone(student.getPhone())
								.build();
						studentResultDto.setDto(studentDto);
						studentResultDto.setResult("success");
					}, // Present : 있으면 그대로 반환
					() -> {	studentResultDto.setResult("fail"); } // else: null 이면 빈칸
			);
		} catch (Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		return studentResultDto;
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
	public StudentResultDto insertStudent(StudentDto studentDto) {
		StudentResultDto studentResultDto = new StudentResultDto();
		
		// StudentDto -> student
		Student student = Student.builder()
								.name(studentDto.getName()) // id X why? id는 autogenerate (insert에서는 입력X)
								.email(studentDto.getEmail())
								.phone(studentDto.getPhone())
								.build();
		
		// 만든 student를 repository를 이용해 DB에 저장
		try {
			studentRepoitory.save(student);
			studentResultDto.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		
		return studentResultDto;
	}

	@Override
	public StudentResultDto updateStudent(StudentDto studentDto) {
		StudentResultDto studentResultDto = new StudentResultDto();
		
		// StudentDto -> student
		Student student = Student.builder()
								.id(studentDto.getId())  // update는 id 필요
								.name(studentDto.getName()) 
								.email(studentDto.getEmail())
								.phone(studentDto.getPhone())
								.build();
		
		// 만든 student를 repository를 이용해 DB에 저장
		try {
			studentRepoitory.save(student); // 달라진 점만 update됨
			studentResultDto.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		
		return studentResultDto;
	
	}

	@Override
	public StudentResultDto deleteStudent(int id) {
		StudentResultDto studentResultDto = new StudentResultDto();
		
		try {
			studentRepoitory.deleteById(id);
			studentResultDto.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		
		return studentResultDto;
	}

	@Override
	public StudentResultDto countStudent() {
		StudentResultDto studentResultDto = new StudentResultDto();
		
		try {
			Long count = studentRepoitory.count();
			studentResultDto.setCount(count);
			
			studentResultDto.setResult("success");
		} catch (Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		
		return studentResultDto;
	}

	public StudentResultDto listStudent(int pageNumber, int pageSize) {
		StudentResultDto studentResultDto = new StudentResultDto();
		
		try {
			Pageable pageable = PageRequest.of(pageNumber, pageSize);
			Page<Student> page = studentRepoitory.findAll(pageable);
			
			// student -> studentDto
//			List<Student> studentList = page.toList();
//			studentList.forEach( () -> );
			// 위를 간단하게 표현
			List<StudentDto> studentDtoList = new ArrayList<>();
			page.toList().forEach( student -> {
				StudentDto studentDto = StudentDto.builder()
						.id(student.getId())
						.name(student.getName())
						.email(student.getEmail())
						.phone(student.getPhone())
						.build();
				studentDtoList.add(studentDto);
			});
			
			studentResultDto.setList(studentDtoList);
			studentResultDto.setResult("success");

		} catch (Exception e) {
			e.printStackTrace();
			studentResultDto.setResult("fail");
		}
		
		
		return studentResultDto;

	}
	
}
