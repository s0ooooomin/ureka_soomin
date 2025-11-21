import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/*
Join + Subquery  
 */
public class Test {

	public static void main(String[] args) {
		
		Map<String, String> props = new HashMap<>();
//		props.put("hibernate.hbm2ddl.auto", "update");
		props.put("hibernate.show_sql", "true");
		
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);
		
		EntityManager em = emf.createEntityManager();

		// #1. orders 목록만 select
		//     => 첫번째 jpql 은 N + 1 이슈. Orders 와 Customer, Product 가 모두 필요하다? 두번째 jpql join fetch
////		String jpql = "select o from Orders o";
//		String jpql = "select o from Orders o join fetch o.customer join fetch o.product ";
//		
//		em.createQuery(jpql, Orders.class)
//			.getResultList()
//			.forEach( order -> System.out.println(order) );
		
		// #2. order 와 다른 Entity 를 선택적으로 조인 (Orders, Product)
		//     => EAGER 로 인한 join 으로 가져오지 않은 Customer 가 추가로 영속화 <= 불필요한 select 수행
		//     => Orders 중심의 select with join 수행, LAZY 변경
		//     => EAGER 은 join fetch ( 모두 필요할 때 ) 외 LAZY + select + join ( 선택적으로 필요할 때 ) 명시함으로써 N + 1 해결 방법.
//		String jpql = "select o, p from Orders o, Product p where o.product = p";
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach( objArray -> System.out.println(objArray[0] + " | " + objArray[1]) );
		
		// #3. 다양한 join 문, 모두 동일한 결과
//		String jpql = "select o, p from Orders o, Product p where o.product = p"; // #2 와 동일
//		String jpql = "select o, p from Product p, Orders o where o.product = p"; // from 절 순서 변경
//		String jpql = "select p, o from Product p, Orders o where o.product = p"; // select 절 순서 변경
		
//		String jpql = "select o, p from Orders o inner join o.product p"; // Orders 중심 inner join 사용 (inner 생략 가능)
//		String jpql = "select o, p from Product p inner join p.orders o"; 
			// 오류 발생, Product 중심 <= Product 에 orders 필드 X (단방향)
			// => 양방향 ( Product 에 OneToMany(mappedBy="product") 추가 후 오류 없이 동일한 결과 확인
			// Customer 에도 동일한 설정
//		String jpql = "select o, c from Customer c inner join c.orders o"; 
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach( objArray -> System.out.println(objArray[0] + " | " + objArray[1]) );		
		
		// #4 3개 테이블
//		String jpql = """
//				select o, p, c 
//				  from Orders o, Product p, Customer c
//				 where o.product = p
//				   and o.customer = c
//				"""; 
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach( objArray -> System.out.println(objArray[0] + " | " + objArray[1] + " | " + objArray[2]) );	
		
		// #5 3개 테이블 + 일부 필드
		//    => 꼭 필요한 필드만 select 절에 포함, Dto 변환 검토
//		String jpql = """
//				select o.id, o.orderQuantity, p.id, p.price, c.name
//				  from Orders o, Product p, Customer c
//				 where o.product = p
//				   and o.customer = c
//				"""; 
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach( objArray -> System.out.println(objArray[0] + " | " + objArray[1] + " | " + objArray[2]+ " | " + objArray[3] + " | " + objArray[4]) );	
//
////		Hibernate: select o1_0.id,o1_0.order_quantity,p1_0.id,p1_0.price,c1_0.name from Orders o1_0,Product p1_0,Customer c1_0 where o1_0.product_id=p1_0.id and o1_0.customer_id=c1_0.id		
		
		// #6 left outer join
		//    => product 기준 orders 에 포함되지 않은 건을 함께 select ( 주문되지 않은 상품도 포함 )
//		String jpql = """
//				select p.id, p.name, o.orderQuantity, o.orderDate
//				  from Product p left join p.orders o
//				"""; 
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach( objArray -> System.out.println(objArray[0] + " | " + objArray[1] + " | " + objArray[2]+ " | " + objArray[3] ) );	

		
		// #7 join + 조건
		//    => Product 중심 jPQL (양방향), 각각의 조건을 추가하면서 결과 확인
//		String jpql = """
//				select p.name, p.price, o.orderQuantity, o.orderDate
//				  from Product p join p.orders o
//				 where p.price > 1000
//				   and o.orderQuantity = 3
//				"""; 
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach( objArray -> System.out.println(objArray[0] + " | " + objArray[1] + " | " + objArray[2]+ " | " + objArray[3] ) );	
		
		// parameter,....고려
		
		
		// Subquery
		
		// #8 subquery + where ( 주문된 상품의 가격이 4000 미만인 주문건들 조회 )
//		String jpql = """
//				select o, o.product.price
//				  from Orders o
//				 where o.product in ( select p from Product p where p.price < 4000 )
//				"""; 
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach( objArray -> System.out.println(objArray[0] + " | " + objArray[1] ) );	
		
		
		// #9 subquery + select ( 주문 + 주문한 고객명 )
		//    =>  subquery 에서 where o.customer = c 를 제외하면 java.sql.SQLException: Subquery returns more than 1 row 오류 발생
		String jpql = """
				select o, ( select c.name from Customer c where o.customer = c) customerName
				  from Orders o
				"""; 
		
		em.createQuery(jpql, Object[].class)
		.getResultList()
		.forEach( objArray -> System.out.println(objArray[0] + " | " + objArray[1] ) );			
		
		
		
		
		em.close();		

	}
}













