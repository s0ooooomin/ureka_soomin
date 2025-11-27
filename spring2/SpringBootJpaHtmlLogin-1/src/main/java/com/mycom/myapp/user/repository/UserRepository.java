package com.mycom.myapp.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	// 기본적인 CRUD 자동 처리
	
	// email 확인 -> password 일치 확인 -> 로그인
	Optional<User> findByEmail(String email);
	
}