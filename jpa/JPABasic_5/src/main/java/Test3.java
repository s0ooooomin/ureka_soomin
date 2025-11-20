import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Employee;
import entity.Orders;
import entity.Product;
import entity.Student;
import entity.key.OrdersKey;
import entity.key.ProductKey;
import entity.key.StudentKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;

public class Test3 {

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
//			// 등록
//			Orders o1 = new Orders();
//			o1.setOrderId(1);
//			o1.setOrderDate("2025-11-17 15:50:00");
//			o1.setProductNo(1);
//			em.persist(o1);
			
//			// 조회
//			OrdersKey key1 = new OrdersKey();
//			key1.setOrderId(1);
//			key1.setOrderDate("2025-11-17 15:50:00");
//			Orders o2 = em.find(Orders.class, key1);
//			System.out.println(o2);
			
		}
		
		// Embedded
		{
//			// 등록
//			OrdersKey key2 = new OrdersKey();
//			key2.setOrderId(1);
//			key2.setOrderDate("2025-11-17 15:50:00");
//			
//			Orders o3 = new Orders();
//			o3.setId(key2);
//			o3.setProductNo(2);
//			em.persist(o3);
			
//			// 조회
			OrdersKey key2 = new OrdersKey();
			key2.setOrderId(1);
			key2.setOrderDate("2025-11-17 15:50:00");
			
			Orders o3 = em.find(Orders.class, key2);
			System.out.println(o3);
			
		}
		
		em.getTransaction().commit(); // 이 시점에 db작업
		em.close();

	}
}



