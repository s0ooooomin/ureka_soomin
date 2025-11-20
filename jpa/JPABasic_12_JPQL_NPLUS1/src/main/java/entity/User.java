package entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

//Ownership X
// 다대다
// user : team - M:M
@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@ManyToMany(mappedBy="users") // owner가 아니라 map 당함. 상대 (owner)가 가진 필드명 연결
	private List<Team> teams;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Team> getTeams() {
		return teams;
	}
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
//	@Override
//	public String toString() {
//		return "User [id=" + id + ", name=" + name + ", teams=" + teams + "]";
//	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
	
}
