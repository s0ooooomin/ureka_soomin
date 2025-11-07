package com.mycom.myapp.service;

import java.util.List;

import com.mycom.myapp.dto.CustomerDto;
import com.mycom.myapp.dto.MovieDto;

public interface CustomerService {
	List<CustomerDto> listCustomer();
	CustomerDto detailCustomer(int cust_id);
	int insertCustomer(CustomerDto customerDto);
	int updateCustomer(CustomerDto customerDto);
	int deleteCustomer(int cust_id);	

	List<CustomerDto> listCustomerLike(String searchWord);
}
