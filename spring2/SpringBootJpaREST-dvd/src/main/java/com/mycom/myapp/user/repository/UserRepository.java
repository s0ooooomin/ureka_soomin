package com.mycom.myapp.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mycom.myapp.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	// 기본적인 CRUD는 이미 처리됨 (repository)
	
	// 로그인은 email, password 검증, repository 단계에서는 email로 select
	// findByEmail 
	// -> 있으면 password 검증
	// -> 없으면 fail
	
	// 회원 도메인 메소드 (auth 쪽에서 이 메소드로 User entity 정보 얻음)
	Optional<User> findByPhone(String phone);
	
	
	
}
