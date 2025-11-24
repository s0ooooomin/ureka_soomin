import java.util.List;

import entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

// select
// em.commit() X
// JPQL <= SQL lite버전같은거
public class Test {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
		EntityManager em = emf.createEntityManager();
		
		// typed query
//		{
//			// entity/Book.java에서 가져옴. 쿼리문 같지만 사실 아님
//			String jpql = "select b from Book b";
//			TypedQuery<Book> query = em.createQuery(jpql, Book.class);
//			List<Book> bookList = query.getResultList();
//			for (Book book : bookList) {
//				System.out.println(book);
//			}
//		}
		
		// typed query + parameter (position)
		{
			// entity/Book.java에서 가져옴. 쿼리문 같지만 사실 아님
			String jpql = "select b from Book b where price > ?1"; // ? : parameter
			TypedQuery<Book> query = em.createQuery(jpql, Book.class);
			query.setParameter(1, 15000);
			List<Book> bookList = query.getResultList();
			for (Book book : bookList) {
				System.out.println(book);
			}
		}
		// typed query + parameter (name)
		{
			// entity/Book.java에서 가져옴. sql쿼리문 같지만 사실 아님
			String jpql = "select b from Book b where price > :price"; // :(변수명) parameter
			TypedQuery<Book> query = em.createQuery(jpql, Book.class);
			query.setParameter("price", 15000);
			List<Book> bookList = query.getResultList();
			for (Book book : bookList) {
				System.out.println(book);
			}
		}
	}

}
