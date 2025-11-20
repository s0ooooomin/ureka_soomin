package entity;

import entity.key.ProductKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

// code, number 가 복합키
@Entity
@IdClass(ProductKey.class)
public class Product {
	@Id // PK 설정
	private String code;
	@Id // PK 설정
	private int number;
	
	private String color;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
	@Override
	public String toString() {
		return "Product [code=" + code + ", number=" + number + ", color=" + color + "]";
	}
	

}
