import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Employee;
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
		
		Employee emp = new Employee();
		// @GeneratedValue (X)
//		{
//			// #1
//			emp.setId(2);
//			emp.setName("홍길동");
//			emp.setAddress("대한민국 어디");
//			em.persist(emp);
//
//			// #2
//			emp.setId(2);
//			emp.setName("홍길동");
//			emp.setAddress("대한민국 어디");
//			em.persist(emp);
//			
//			// #3
//			Employee emp2 = new Employee();
//			emp2.setName("auto홍길동");
//			emp2.setAddress("auto대한민국");
//			em.persist(emp2);
//		}
		
		// @GeneratedValue (O)
		{
			emp.setName("auto홍길동");
			emp.setAddress("auto대한민국");
			em.persist(emp);
		}
		

		
		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



