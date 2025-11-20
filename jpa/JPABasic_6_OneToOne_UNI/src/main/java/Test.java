import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.IdCard;
import entity.Passport;
import entity.Person;
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
		
		Person person = new Person();
		person.setName("홍길동");
		
		Passport passport = new Passport();
		passport.setNumber("KOR1234");
		
//		// #1. 객체 연결 X 각각 따로 persist
//		em.persist(person);
//		em.persist(passport);
//		// person.passport == null
		
//		// #2. 객체 연결 O, person만 persist
//		person.setPassport(passport);
//		em.persist(person);
//		// ERROR! 
		
//		// #3. 객체 연결 O, person, passport 모두 persist
//		person.setPassport(passport);
//		em.persist(person);
//		em.persist(passport);
		
//		// #4. 객체 연결 O, passport->person 순서대로 persist
//		person.setPassport(passport);
//		
//		em.persist(passport);
//		em.persist(person);
		
//		// #5. 객체 연결 O, passport 만 persist
//		person.setPassport(passport);
//		em.persist(passport);
		
//		// #6. 객체 연결 O, cascadeType.Persist, person만 persist
//		person.setPassport(passport);
//		em.persist(person);
		
// ------------------------------ 실습 시간
		IdCard card = new IdCard();
		card.setNumber("001122-4123456");
		
		// #1. 객체 연결 X 각각 따로 persist
//		em.persist(card);
		
//		// #2. 객체 연결 O, person만 persist
//		person.setIdcard(card);
//		em.persist(person);
//		// ERROR! 
		
//		// #3. 객체 연결 O, person, passport 모두 persist
//		person.setPassport(passport);
//		em.persist(person);
//		em.persist(passport);
		
//		// #4. 객체 연결 O, passport->person 순서대로 persist
//		person.setPassport(passport);
//		
//		em.persist(passport);
//		em.persist(person);
		
//		// #5. 객체 연결 O, passport 만 persist
//		person.setPassport(passport);
//		em.persist(passport);
		
//		// #6. 객체 연결 O, cascadeType.Persist, person만 persist
//		person.setPassport(passport);
//		em.persist(person);
		
		
		
		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



