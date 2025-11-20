package entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

// Ownership O (Owning entity)
//다대다
//team : user - M:M
@Entity
@Table(name="teams")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
//	@ManyToMany(cascade = CascadeType.PERSIST)
//	@JoinTable(
//		name="teams_users",
//		joinColumns = @JoinColumn(name="team_id"), // 현재 join될 상황인 테이블 : teams (user에 있는 List)
//		inverseJoinColumns = @JoinColumn(name="user_id") // 엔티티와 관계 대응되는, 연결될 컬럼
//			)
//	private List<User> users;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
			name="teams_users",
			joinColumns = @JoinColumn(name="team_id"), // 현재 join될 상황인 테이블 : teams (user에 있는 List)
			inverseJoinColumns = @JoinColumn(name="user_id") // 엔티티와 관계 대응되는, 연결될 컬럼
			)
	private List<User> users;
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
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", users=" + users + "]";
	}
	
}
