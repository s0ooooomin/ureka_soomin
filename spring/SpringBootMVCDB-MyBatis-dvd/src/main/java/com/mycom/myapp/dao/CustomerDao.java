package com.mycom.myapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.dto.CustomerDto;
import com.mycom.myapp.dto.MovieDto;

@Mapper
public interface CustomerDao {

	List<CustomerDto> listCustomer();
	CustomerDto detailCustomer(int cust_id);
	int insertCustomer(CustomerDto customerDto);
	int updateCustomer(int cust_id, String cust_phone);
	int deleteCustomer(int cust_id);	

	List<CustomerDto> listCustomerLike(String searchWord);
}
