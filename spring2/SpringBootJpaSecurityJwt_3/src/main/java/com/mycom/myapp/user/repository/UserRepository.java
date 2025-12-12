package com.mycom.myapp.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mycom.myapp.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	// 기본적인 crud 관련 메소드는 이미 자동으로 처리

	Optional<User> findByEmail(String email);
}
