package com.mycom.myapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mycom.myapp.entity.Student;
import com.mycom.myapp.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceFindImpl implements StudentServiceFind{

	private final StudentRepository studentRepository;
	@Override
	public List<Student> findByName(String name) {
		return studentRepository.findByName(name);
	}
	
	@Override
	public List<Student> findByEmailAndPhone(String email, String phone) {
		return studentRepository.findByEmailAndPhone(email, phone);
	}
	@Override
	public List<Student> findByEmailOrPhone(String email, String phone) {
		return studentRepository.findByEmailOrPhone(email, phone);
	}

	
	@Override
	public List<Student> findByNameStartingWith(String name) {
		return studentRepository.findByNameStartingWith(name);
	}
	@Override
	public List<Student> findByEmailEndingWith(String email) {
		return studentRepository.findByEmailEndingWith(email);
	}
	@Override
	public List<Student> findByPhoneContaining(String phone) {
		return studentRepository.findByPhoneContaining(phone);
	}

	@Override
	public List<Student> findByNameLike(String name) {
//		return studentRepository.findByNameLike(name); // == findByName (완전히 동일한 것만 (=))
//		return studentRepository.findByNameLike(name + "%"); // == Starting With
//		return studentRepository.findByNameLike("%" + name); // == ending With
		return studentRepository.findByNameLike("%" + name + "%"); // == containing 
		
	}

	@Override
	public List<Student> findAllByOrderByNameDesc() {
		return studentRepository.findAllByOrderByNameDesc();
	}

	@Override
	public List<Student> findByIdBetween(int from, int to) {
		return studentRepository.findByIdBetween(from, to);
	}
	
}
