import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Passport;
import entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.GeneratedValue;

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
		
		em.getTransaction().begin();
		
//		// #1. Person만 find
//		Person person = em.find(Person.class, 1);
//		System.out.println(person);
		
//		// #2. passport 만 find
//		Passport passport = em.find(Passport.class, 1);
//		System.out.println(passport);
		
//		// #3. OneToOne fetchType EAGER -> LAZY 변경, person 만 find
//		Person person = em.find(Person.class, 1);
//		System.out.println(person);
		
//		// #4. OneToOne fetchType EAGER -> LAZY 변경, person 만 find, Passport를 이후에 사용
//		Person person = em.find(Person.class, 1);
//		Passport passport = person.getPassport();
//		System.out.println(passport);
		
//		// #5. 4와 동일한 조건에서 Passport 를 사용하지 않고 person 객체를 출력하면 2번의 select 수행
//		//    => Passport join 없이 Person만 select
//		//    => Passport 를 가져올 때 Passport select
//		//    => Person 의 toString() 에서 Passport 사용 코드를 제거하면 Person 가져오는 select 한번만 수행	
//		Person person = em.find(Person.class, 1);
//		Passport passport = person.getPassport();
//		System.out.println(passport);
		
		// #6. LAZY & passport 만 find, passport를 통해 person 가져오기
		Passport passport = em.find(Passport.class, 1);
		Person person = passport.getPerson();
		System.out.println(person);
//		Hibernate: select p1_0.id,p1_0.number from Passport p1_0 where p1_0.id=?
//		Hibernate: select p1_0.id,p1_0.name,p1_0.passport from Person p1_0 where p1_0.passport=?
		
		
		
		
		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



