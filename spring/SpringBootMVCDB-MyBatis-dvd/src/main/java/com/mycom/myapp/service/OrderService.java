package com.mycom.myapp.service;

import java.sql.Date;
import java.util.List;

import com.mycom.myapp.dto.MovieDto;
import com.mycom.myapp.dto.OrderDto;

public interface OrderService {
	List<OrderDto> listOrder();
	OrderDto detailOrder( int order_id );
	int updateOrder(OrderDto orderDto);
	int deleteOrder ( int order_id );
	
	List<OrderDto> listOrderLike(String searchWord);
	
}
