import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Team;
import entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;

public class Test {

	public static void main(String[] args) {
		
		Map<String, String> props = new HashMap<>();
		// create -> 무조건 drop & create table..
		// update -> 테이블이 있으면 아무런 일 X, 없으면 create, 변화가 생기면 alter...
		props.put("hibernate.hbm2ddl.auto", "create"); // create, update
		props.put("hibernate.show_sql", "true"); // 수행되는 sql 출력
		
		
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);
		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		// #0. 구성확인 (아무것도 안 적은 상태로)
		
		User u1 = new User();
		u1.setName("회원1");
		User u2 = new User();
		u2.setName("회원2");
		
		Team t1 = new Team();
		t1.setName("팀1");
		Team t2 = new Team();
		t2.setName("팀2");
		
		// #1. 객체연결X , User 2개 persist
//		em.persist(u1); 
//		em.persist(u2); 
		// user 2건 insert
//		Hibernate: insert into users (name) values (?)
//		Hibernate: insert into users (name) values (?)
		
		// #2. 객체연결X , team 2개 persist
//		em.persist(t1); 
//		em.persist(t2); 
		// team 2건 insert

		// #3. 객체연결X, User 2개 team 2개 persist
//		em.persist(u1); 
//		em.persist(u2);
//		em.persist(t1); 
//		em.persist(t2); 
		// user 2건 team 2건 각 테이블에 insert
		
		// #4. 객체연결O (Team에 User 연결), team 2개만 persist
//		t1.setUsers(List.of(u1, u2));
//		t2.setUsers(List.of(u2));
//		em.persist(t1); 
//		em.persist(t2); 
		// ERROR!  org.hibernate.TransientObjectException : team에 연결된 user가 persist 되지 않음
		
//		// #4-2. 객체연결O (User에 Team 연결), user 2개만 persist
//		u1.setTeams(List.of(t1,t2));
//		u2.setTeams(List.of(t2));
//		em.persist(u1);
//		em.persist(u2);
//		// 오류X user 2개만 insert (why? M:M은 non-owning entity를 통한 영속화는 연관관계의 영속화X)
		
//		// #5. 객체연결O (Team에 User 연결), team 2개 User 2개 persist
//		t1.setUsers(List.of(u1, u2));
//		t2.setUsers(List.of(u2));
//		em.persist(t1); 
//		em.persist(t2); 
//		em.persist(u1); 
//		em.persist(u2); 
		
//		// #5-2. 5번 반대 (User에 Team 연결)
//		u1.setTeams(List.of(t1,t2));
//		u2.setTeams(List.of(t2));
//		em.persist(t1);
//		em.persist(t2);
//		em.persist(u1);
//		em.persist(u2);
//		// team 2개 user 2개 insert (teams_users X)
		
//		// #9. 객체연결O (Team에 User 연결), team만 persist
		t1.setUsers(List.of(u1, u2));
		t2.setUsers(List.of(u2));
		em.persist(t1); 
		em.persist(t2); 
		
		
		
		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



