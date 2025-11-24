package entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

// 주문
// 주문과 고객은 다대일
// 주문과 상품은 다대일 (상품 1개에 주문 1개, 주문 1개에 여러 상품)
// NamedQuery 는 이름, jpql 2개로 구성. 사용하는 코드에서 해당 이름을 이용/호출
@NamedQueries ({
	@NamedQuery (
		name="Orders.findByCustomerName", // 주문한 고객명으로 찾겠다
		query="select o from Orders o join o.customer c where c.name=:customerName"
	),
	@NamedQuery (
			name="Orders.findByOrderDate", // 주문한 날짜로 찾겠다
			query="select o from Orders o where o.orderDate = :orderDate"
	),
	@NamedQuery (
			name="Orders.findByOrderRange", // 주문한 날짜 범위로 찾겠다
			query="select o from Orders o where o.orderDate between :startDate and :endDate"
		),
	@NamedQuery (
			name="Orders.findByProductPriceRange", // 주문한 금액 범위로 찾겠다
			query="select o, p.price from Orders o JOIN o.product p where p.price between :startPrice and :endPrice"
			)
})
@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	// Orders 중심의 select with join 수행
	// N + 1 이슈 방지를 위해 LAZY 변경
	@ManyToOne(fetch=FetchType.LAZY)
	private Customer customer;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Product product;
	
	@Column(name="order_quantity")
	private int orderQuantity;
	
	@Column(name="order_date")
	private LocalDate orderDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	// 연관관계의 customer, product 제외
	@Override
	public String toString() {
		return "Orders [id=" + id + ", orderQuantity=" + orderQuantity + ", orderDate=" + orderDate + "]";
	}
}
