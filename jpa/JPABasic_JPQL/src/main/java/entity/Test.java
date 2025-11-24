package entity;

import java.util.List;

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
		{
			// entity/Book.java에서 가져옴
			String jpql = "select b from Book b";
			TypedQuery<Book> query = em.createQuery(jpql, Book.class);
			List<Book> bookList = query.getResultList();
			for (Book book : bookList) {
				System.out.println(book);
			}
		}
	}

}
