import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;


import config.MyPersistenceUnitInfo;
import entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.TypedQuery;

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

//		// #1. find()
//		Customer c = em.find(Customer.class, 1);
//		System.out.println(c);
		
		// ------------ JPQL ------------ //
//		// #2. TypedQuery
		String jpql = "select c from Customer c";
		TypedQuery<Customer> query  = em.createQuery(jpql, Customer.class);
		List<Customer> customerList = query.getResultList();
		customerList.forEach(customer -> System.out.println(customer));
		
//		// #3. 개별 필드
//		String jpql = "select c.cust_id, c.cust_name, c.cust_phone from Customer c";
//		em.createQuery(jpql, Object[].class)
//			.getResultList()
//			.forEach(objArray-> System.out.println(objArray[0] + ", " + objArray[1] + ", " + objArray[2]));
		
//		// #4. where + like
//		String jpql = ""
//				+ "select 	c "
//				+ "from 	Customer c "
//				+ "where 	c.cust_name like :name"
//				+ "";
//		em.createQuery(jpql, Customer.class)
//			.setParameter("name", "%서%")
//			.getResultList()
//			.forEach(customer -> System.out.println(customer));
		
		// Aggregation Function (order_count 컬럼 추가)
//		// #5. count
//		String jpql = ""
//				+ "select 	count(c.cust_name) "
//				+ "from 	Customer c "
//				+ "";
//		Long cnt = em.createQuery(jpql, Long.class)
//						.getSingleResult();
//		System.out.println(cnt);
			
		// #6. avg (order_count)
//		String jpql = ""
//				+ "select 	avg(c.order_count) "
//				+ "from 	Customer c "
//				+ "";
//		Double avg = em.createQuery(jpql, Double.class)
//						.getSingleResult();
//		System.out.println(avg);
		
		// #7. sum() + min() + max()
		
		
		
		
		em.close();

	}
}



