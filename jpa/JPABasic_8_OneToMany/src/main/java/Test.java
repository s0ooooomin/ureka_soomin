import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Comment;
import entity.Post;
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
		
		
		Post p = new Post();
		p.setTitle("제목1");
		p.setContent("내용1");
		
		Comment c1 = new Comment();
		c1.setContent("코멘트1");
		
		Comment c2 = new Comment();
		c2.setContent("코멘트2");
		
		// #1. post 1건 persist
//		em.persist(p);
		
		// #2. 객체 연결X , Comment 2건 persist
//		em.persist(c1);
//		em.persist(c2);
		
		// #3. 객체 연결X , post 1건 & Comment 2건 persist
//		em.persist(p);
//		em.persist(c1);
//		em.persist(c2);
		// 결과 : post 1건, comment 2건 insert (post-comment는 X)		
		
		// #4. 객체 연결O , post 1건 persist
//		p.setComments(List.of(c1, c2)); // List.of : c1, c2를 arraylist로 만들어서 return 
//		em.persist(p);
		// 결과 : ERROR! org.hibernate.TransientObjectException // post에 포함된 comment들이 영속화되지 않았다.
		
		// #5. 객체 연결O , post 1건 comment 2건 persist
//		p.setComments(List.of(c1, c2)); // List.of : c1, c2를 arraylist로 만들어서 return 
//		em.persist(p);
//		em.persist(c1);
//		em.persist(c2);
		// 결과 : insert Post 1건, Comment 2건, Post_Comment 2건

		// #6. 객체 연결O , CascadeType.PERSIST 추가 , post 1건 persist
		p.setComments(List.of(c1, c2)); // List.of : c1, c2를 arraylist로 만들어서 return 
		em.persist(p);
		// 결과 : insert Post 1건, Comment 2건, Post_Comment 2건
		
		

		

		
		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



