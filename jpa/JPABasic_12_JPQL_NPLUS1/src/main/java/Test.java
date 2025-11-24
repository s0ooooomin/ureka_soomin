import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;


import config.MyPersistenceUnitInfo;
import entity.Comment;
import entity.Team;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.TypedQuery;

/*
N+1 문제는 어떤 entity A의 목록 N개를 가져와서 사용하려 할 때, 
A 목록을 가져오는 select 1건, N개의 A와 연관관계의 entity를 추가로 가져오는 select N건 수행되는 현상
EAGER, LAZY 모두 발생 가능. (보통 LAZY가 많음)
다양한 해결책 中 join fetch!
 */


public class Test {

	public static void main(String[] args) {
		
		Map<String, String> props = new HashMap<>();
//		props.put("hibernate.hbm2ddl.auto", "update"); // create, update
		props.put("hibernate.show_sql", "true"); // 수행되는 sql 출력
		
		
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);
		
		EntityManager em = emf.createEntityManager();
		
		// fetch EAGER 
		
//		// #1. find()를 이용한 Comment 1건 조회
//		Comment comment = em.find(Comment.class, 1);

//		// #2. JPQL을 이용한 Comment 목록 조회
//		String jpql = "select c from Comment c";
//		List<Comment> list = em.createQuery(jpql, Comment.class).getResultList();
		// join 사용X Comment 목록만 가져옴
		// (fetch type == EAGER -> Comment, (연관관계)Post 추가 select
		// N+1은 아니지만 비효율적인 상황. (5건 중 2건 select하게 됨)
		// why? 필요한 객체가 파악될 때마다 추가로 가서 가져옴
//		Hibernate: select c1_0.id,c1_0.content,c1_0.post_id from Comment c1_0
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?


		// #3. JPQL을 Comment 목록, join 추가
//		String jpql = "select c from Comment c join c.post";
//		List<Comment> list = em.createQuery(jpql, Comment.class).getResultList();
		// join이 실행되지만, 조건에만 참여하고 select절에는 post 관련 컬럼 X
		// 여전히 Comment 객체 각각이 영속화되는 과정에서 필요한 Post를 파악하고 추가로 영속화 진행함
//		Hibernate: select c1_0.id,c1_0.content,c1_0.post_id from Comment c1_0 join Post p1_0 on p1_0.id=c1_0.post_id
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?

		
		// -> fetch JOIN
		// #3. JPQL을 Comment 목록, join 추가, fetch 추가
//		String jpql = "select c from Comment c join fetch c.post";
//		List<Comment> list = em.createQuery(jpql, Comment.class).getResultList();
		// Hibernate: select c1_0.id,c1_0.content,p1_0.id,p1_0.content,p1_0.title from Comment c1_0 join Post p1_0 on p1_0.id=c1_0.post_id
		// 중간에 p_id, p_content, p_title
		// join fetch를 통해서 조건 참여, select 절에 post 관련 컬럼 가져옴 -> 연관된 엔티티 함께 select해옴
//		Hibernate: select c1_0.id,c1_0.content,p1_0.id,p1_0.content,p1_0.title from Comment c1_0 join Post p1_0 on p1_0.id=c1_0.post_id

		
		// ====== fetch type : LAZY, @ManyToMany (Team - User) ========== // 
//		// #1. JPQL을 이용한 Team 목록
//		String jpql = "select t from Team t"; // ownership 가진쪽
//		List<Team> list = em.createQuery(jpql, Team.class).getResultList();
		// 연관관계에 있는 User 가져오지 X
		// Hibernate: select t1_0.id,t1_0.name from teams t1_0


		// #2. JPQL을 이용한 Team 목록, Team의 연관관계 Entity 사용 (Team별 회원수 출력)
//		String jpql = "select t from Team t"; // ownership 가진쪽
//		List<Team> list = em.createQuery(jpql, Team.class).getResultList();
//		list.forEach(team -> System.out.println(team.getUsers().size()));
		// LAZY라 team 목록만 select
		// -> team 목록을 가져오는 select & team의 수 (2개)만큼 연관된 User을 가져오는 select 2개 진행
		// -> LAZY 이므로 team 각각에서 연관관계의 Entity를 사용하는 시점에 영속화 시도.
//		Hibernate: select t1_0.id,t1_0.name from teams t1_0
//		Hibernate: select u1_0.team_id,u1_1.id,u1_1.name from teams_users u1_0 join users u1_1 on u1_1.id=u1_0.user_id where u1_0.team_id=?
//		3
//		Hibernate: select u1_0.team_id,u1_1.id,u1_1.name from teams_users u1_0 join users u1_1 on u1_1.id=u1_0.user_id where u1_0.team_id=?
//		2
		
		// #3. JPQL을 이용한 Team 목록, Team의 연관관계 Entity 사용 (Team별 회원수 출력)
//		String jpql = "select t from Team t join fetch t.users"; // ownership 가진쪽
//		List<Team> list = em.createQuery(jpql, Team.class).getResultList();
//		list.forEach(team -> System.out.println(team.getUsers().size()));
		// LAZY라 team 목록만 select 해야하지만, join fetch가 있어 연관관계에 있는 User를 함께 select 
		// -> select 절에 User의 컬럼이 포함
//		Hibernate: select t1_0.id,t1_0.name,u1_0.team_id,u1_1.id,u1_1.name from teams t1_0 join teams_users u1_0 on t1_0.id=u1_0.team_id join users u1_1 on u1_1.id=u1_0.user_id
//		3
//		2
		
		
		
		
		
		em.close();

	}
}



