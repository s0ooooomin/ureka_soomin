import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
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

// ----------- 양방향 테스트 추가 코드 ---------------
		
		// #7. 객체 연결 O, CascadeType.Persist, person만 persist
//		person.setPassport(passport);
//		passport.setPerson(person);
//		em.persist(passport);
		// person의 passport == null
		
		// #8. 객체 연결( person 이용 ), Passport 에 CascadeType.PERSIST, Passport 만 persist
		//     => Person 의 passport 를 객체 연결하고, Passport 의 person 은 객체 연결 X
		//	   => Passport 를 영속화하면 cascade 되어야 하는 필드가 null 이므로 cascade 되지 X
		//     => Passport 1건만 insert
//				person.setPassport(passport);
//				
//				em.persist(passport);
////				Hibernate: insert into Passport (number) values (?)		

		// #9. 객체 연결( 양쪽 ), Passport 에 CascadeType.PERSIST, Passport 만 persist
		//     => Person 의 passport 를 객체 연결하고, Passport 의 person 도 객체 연결
		//     => Person, Passport 2건 insert
		//     => person 의 passport 가 null 이 아니다.
		person.setPassport(passport);
		passport.setPerson(person);
		
		em.persist(passport);
//				Hibernate: insert into Passport (number) values (?)
//				Hibernate: insert into Person (name,passport) values (?,?)	
		
		

		
		
		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



