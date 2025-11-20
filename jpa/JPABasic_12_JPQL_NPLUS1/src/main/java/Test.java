import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;


import config.MyPersistenceUnitInfo;
import entity.Comment;
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
		String jpql = "select c from Comment c";
		List<Comment> list = em.createQuery(jpql, Comment.class).getResultList();
		// join 사용X Comment 목록만 가져온 놈
		// (fetch type == EAGER -> Comment, (연관관계)Post 추가 select
		// N+1은 아니지만 비효율적인 상황. (5건 중 2건 select하게 됨)
		// why? 필요한 객체가 파악될 때마다 추가로 가서 가져옴
		
		
		
		
		em.close();

	}
}



