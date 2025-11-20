package entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

// Person <-> Passport 양방향 관계
// Passport -> Person O
// 양방향 관계는 꼭 필요한 경우에만 설정
// 구현하고자 하는 Business Logic 에서 Person -> Passport + Passport -> Person 도 꼭 필요할 경우만!!

@Entity
public class Passport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String number; // 여권번호
	
//	@OneToOne(mappedBy="passport")
//	private Person person;

//	@OneToOne(mappedBy="passport", cascade=CascadeType.PERSIST)
//	private Person person;

	@OneToOne(mappedBy="passport", cascade=CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Person person;
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
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
		return "Passport [id=" + id + ", number=" + number + ", person=" + person + "]";
	}

	
}
