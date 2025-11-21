import java.time.LocalDate;
import java.util.HashMap;
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

//		// #1. group by Orders.orderDate
//		//	select order_date, count(customer_id) as customerCount from orders group by order_date order by order_date
//		String jpql = """ 
//				select 	o.orderDate, count(o.customer) as customerCount 
//				from 	Orders o
//				group by 	o.orderDate 
//				order by 	o.orderDate
//				""";
//		em.createQuery(jpql, Object[].class) // orderDate, count라 orders 객체는 X
//			.getResultList()
//			.forEach(objArray -> System.out.println( objArray[0] + " | " + objArray[1] ));
		
		
//		// #2. group by Orders.orderDate + Having
//		//	select order_date, count(customer_id) as customerCount from orders group by order_date having count(customer_id) > 2 order by order_date
//		// 	JPQL문에서는 having절에 alias X (ex. having customerCount > 2) 사용시 ERROR!
//		String jpql = 
//			""" 
//				select 	o.orderDate, count(o.customer) as customerCount 
//				from 	Orders o
//				group by 	o.orderDate
//				having	count(o.customer) > 2
//				order by 	o.orderDate
//			""";
//		em.createQuery(jpql, Object[].class) // orderDate, count라 orders 객체는 X
//		.getResultList()
//		.forEach(objArray -> System.out.println( objArray[0] + " | " + objArray[1] ));
//		// 결과
//		2025-04-13 | 3
//		2025-04-16 | 5
		
		// #3. group by Orders.orderDate + Having + Where (localdate)
		//	select order_date, count(customer_id) as customerCount from orders group by order_date having count(customer_id) > 2 order by order_date
		// 	-> orderDate parameter[localDate] 에 문자열 "2025-04-13"을 대입하면 ERROR!
		//		ERROR! org.hibernate.query.QueryArgumentException : did not match parameter type [java.time.LocalDate (n/a)]
//		String jpql = 
//			""" 
//				select 	o.orderDate, count(o.customer) as customerCount 
//				from 	Orders o
//				where	o.orderDate > :orderDate
//				group by 	o.orderDate
//			""";
//		em.createQuery(jpql, Object[].class) // orderDate, count라 orders 객체는 X
//		.setParameter("orderDate", LocalDate.of(2025, 4, 13) ) // ERROR! org.hibernate.query.QueryArgumentException : did not match parameter type [java.time.LocalDate (n/a)]
//		.getResultList()
//		.forEach(objArray -> System.out.println( objArray[0] + " | " + objArray[1] ));
		//결과
//		2025-04-16 | 5
//		2025-04-14 | 2

		// #4. group by Orders.orderDate + Having + order by
		//	select order_date, count(customer_id) as customerCount from orders group by order_date having count(customer_id) > 2 order by order_date
		// 	-> order by 절은 alias 사용O
//		String jpql = 
//			""" 
//				select 	o.orderDate, count(o.customer) as customerCount 
//				from 	Orders o
//				group by 	o.orderDate
//				order by 	customerCount desc
//			""";
//		em.createQuery(jpql, Object[].class) // orderDate, count라 orders 객체는 X
//		.getResultList()
//		.forEach(objArray -> System.out.println( objArray[0] + " | " + objArray[1] ));
		//결과
//		2025-04-16 | 5
//		2025-04-14 | 2
//		2025-04-13 | 3

		
		// #5. join + group by + where(join조건, product, customer) + order by
		/*
select 	order_date, sum(p.price) as femaleOrderSum
from 	orders o, customer c, product p
where	o.customer_id = c.id
and		o.product_id = p.id
and 	c.gender = 'f'
and		p.price >= 2000
group by order_date
order by femaleOrderSum desc;
		 */

//		String jpql = 
//			""" 
//				select 	o.orderDate, sum(p.price) as femaleOrderSum
//				from 	Orders o JOIN o.customer c
//								 JOIN o.product p
//				where	c.gender = 'f'
//				and		p.price >= 2000
//				group by 	o.orderDate
//				order by 	femaleOrderSum desc
//			""";
//		em.createQuery(jpql, Object[].class) // orderDate, count라 orders 객체는 X
//		.getResultList()
//		.forEach(objArray -> System.out.println( objArray[0] + " | " + objArray[1] ));
		//결과
//		2025-04-16 | 11000
//		2025-04-13 | 7000
//		2025-04-14 | 4000

		
		// #6. subquery + group by + where(join조건, product, customer) + order by
		/*
select 	order_date, sum(p.price) as femaleOrderSum
from 	orders o, customer c, product p
where	o.customer_id = c.id
and		o.product_id = p.id
and 	c.gender = 'f'
and		p.price >= 2000
group by order_date
order by femaleOrderSum desc;
		 */
		String jpql = 
//			""" 
//				select 	o.orderDate, sum(p.price) as femaleOrderSum
//				from 	Orders o
//				where	o.customer in ( select c from Customer c where c.gender = 'f' )
//				and		o.product in ( select p from Product p where p.price >= 2000 )
//				group by 	o.orderDate
//				order by 	femaleOrderSum desc
//			""";
				// p.price 불가
//			"""
//				select o.orderDate, sum(o.product.price) as femaleOrderSum
//				  from Orders o
//				 where o.customer in ( select c from Customer c where c.gender = 'f' )
//				   and o.product in ( select p from Product p where p.price >= 2000 )          
//				 group by o.orderDate
//				 order by femaleOrderSum desc
//			""";
			// sum(p.price) => 	sum(o.product.price) 로 변경, join Product 수행 확인
			""" 
				select 	o.orderDate, sum(o.product.price) as femaleOrderSum
				from 	Orders o
				where	o.customer.gender = 'f' 
				and		o.product.price >= 2000 
				group by 	o.orderDate
				order by 	femaleOrderSum desc
			""";

		em.createQuery(jpql, Object[].class) // orderDate, count라 orders 객체는 X
		.getResultList()
		.forEach(objArray -> System.out.println( objArray[0] + " | " + objArray[1] ));
		//결과
//		2025-04-16 | 11000
//		2025-04-13 | 7000
//		2025-04-14 | 4000

		
		
		em.close();		

	}
}













