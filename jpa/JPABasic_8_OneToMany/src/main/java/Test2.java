import java.util.HashMap;
import java.util.List;
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
		
		// #1. Post 1건 find
//		Post p = em.find(Post.class, 1);
		
		// #2. Comment 1건 find
//		Comment c1 = em.find(Comment.class, 1);
		
		// #3. Post 1건 find 후 Post의 comments 사용
//		Post p = em.find(Post.class, 1);
//		List<Comment> comments = p.getComments();
//		
//		comments.forEach( comment -> System.out.println(comment) );
//		comments.forEach( comment -> System.out.println(comment) );
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title from Post p1_0 where p1_0.id=?
//		Hibernate: select c1_0.Post_id,c1_1.id,c1_1.content from Post_Comment c1_0 join Comment c1_1 on c1_1.id=c1_0.comments_id where c1_0.Post_id=?
//				Comment [id=1, content=코멘트1]
//				Comment [id=2, content=코멘트2]

		// #4. Post 1건 find 후 Post의 comments 사용 (fetchType : LAZY -> EAGER
//		Post p = em.find(Post.class, 1);
//		List<Comment> comments = p.getComments();
//		
//		try {
//			Thread.sleep(5000);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		comments.forEach( comment -> System.out.println(comment) );
////		Hibernate: select p1_0.id,p1_0.content,p1_0.title,c1_0.Post_id,c1_1.id,c1_1.content from Post p1_0 left join Post_Comment c1_0 on p1_0.id=c1_0.Post_id left join Comment c1_1 on c1_1.id=c1_0.comments_id where p1_0.id=?
////		Comment [id=1, content=코멘트1]
////		Comment [id=2, content=코멘트2]
		
		// #5. Post 1건 find 후 Comment 1개 생성, 연결, 생성된 comment persist
		Post p = em.find(Post.class, 1);
		Comment c3 = new Comment();
		c3.setContent("코멘트 3");
		
		p.getComments().add(c3);
		em.persist(c3);
//		Hibernate: select p1_0.id,p1_0.content,p1_0.title,c1_0.Post_id,c1_1.id,c1_1.content from Post p1_0 left join Post_Comment c1_0 on p1_0.id=c1_0.Post_id left join Comment c1_1 on c1_1.id=c1_0.comments_id where p1_0.id=?
//		Hibernate: insert into Comment (content) values (?)
//		Hibernate: delete from Post_Comment where Post_id=?
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
//		Hibernate: insert into Post_Comment (Post_id,comments_id) values (?,?)
		// post, comment, post-comment 모두 1번에 join으로
		// 새로운 c3 등록
		// post 객체와 연결된 post_comment 전체를 delete
		// post 객체와 연결된 cooment를 다시 insert
		// => post 객체와연결된 comment 전체insert (2건(기존) + 1건(new) = 3건)
		// 왜 delete->insert ?? 

		
		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



