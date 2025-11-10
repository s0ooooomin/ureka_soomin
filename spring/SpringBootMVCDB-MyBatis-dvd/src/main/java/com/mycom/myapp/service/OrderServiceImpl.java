package com.mycom.myapp.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.myapp.dao.MovieDao;
import com.mycom.myapp.dao.OrderDao;
import com.mycom.myapp.dto.OrderDto;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderDao orderDao;
	public OrderServiceImpl(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	@Override
	public List<OrderDto> listOrder() {
		return orderDao.listOrder();
	}

	@Override
	public OrderDto detailOrder(int order_id) {
		return orderDao.detailOrder(order_id);
	}

	@Override
	public int updateOrder(OrderDto orderDto) {
		return orderDao.updateOrder(orderDto.getOrder_id(), orderDto.getBorrowdate(), orderDto.getReturndate());
	}

	@Override
	public int deleteOrder(int order_id) {
		return orderDao.deleteOrder(order_id);
	}

	@Override
	public List<OrderDto> listOrderLike(String searchWord) {
		return orderDao.listOrderLike(searchWord);
	}

	@Override
	public int borrowMovie(String cust_name, String cust_phone, int movie_id) {
		return orderDao.borrowMovieByParams(cust_name, cust_phone, movie_id);
	}


}
