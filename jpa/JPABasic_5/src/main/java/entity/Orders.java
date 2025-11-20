package entity;

import entity.key.OrdersKey;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
//@IdClass(OrdersKey.class) // #1
public class Orders {

	/*
	// #1. IdClass
	@Id
	private int orderId;
	@Id
	private String orderDate;
	
	private int productNo;

	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}

	
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", productNo=" + productNo + "]";
	}
	*/
	
	// #2 embeddable
	
	@EmbeddedId
	private OrdersKey id;
	private int productNo;
	
	public OrdersKey getId() {
		return id;
	}
	public void setId(OrdersKey id) {
		this.id = id;
	}
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	@Override
	public String toString() {
		return "Orders [id=" + id + ", productNo=" + productNo + "]";
	}
	
	
	
	

	
}
