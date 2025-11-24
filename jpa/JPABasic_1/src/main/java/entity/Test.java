package entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// Spring 없는 JPA 사용 (spring은 자동으로 해주는 게 多)
public class Test {

	public static void main(String[] args) {

		// EntityManagerFactory -> EntityManager
		// persistence.xml의 unit이름을 넣으면 됨
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		
		// 작업 시작, 1차 캐시, 영속화 context 준비
		em.getTransaction().begin();
		
		// 작업 수행
		// #1. 1건
//		{
//			Product p = new Product();
//			p.setId(1);
//			p.setName("Book");
//			
//			em.persist(p); // em이 위의 코드 관리
//		}
		
		// #2. 여러건
		{
			Product p2 = new Product();
			p2.setId(2);
			p2.setName("Phone");

			Product p3 = new Product();
			p3.setId(3);
			p3.setName("Car");
			
			// 순서대로 아니어도 2-3으로 나옴
			// persist 순서대로 insert X
			// 영속화 context가 flush() ( commit()이 flush()호출) 되는 시점에 순서 결정됨 (순서 알수X, 일반적으로 id순인 것 같음)
			em.persist(p3); // em이 위의 p3 코드 관리
			em.persist(p2); // em이 위의 p2 코드 관리
		}

		
		// 작업 수행 후 commit 또는 rollback
		em.getTransaction().commit();
		
		em.close();
		
	}

}
