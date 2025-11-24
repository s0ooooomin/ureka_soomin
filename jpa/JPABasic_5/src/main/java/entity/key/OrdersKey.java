package entity.key;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrdersKey implements Serializable{

	private static final long serialVersionUID = 1L;

	private int orderId;
	private String orderDate;
	
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
	@Override
	public int hashCode() {
		return Objects.hash(orderDate, orderId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdersKey other = (OrdersKey) obj;
		return Objects.equals(orderDate, other.orderDate) && orderId == other.orderId;
	}
	
	@Override
	public String toString() {
		return "OrderKey [orderId=" + orderId + ", orderDate=" + orderDate + "]";
	}
	
	
}
