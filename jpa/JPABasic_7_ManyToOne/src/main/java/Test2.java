import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;


import config.MyPersistenceUnitInfo;
import entity.Comment;
import entity.Post;
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
		
		// #1. Post만 find
//		Post p = em.find(Post.class, 1);
		
		// #2. Comment만 find (fetch EAGER 상태)
//		Comment c1 = em.find(Comment.class, 1);
//		Hibernate: select c1_0.id,c1_0.content,p1_0.id,p1_0.content,p1_0.title from Comment c1_0 
//					left join Post p1_0 on p1_0.id=c1_0.post_id // left join으로 연관관계에 있는 Post 가져옴 
//					where c1_0.id=? 
		
		// #3-1. Comment만 find (fetch LAZY 상태)
//		Comment c1 = em.find(Comment.class, 1);
//		Hibernate: select c1_0.id,c1_0.content,c1_0.post_id 
//					from Comment c1_0 where c1_0.id=? // Post 안 가져옴 (나중에 사용할때 가져옴)
		
//		// #3-2. Comment만 find (fetch LAZY 상태) - post 나중에 사용
//		Comment c1 = em.find(Comment.class, 1);
//		Post p = c1.getPost(); // select X
//		System.out.println(p);
		
//		Hibernate: select c1_0.id,c1_0.content,c1_0.post_id from Comment c1_0 where c1_0.id=?	// c1 find
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?		// p toString 할때 
//		Post [id=1, title=제목1, content=내용1]

		// #4. 새로운 Comment 생성 후 Post 연결, 새로운 Comment persist, fetch EAGER,LAZY 상관없이 Post
		Comment c1 = em.find(Comment.class, 1);
		Post p = c1.getPost();
		System.out.println(p);
		
		Comment c3 = new Comment();
		c3.setContent("코멘트3");
		c3.setPost(p);
		em.persist(c3);
		
//		Hibernate: select c1_0.id,c1_0.content,c1_0.post_id from Comment c1_0 where c1_0.id=?
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//		Post [id=1, title=제목1, content=내용1]
//		Hibernate: insert into Comment (content,post_id) values (?,?)

		
		
		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



