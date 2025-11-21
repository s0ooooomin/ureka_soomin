import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/*
GROUP BY, HAVING, ORDER BY 
 */
public class Test {

	public static void main(String[] args) {
		
		Map<String, String> props = new HashMap<>();
//		props.put("hibernate.hbm2ddl.auto", "update");
		props.put("hibernate.show_sql", "true");
		
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);
		
		EntityManager em = emf.createEntityManager();

//	createQuery() -> createNamedQuery() 사용
		
		// #1. Orders.findByCustomerName
//		em.createNamedQuery("Orders.findByCustomerName", Orders.class)
//			.setParameter("customerName", "고객1")
//			.getResultList()
//			.forEach(order -> System.out.println(order));
//		Hibernate: select o1_0.id,o1_0.customer_id,o1_0.order_date,o1_0.order_quantity,o1_0.product_id from Orders o1_0 join Customer c1_0 on c1_0.id=o1_0.customer_id where c1_0.name=?
//				Orders [id=1, orderQuantity=2, orderDate=2025-04-16]
//				Orders [id=6, orderQuantity=2, orderDate=2025-04-13]

		
		// #2. Orders.findByOrderDate
//		em.createNamedQuery("Orders.findByOrderDate", Orders.class)
//		.setParameter("orderDate", LocalDate.of(2025, 4, 16))
//		.getResultList()
//		.forEach(order -> System.out.println(order));

//		Hibernate: select o1_0.id,o1_0.customer_id,o1_0.order_date,o1_0.order_quantity,o1_0.product_id from Orders o1_0 where o1_0.order_date=?
//		Orders [id=1, orderQuantity=2, orderDate=2025-04-16]
//		Orders [id=2, orderQuantity=5, orderDate=2025-04-16]
//		Orders [id=3, orderQuantity=3, orderDate=2025-04-16]
//		Orders [id=4, orderQuantity=10, orderDate=2025-04-16]
//		Orders [id=5, orderQuantity=5, orderDate=2025-04-16]

		
		// #3. Orders.findByOrderRange
//		em.createNamedQuery("Orders.findByOrderRange", Orders.class)
//		.setParameter("startDate", LocalDate.of(2025, 4, 13))
//		.setParameter("endDate", LocalDate.of(2025, 4, 14))
//		.getResultList()
//		.forEach(order -> System.out.println(order));
//		Hibernate: select o1_0.id,o1_0.customer_id,o1_0.order_date,o1_0.order_quantity,o1_0.product_id from Orders o1_0 where o1_0.order_date between ? and ?
//		Orders [id=6, orderQuantity=2, orderDate=2025-04-13]
//		Orders [id=7, orderQuantity=5, orderDate=2025-04-13]
//		Orders [id=8, orderQuantity=3, orderDate=2025-04-13]
//		Orders [id=9, orderQuantity=10, orderDate=2025-04-14]
//		Orders [id=10, orderQuantity=5, orderDate=2025-04-14]
		
		
		// #4. Orders.findByProductPriceRange
//		em.createNamedQuery("Orders.findByProductPriceRange", Object[].class)
//		.setParameter("startPrice", 3000)
//		.setParameter("endPrice", 4000)
//		.getResultList()
//		.forEach( objArray -> System.out.println(objArray[0] + " | " + objArray[1]) );

//		Hibernate: select o1_0.id,o1_0.customer_id,o1_0.order_date,o1_0.order_quantity,o1_0.product_id,p1_0.price from Orders o1_0 join Product p1_0 on p1_0.id=o1_0.product_id where p1_0.price between ? and ?
//		Orders [id=1, orderQuantity=2, orderDate=2025-04-16] | 3000
//		Orders [id=2, orderQuantity=5, orderDate=2025-04-16] | 3000
//		Orders [id=6, orderQuantity=2, orderDate=2025-04-13] | 3000
//		Orders [id=7, orderQuantity=5, orderDate=2025-04-13] | 3000
//		Orders [id=3, orderQuantity=3, orderDate=2025-04-16] | 4000
//		Orders [id=4, orderQuantity=10, orderDate=2025-04-16] | 4000
//		Orders [id=8, orderQuantity=3, orderDate=2025-04-13] | 4000
//		Orders [id=9, orderQuantity=10, orderDate=2025-04-14] | 4000		
		
		
		// #5. Native Query // 바로 foreach-print (X) 일단 List<?>로 받기
		String sql = """
				select 	o.*
				from	orders o, customer c
				where	o.customer_id = c.id
				and		c.name = :customerName
				""";
		
		// 뭐가 들어올지 아직 몰라서 일단 와일드카드
		List<?> orderList = em.createNativeQuery(sql, Orders.class)
			.setParameter("customerName", "고객1")
			.getResultList();
		orderList.forEach( order -> System.out.println(order));
		
//		Hibernate: select 	o.*
//		from	orders o, customer c
//		where	o.customer_id = c.id
//		and		c.name = ?
//
//		Orders [id=1, orderQuantity=2, orderDate=2025-04-16]
//		Orders [id=6, orderQuantity=2, orderDate=2025-04-13]

		
		
		
		em.close();		

	}
}













