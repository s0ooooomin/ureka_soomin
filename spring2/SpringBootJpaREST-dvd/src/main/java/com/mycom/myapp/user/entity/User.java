package com.mycom.myapp.user.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 로그인 시점 (Authorization 이 완료되는 시점) 에 Authentication 처리를 위해 
// 로그인한 사용자의 Role 을 가져오려고 한다. (즉시. EAGER fetch)
// User 중심으로 UserRole 사용
// User 엔티티가 중심 OwnerShip 을 가짐 => @OneToMany
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String phone;
	private String password;
	
	@OneToOne(fetch=FetchType.EAGER)
//	@OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
	@ToString.Exclude
	private UserRole userRole;
	
}
