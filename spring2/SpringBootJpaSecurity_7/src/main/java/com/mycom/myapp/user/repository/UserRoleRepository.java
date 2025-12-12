package com.mycom.myapp.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.user.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
	// 기본적인 crud 메소드는 자동 구현
	
	// role 이름으로 select, Optional X, 
	UserRole findByName(String name);
}
