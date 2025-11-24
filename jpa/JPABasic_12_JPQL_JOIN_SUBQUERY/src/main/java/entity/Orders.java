package entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

// 주문
// 주문과 고객은 다대일
// 주문과 상품은 다대일 (상품 1개에 주문 1개, 주문 1개에 여러 상품)
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
