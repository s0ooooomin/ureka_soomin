package entity;

import entity.key.StudentKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {
	
	@EmbeddedId
	private StudentKey id; // StudentKey 가 embeddable이라 가능
	private String name;
	
	
	public StudentKey getId() {
		return id;
	}
	public void setId(StudentKey id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
