package com.mycom.myapp.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.user.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{
	// 기본 CRUD 외
	// role name 으로 select. OptionalX (null일 경우가 불가능)
	UserRole findByName(String name);
}
