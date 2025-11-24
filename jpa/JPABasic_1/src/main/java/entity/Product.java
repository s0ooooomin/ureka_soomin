package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // 이 어노테이션을 통해 테이블과 연결됨을 의미
public class Product {
	@Id // PK에 해당함을 의미
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String string) {
		this.name = string;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}
}
