package com.mycom.myapp.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Student {
	private int id;
	private String name;
}