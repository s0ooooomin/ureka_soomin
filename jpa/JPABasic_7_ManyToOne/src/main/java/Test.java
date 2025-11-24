import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Comment;
import entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;

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

// --------------------------1
		// @ManyToOne 
		// Join column 설정이 없으면 필드이름+_id로 컬럼 생성됨
		// private Post post;
		
//		// #1. 객체연결X , Post만 persist
//		em.persist(p);
		
//		// #2. 객체연결X , Comment1만 persist
//		em.persist(c1);
		
		// #3. 객체연결O , Post만
		// -> post만 올라감. comment X
//		c1.setPost(p);
//		c2.setPost(p);
//		em.persist(p);
		
//		// #4. 객체연결O , Comment 중심
//		c1.setPost(p);
//		em.persist(c1);
//		// ERROR!
		
//		// #5. 객체연결O , persist : c1 -> c2 -> p
		// 3건의 insert, 2건의 update
//		c1.setPost(p);
//		c2.setPost(p);
//		
//		em.persist(c1);
//		em.persist(c2);
//		em.persist(p);
		
//		Hibernate: insert into Comment (content,post_id) values (?,?) // persist c1 (post_id는 null)
//		Hibernate: insert into Comment (content,post_id) values (?,?) // persist c2 (post_id는 null)
//		Hibernate: insert into Post (content,title) values (?,?)	  // persist p
//		Hibernate: update Comment set content=?,post_id=? where id=?  // p의 존재가 저장되었으니 update
//		Hibernate: update Comment set content=?,post_id=? where id=?  // (동일)
		
//		// #6. 객체연결O, persist : p -> c1 -> c2
//		// 3건의 insert, 0건의 update
//		c1.setPost(p);
//		c2.setPost(p);
//		
//		em.persist(p);
//		em.persist(c1);
//		em.persist(c2);

//		Hibernate: insert into Post (content,title) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?) // 이미 p가 저장되어있으니 insert로 post_id 들어감
//		Hibernate: insert into Comment (content,post_id) values (?,?) // (동일)

		
// --------------------------2
//		@ManyToOne(cascade = CascadeType.PERSIST) // Join column 설정이 없으면 필드이름+_id로 컬럼 생성됨
//		private Post post;
		
		// #7. 객체연결O, persist : p -> c1 -> c2
		// 3건의 insert, 0건의 update
		c1.setPost(p);
		c2.setPost(p);
		
		em.persist(c1);
		em.persist(c2);
		
//		Hibernate: insert into Post (content,title) values (?,?)	 // c1과 연관관계에 있는 p가 먼저 persist됨
//		Hibernate: insert into Comment (content,post_id) values (?,?)
//		Hibernate: insert into Comment (content,post_id) values (?,?)

		

		
		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



