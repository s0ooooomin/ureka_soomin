import java.util.HashMap;
import java.util.Map;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Test {

	public static void main(String[] args) {
		
		Map<String, String> props = new HashMap<>();
		// create -> 무조건 drop & create table..
		// update -> 테이블이 있으면 아무런 일 X, 없으면 create, 변화가 생기면 alter...
		props.put("hibernate.hbm2ddl.auto", "update"); // create, update
//		props.put("hibernate.hbm2ddl.auto", "create"); // create, update
		props.put("hibernate.show_sql", "true"); // 수행되는 sql 출력
		
		
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), props);
		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();

		// DB 작업
		// #1. ddl option update ( 현재 엔티티와 동일한 테이블 존재 ) => 아무일 X
		// #2. ddl option update ( 테이블 존재 X ) => 테이블 새로 생성
		//     Hibernate: create table Employee (id integer not null, address varchar(255), name varchar(255), primary key (id)) engine=InnoDB
		// #3. ddl option update ( 테이블 존재하지만 컬럼이 다르다. ) => 컬럼을 추가 / 삭제
		//     Hibernate: alter table Employee add column address varchar(255)
		
		// #4. ddl option create (테이블 존재) => 기존 테이블 drop & 새로 생성 (초기화 느낌)

		// ddl option update 상태
		// #5. persist
//		{
//			Employee e = new Employee();
//			e.setId(1);
//			e.setName("홍길동");
//			e.setAddress("서울");
//			em.persist(e);
//		}
		
//		// #6. find
//		{
//			Employee e = em.find(Employee.class, 1);
//			System.out.println(e);
//		}
//		// #7. find & update
//		{
//			Employee e = em.find(Employee.class, 1);
//			System.out.println(e);
//			
//			e.setAddress("부산");
//			System.out.println(e);
//			
//		}
//		// #8. merge
		{
			// 없는 경우
//			Employee e = new Employee();
//			e.setId(2);
//			e.setName("이길동");
//			e.setAddress("제주");
//			em.merge(e);
//			System.out.println(e);
//			// Hibernate: select e1_0.id,e1_0.address,e1_0.name from Employee e1_0 where e1_0.id=?
//			// Employee [id=2, name=이길동, address=제주]
//			// Hibernate: insert into Employee (address,name,id) values (?,?,?)

//			// 있는 경우
//			Employee e = new Employee();
//			e.setId(2);
//			e.setName("이길동");
//			e.setAddress("제주2");
//			em.merge(e);
//			System.out.println(e);
			
			// select
			// update
			
		}
		// #9. remove
		{
			Employee e = em.find(Employee.class, 1);
			em.remove(e);
			
			// Hibernate: select e1_0.id,e1_0.address,e1_0.name from Employee e1_0 where e1_0.id=?
			// Hibernate: delete from Employee where id=?
			
			try {
				Thread.sleep(5000);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// Hibernate: select e1_0.id,e1_0.address,e1_0.name from Employee e1_0 where e1_0.id=?
			// --delay(5000)--
			// Hibernate: delete from Employee where id=?

		}
		
		
		
		
//		em.getTransaction().commit(); // 이 시점에 update O
		em.getTransaction().commit(); // 이 시점에 db작업
		
		em.close();

	}
}



