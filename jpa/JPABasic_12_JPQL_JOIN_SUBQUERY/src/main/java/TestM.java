import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mysql.cj.Query;

import configM.MyPersistenceUnitInfo;
import entityM.Orders;
import entityM.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.TypedQuery;

/*
JOIN + SubQuery
 */
public class TestM {

	public static void main(String[] args) {
		
		Map<String, String> props = new HashMap<>();
		// create -> 무조건 drop & create table..
		// update -> 테이블이 있으면 아무런 일 X, 없으면 create, 변화가 생기면 alter...
		props.put("hibernate.hbm2ddl.auto", "update"); // create, update
		props.put("hibernate.show_sql", "true"); // 수행되는 sql 출력
		
		
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);
		
		EntityManager em = emf.createEntityManager();
		
		// #1. orders 목록 select
//		String jpql = "select o from Orders o join fetch o.customer join fetch o.product";
//		em.createQuery(jpql, Orders.class).getResultList().forEach(order -> System.out.println(order) );
		// N+1 이슈!! Orders와 Customer, Product 가 모두 필요 -> join fetch
		// 안하면 계속 select 호출
		
		
		// #2. order와 다른 entity를 선택적으로 조인 (orders, product)
		// -> EAGER로 인한 join 으로 가져오지 않은 Customer 가 추가로 영속화 : 불필요한 select 수행
		// -> orders 중심의 select with join 수행 & LAZY 변경
		// -> EAGER은 join fetch (모두 필요할 때) 외 LAZY + select + join 명시. ( N+1 해결 )
//		String jpql = "select o, p from Orders o, Product p where o.product = p"; // 가져온 order의 product가 p와 같을경우만
//		em.createQuery(jpql, Object[].class)
//			.getResultList()
//			.forEach(objArray -> System.out.println(objArray[0] + " " + objArray[1]));
		
		// #3. 다양한 join 문, 모두 동일한 결과
//		String jpql = "select o, p from Orders o, Product p where o.product = p"; // #2와 동일
//		String jpql = "select o, p from Product p, Orders o where o.product = p"; // from 절 순서 변경
//		String jpql = "select p, o from Product p, Orders o where o.product = p"; // select 절 순서 변경

//		String jpql = "select o, p from Orders o inner join o.product p"; // Orders 중심, inner join 사용 (inner 생략 O)
//		String jpql = "select o, p from Product p inner join p.orders o"; // product 중심, 단방향 오류 발생 (Product 에 orders 필드 X)
//		// => 양방향으로? (Product 에 OneToMany(mappedBy)) 추가 후 오류 없이 동일한 결과
//		String jpql = "select o, c from Customer c inner join c.orders o";
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach(objArray -> System.out.println(objArray[0] + " " + objArray[1]));
		
//		// #4. 3개 테이블
//		String jpql = """
//				select 	o, p, c 
//				from	Orders o, Product p, Customer c
//				where	o.product = p
//				and 	o.customer = c
//				""";
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach(objArray -> System.out.println(objArray[0] + " " + objArray[1] + " " + objArray[2]));
		
		
		// #5. 3개 테이블 - 일부 컬럼만
		String jpql = """
		select o.id, o.orderQuantity, p.id, p.price, c.name
		  from Orders o, Product p, Customer c
		 where o.product = p
		   and o.customer = c
		"""; 
		em.createQuery(jpql, Object[].class)
		.getResultList()
		.forEach( objArray -> System.out.println(objArray[0] + " | " + objArray[1] + " | " + objArray[2]+ " | " + objArray[3] + " | " + objArray[4]) );	

		
//		// #6. left join
//		String jpql = """
//				select 	p.id, p.name, o.orderQuantity, o.orderDate
//				from	Product p left join p.orders o 
//				""";
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach(objArray -> System.out.println(objArray[0] + " | " + objArray[1] + " | " + objArray[2] + " | " + objArray[3]));
		
//		// #6. join + 조건
//		String jpql = """
//				select 	p.name, p.price, o.orderQuantity, o.orderDate
//				from	Product p left join p.orders o 
//				where	p.price > 1000
//				and		o.orderQuantity = 3
//				""";
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach(objArray -> System.out.println(objArray[0] + " | " + objArray[1] + " | " + objArray[2] + " | " + objArray[3]));
		
		
// <============ subquery ==================> //
//		// #8. subquery + where (주문된 상품의 가격이 4000원 미만인 주문건 조회)
//		String jpql = """
//				select	o, o.product.price
//				from	Orders o
//				where	o.product in ( select p from Product p where p.price <= 4000 )
//				""";
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach(objArray -> System.out.println(objArray[0] + " | " + objArray[1] ));
		
//		// #9. subquery + select (주문 + 주문한 고객명)
//		String jpql = """
//				select	o, (select c.name from Customer c where o.customer = c) customerName
//				from	Orders o
//				""";
//		
//		em.createQuery(jpql, Object[].class)
//		.getResultList()
//		.forEach(objArray -> System.out.println(objArray[0] + " | " + objArray[1] ));
		
		
		em.close();

	}
}



