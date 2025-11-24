import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;


import config.MyPersistenceUnitInfo;
import entity.Team;
import entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;

public class Test2 {

	public static void main(String[] args) {
		
		Map<String, String> props = new HashMap<>();
		// create -> 무조건 drop & create table..
		// update -> 테이블이 있으면 아무런 일 X, 없으면 create, 변화가 생기면 alter...
		props.put("hibernate.hbm2ddl.auto", "update"); // create, update
		props.put("hibernate.show_sql", "true"); // 수행되는 sql 출력
		
		
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);
		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		// #1. Team 1개 find
//		Team t1 = em.find(Team.class, 1);
		
//		// #2. Team 1개 find, toString() 호출
//		Team t1 = em.find(Team.class, 1);
//		System.out.println(t1);
//		// StackOverFlow, 무한 호출 
//		// why? team toString: user호출 -> users의 toString : team호출 -> ... (순환참조)
//		// user.java - toString 에서 team 호출 부분 삭제
		
//		// #3. User 1개 find
//		User u1 = em.find(User.class, 1);		
		
//		// #4. User 1개 find, User의 teams 사용 (toString()은 team을 제거한 상태)
//		User u1 = em.find(User.class, 2);
//		u1.getTeams().forEach(team -> System.out.println(team) );
		
		// #5. fetch EAGER 상태. Team 1개 find
		Team t1 = em.find(Team.class, 1);
		System.out.println(t1);
		// Hibernate: select t1_0.id,t1_0.name,u1_0.team_id,u1_1.id,u1_1.name 
		// FROM 	teams t1_0 
		// LEFT JOIN	teams_users u1_0 
		// ON 	t1_0.id=u1_0.team_id 
		// LEFT JOIN 	users u1_1 
		// ON 	u1_1.id=u1_0.user_id 
		// WHERE	t1_0.id=?
		
		
		
		
		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



