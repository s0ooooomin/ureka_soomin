import java.util.HashMap;

import org.hibernate.jpa.HibernatePersistenceProvider;

import config.MyPersistenceUnitInfo;
import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Test {

	public static void main(String[] args) {
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), new HashMap<>());
		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		// db 작업 수행
		// #1. persist (영속화)
		// entity 폴더의 파일에서 @Table, @Column 어노테이션을 이용해 테이블명, 컬럼명을 다르게 설정 가능
//		{
//			Employee e1 = new Employee();
//			e1.setId(1);
//			e1.setName("홍길동");
//			e1.setAddress("서울");
//			
//			em.persist(e1);
		
//			Employee e2 = new Employee();
//			e2.setId(1);
//			e2.setName("이길동");
//			e2.setAddress("대전");
//			
//			em.persist(e2);
//		}
		
		// #2-1. find()
		// 테이블 데이터 -> 객체화 & 영속화
//		{
//			Employee e = em.find(Employee.class, 1); // id가 1인 것을 찾아 객체화 & 영속화
//			System.out.println(e); // 객체화했기 때문에 toString()호출
//			Employee e2 = em.find(Employee.class, 2); // id가 2 (없는 데이터)
//			System.out.println(e2); // 객체화했기 때문에 toString()호출
//		}
		// #2-2. find()
		// 테이블 데이터 -> 객체화 & 영속화
		{
			// 객체화 & 영속화
//			Employee e1 = em.find(Employee.class, 1); // id가 1인 것을 찾아 객체화 & 영속화
//			System.out.println(e1); // 객체화했기 때문에 toString()호출
//
//			e1.setAddress("대전");
//			System.out.println(e1);
			
		}
		
		// #3. merge()
		// 객체가 테이블에 없으면 insert, 있으면 update
		{
			// 테이블에 없는 데이터 - insert
			// 객체화
//			Employee e = new Employee();
//			e.setId(2);
//			e.setName("이길동");
//			e.setAddress("광주");
//			
//			em.merge(e); // 없을 땐 persist와 비슷한 역할
			
			// 테이블에 있는 데이터 - update
			// 객체화
//			Employee e = new Employee();
//			e.setId(2);
//			e.setName("이길동");
//			e.setAddress("부산");
//			
//			em.merge(e); // 없을 땐 persist와 비슷한 역할
//			System.out.println(e); // 객체화했기 때문에 toString()호출
		}
		
		// #4. detach()
		// 영속화 context에서 분리 ( EntityManager가 더이상 관리X )
		{
			// 객체화 & 영속화
//			Employee e = em.find(Employee.class, 2);
//			System.out.println(e); 	// 객체화했기 때문에 toString()호출
//			e.setAddress("제주");	// 영속화 이후 update
//			System.out.println(e);
			
//			Employee e = em.find(Employee.class, 2);
//			System.out.println(e); 	// 객체화했기 때문에 toString()호출
//
//			em.detach(e);			// 영속화 해제됨
//			
//			e.setAddress("대구");	// 출력은 대구, but 실제 db 업데이트X
//			System.out.println(e);
		}
		
		// #5. remove()
		{
//			Employee e = em.find(Employee.class, 2);
//			System.out.println(e); 	// 객체화했기 때문에 toString()호출
//			em.remove(e);			// 1차 캐시에서도 삭제됨 (실제 db에서도)
			
			Employee e = new Employee();
			e.setId(1);
			em.remove(e);			// java.lang.IllegalArgumentException: Removing a detached instance entity.Employee#1
		}
		
		
		
		// 작업 종료
		em.getTransaction().commit();
		em.close();
		
	}

}
