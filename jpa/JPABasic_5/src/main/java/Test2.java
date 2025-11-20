import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Employee;
import entity.Product;
import entity.Student;
import entity.key.ProductKey;
import entity.key.StudentKey;
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
		
		// @IdClass
		{
//			Product p = new Product();
//			p.setCode("uplus");
//			p.setNumber(1);
//			p.setColor("blue");
//			
//			em.persist(p);
			
//			// 조회
//			// find(entity class, entity key)
//			ProductKey key = new ProductKey();
//			key.setCode("uplus");
//			key.setNumber(1);
//			Product p = em.find(Product.class, key);
//			System.out.println(p);
		}
		
		// Embedded
		{
//			StudentKey key = new StudentKey();
//			key.setCode("uplus");
//			key.setNumber(1);
//
//			Student s = new Student();
//			s.setId(key);
//			s.setName("홍길동");
//			
//			em.persist(s);
			
			// 조회
			// find(entity class, entity key)
			StudentKey key = new StudentKey();
			key.setCode("uplus");
			key.setNumber(1);
			
			Student s = em.find(Student.class, key);
			System.out.println(s);
			
		}
		
		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



