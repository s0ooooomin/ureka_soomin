package com.mycom.myapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.entity.Student;
import com.mycom.myapp.service.StudentServiceFind;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor // DI(생성자주입)
public class StudentControllerFind {
	private final StudentServiceFind studentServiceFind;
	
	@GetMapping("/find/name")
	public List<Student> findByName(@RequestParam("name") String name) {
		return studentServiceFind.findByName(name);
	}
	
	@GetMapping("/find/emailandphone")
	public List<Student> findByEmailAndPhone(
			@RequestParam("email") String email, 
			@RequestParam("phone") String phone
			) {
		return studentServiceFind.findByEmailAndPhone(email, phone);
	}

	@GetMapping("/find/emailorphone")
	public List<Student> findByEmailOrPhone(
			@RequestParam("email") String email, 
			@RequestParam("phone") String phone
			) {
		return studentServiceFind.findByEmailOrPhone(email, phone);
	}
//	Hibernate: select s1_0.id,s1_0.email,s1_0.name,s1_0.phone from student s1_0 where s1_0.email=? or s1_0.phone=?

	@GetMapping("/find/namestartingwith")
	public List<Student> findByNameStartingWith(
			@RequestParam("name") String name) {
		return studentServiceFind.findByNameStartingWith(name);
	}
	@GetMapping("/find/emailendingwith")
	public List<Student> findByEmailEndingWith(
			@RequestParam("email") String email) {
		return studentServiceFind.findByEmailEndingWith(email);
	}
	@GetMapping("/find/phonecontaining")
	public List<Student> findByPhoneContaining(
			@RequestParam("phone") String phone) {
		return studentServiceFind.findByPhoneContaining(phone);
	}
	@GetMapping("/find/namelike")
	public List<Student> findByNameLike(
			@RequestParam("name") String name) {
		return studentServiceFind.findByNameLike(name);
	}
	
	@GetMapping("/find/orderbynamedesc")
	public List<Student> orderByNameDesc() {
		return studentServiceFind.findAllByOrderByNameDesc();
	}

	@GetMapping("/find/idbetween")
	public List<Student> findByIdBetween(
			@RequestParam("from") int from, 
			@RequestParam("to") int to) {
		return studentServiceFind.findByIdBetween(from, to);
	}
	
	
	
}
