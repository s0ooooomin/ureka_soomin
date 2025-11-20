package entity.key;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

//복합키를 표현하는 클래스
//1. public class
//2. 기본생성자 필수
//3. equals & hashCode Overriding (source generate 로 생성가능)
//4. implements Serializable <- 객체 직렬화 가능

@Embeddable // 이 key class를 가지는 엔티티에 포함됨
public class StudentKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code;
	private int number;
	
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
	@Override
	public int hashCode() {
		return Objects.hash(code, number);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentKey other = (StudentKey) obj;
		return Objects.equals(code, other.code) && number == other.number;
	}
	@Override
	public String toString() {
		return "StudentKey [code=" + code + ", number=" + number + "]";
	}
	
	
	
	
}
