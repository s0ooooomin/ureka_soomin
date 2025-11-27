import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mysql.cj.Query;

import config.MyPersistenceUnitInfo;
import entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.TypedQuery;

public class Test {

	public static void main(String[] args) {
		
		Map<String, String> props = new HashMap<>();
		// create -> 무조건 drop & create table..
		// update -> 테이블이 있으면 아무런 일 X, 없으면 create, 변화가 생기면 alter...
		props.put("hibernate.hbm2ddl.auto", "update"); // create, update
		props.put("hibernate.show_sql", "true"); // 수행되는 sql 출력
		
		
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);
		
		EntityManager em = emf.createEntityManager();
		
//		em.getTransaction().begin(); // select는 commit 필요X
		
		// #1. vs find()
//		Product p = em.find(Product.class, 7);
//		System.out.println(p);
		
		//-------------JPQL-----------------//
//		// #2. TypedQuery (Query는 생략)
//		String jpql = "select p from Product p"; // jpql 문자열
//		TypedQuery<Product> query = em.createQuery(jpql, Product.class); // jpql을 이용한 TypedQuery 객체 생성
//		// generic에는 entity
//		List<Product> productList = query.getResultList();
//		productList.forEach(product -> System.out.println(product) );
		
		// #3. 개별 필드 ( id, name, price )
		String jpql = "select p.id, p.name, p.price from Product p"; // jpql 문자열
		em.createQuery(jpql, Object[].class)
			.getResultList()
			.forEach(objArray -> System.out.println(objArray[0] + ", " + objArray[1] + ", " + objArray[2]) ); // 중간에 연결되는 부분 삭제
		
//		// #4. 전체 필드 + where (price > 2000)
//		String jpql = ""
//				+ "select	p "
//				+ "from 	Product p "
//				+ "where 	p.price > :price "
//				+ "and 		p.quantity >= :quantity"; // jpql 문자열 (뒤는 넘겨받은 변수 *기존에 ? 이런 식으로 썼던거)
//		em.createQuery(jpql, Product.class)
//			.setParameter("price", 2000)
//			.setParameter("quantity", 20)
//			.getResultList()
//			.forEach(product -> System.out.println(product) ); // 중간에 연결되는 부분 삭제

		// #6. 전체 필드 + where + and + parameter using index
//		String jpql = ""
//				+ "select	p "
//				+ "from 	Product p "
//				+ "where 	p.price > ?1 "
//				+ "and 		p.quantity >= ?2"; // jpql 문자열 (뒤는 넘겨받은 변수 *기존에 ? 이런 식으로 썼던거)
//		em.createQuery(jpql, Product.class)
//			.setParameter(1, 2000)
//			.setParameter(2, 20)
//			.getResultList()
//			.forEach(product -> System.out.println(product) ); // 중간에 연결되는 부분 삭제
		
//		// #7. 전체 필드 + where + and + parameter + like
//		String jpql = """
//				select	p 
//				from 	Product p 
//				where 	p.price > :price 
//				and 		p.country like :country
//				"""; // jpql 문자열 (뒤는 넘겨받은 변수. 기존에 ? 이런 식으로 썼던거)
//		em.createQuery(jpql, Product.class)
	//		.setParameter("price", 2000)
	//		.setParameter("country", "%k%")
	//		.getResultList()
	//		.forEach(product -> System.out.println(product) ); // 중간에 연결되는 부분 삭제
		
//		
		
		 // Arregation Function [집계함수]
//		// #8. count
//		String jpql = """
//				select	count(p)
//				from 	Product p
//				"""; // jpql 문자열 (뒤는 넘겨받은 변수. 기존에 ? 이런 식으로 썼던거)
//		Long cnt = em.createQuery(jpql, Long.class) // 위에서는 반환받는 게 p(Project)니까 Project.class. 여기는 count를 받아야하니까 숫자, Long
//						.getSingleResult();
//		System.out.println(cnt);

//		// #8-2. id 9 추가 (country == null상태)
//		String jpql = """
//				select	count(p.country)
//				from 	Product p
//				"""; // jpql 문자열 (뒤는 넘겨받은 변수. 기존에 ? 이런 식으로 썼던거)
//		Long cnt = em.createQuery(jpql, Long.class) // 위에서는 반환받는 게 p(Project)니까 Project.class. 여기는 count를 받아야하니까 숫자, Long
//						.getSingleResult();
//		System.out.println(cnt);

//		
//		// #9. avg
//		String jpql = """
//				select	avg(p.price)
//				from 	Product p
//				"""; // jpql 문자열 (뒤는 넘겨받은 변수. 기존에 ? 이런 식으로 썼던거)
//		Double avg = em.createQuery(jpql, Double.class) // 위에서는 반환받는 게 p(Project)니까 Project.class. 여기는 count를 받아야하니까 숫자, Long
//				.getSingleResult();
//		System.out.println(avg);
		
		
		// #10. sum() + min() + max() , quantity
//		String jpql = """
//				select	sum(p.quantity), min(p.quantity), max(p.quantity)
//				from 	Product p
//				"""; // jpql 문자열 (뒤는 넘겨받은 변수. 기존에 ? 이런 식으로 썼던거)
//		Object[] objArray = em.createQuery(jpql, Object[].class) // 위에서는 반환받는 게 p(Project)니까 Project.class. 여기는 count를 받아야하니까 숫자, Long
//				.getSingleResult();
//		System.out.println(objArray[0] + ", " + objArray[1] + ", " + objArray[2]);

		
//		// #11. sum() + min() + max() + groupby
//		// select country, sum(quantity)
//		String jpql = """
//				select	p.country, sum(p.quantity), min(p.quantity), max(p.quantity)
//				from 	Product p
//				group by	p.country
//				"""; // jpql 문자열 (뒤는 넘겨받은 변수. 기존에 ? 이런 식으로 썼던거)
//		List<Object[]> objArrayList = em.createQuery(jpql, Object[].class) // 한 줄 (ex.count -> 8)이 아니기 때문에 single 불가
//				.getResultList();
//		objArrayList.forEach(objArray -> {
//			System.out.println(objArray[0] + ", " + objArray[1] + ", " + objArray[2] + ", " + objArray[3]);
//		});
		

//		// #12. 내장function (DBMS마다 다 다름) mysql concat(), ifnull(),...
//		// select concat(name, "-", price) name_price from Product p from product
//		String jpql = """
//				select concat(p.name, "-", p.price) name_price 
//				from Product p
//				"""; // jpql 문자열 (뒤는 넘겨받은 변수. 기존에 ? 이런 식으로 썼던거)
//		em.createQuery(jpql, String.class) // 한 줄 (ex.count -> 8)이 아니기 때문에 single 불가
//				.getResultList()
//				.forEach( name_price -> {
//			System.out.println(name_price);
//		});

		// #12-2. 내장function (DBMS마다 다 다름) mysql concat(), ifnull(),...
		// select concat(name, "-", price) name_price from Product p from product
//		String jpql = """
//				select p.name, p.price, ifnull(p.country, 'unknown') country
//				from Product p
//				"""; // jpql 문자열 (뒤는 넘겨받은 변수. 기존에 ? 이런 식으로 썼던거)
//		em.createQuery(jpql, Object[].class) // 한 줄 (ex.count -> 8)이 아니기 때문에 single 불가
//			.getResultList()
//			.forEach( objArray -> {
//				System.out.println(objArray[0] + ", " + objArray[1] + "," + objArray[2]);
//			});

		
		
//		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



