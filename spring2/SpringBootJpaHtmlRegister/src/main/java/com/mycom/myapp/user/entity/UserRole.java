package com.mycom.myapp.user.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 이 Role을 어떤 User가 가지고 있고, 이와 관련된 Back Office 업무를 수행한다면
// @ManyToMany로 가는 것이 바람직하나, 현재는 Authorization 용도로 사용

@Entity
@Getter
@Setter
@ToString

public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
}
