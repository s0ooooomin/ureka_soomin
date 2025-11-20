package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// IdCard -> Person X
@Entity
public class IdCard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String number;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return "IdCard [id=" + id + ", number=" + number + "]";
	}
	
	
	
}
