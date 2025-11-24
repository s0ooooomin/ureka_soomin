package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
//@Table(name = "emp") // 테이블명이 자바와 다를때
public class Employee {
	
	// @Id만 있으면 DB의 기본 id 관리 정책을 따름 (JPA가 관여X)
	// auto increment 없으면 id 제공 필요
	// auto increment 있으면 id 제공 필요X
	@Id 
//	@Column(name="emp_id") // column명이 자바와 다를때  	
//	@GeneratedValue(strategy = GenerationType.AUTO) // hibernate에 위임
//	@GeneratedValue(strategy = GenerationType.IDENTITY) // hibernate가 id 제공
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@GeneratedValue(strategy = GenerationType.TABLE)
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String name;
	private String address;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
	
	
	
	
	
}
