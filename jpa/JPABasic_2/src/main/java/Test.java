import java.util.HashMap;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Test {

	public static void main(String[] args) {
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), new HashMap<>());
		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		// 작업 수행
		// #1. 반복문
//		for (int i = 1; i <= 3; i++) {
//			Product p = new Product();
//			p.setId(i);
//			p.setName("Phone" + i);
//			
//			em.persist(p);
//		}
		
		// #2
		Product p = new Product();
		p.setId(4);
		p.setName("Glasses");
		
		em.persist(p); 
		// watch로 영속화 이후 glasses로 변경 -> glasses
		// p 객체는 영속화 context에 담긴 상태 <= EntityManager가 관리 (영속화 시점X commit시점에 변경사항 저장 및 반영)
		
		
		// 작업 종료
		em.getTransaction().commit();
		em.close();
		
	}

}
