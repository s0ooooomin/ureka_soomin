package com.mycom.myapp.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycom.myapp.dto.MovieDto;
import com.mycom.myapp.dto.OrderDto;

@Mapper
public interface OrderDao {
	List<OrderDto> listOrder();
	OrderDto detailOrder( int order_id );
	int updateOrder(int order_id, Date borrowdate, Date returndate);
	int deleteOrder ( int order_id );
	
	List<OrderDto> listOrderLike(String searchWord);
	
	// 원래는 returnProcess 도 있음........
	int borrowMovieByParams(
	        @Param("cust_name") String cust_name, 
	        @Param("cust_phone") String cust_phone, 
	        @Param("movie_id") int movie_id
	    );	
}