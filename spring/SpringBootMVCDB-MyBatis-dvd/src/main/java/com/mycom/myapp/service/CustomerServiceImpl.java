package com.mycom.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.myapp.dao.CustomerDao;
import com.mycom.myapp.dao.MovieDao;
import com.mycom.myapp.dto.CustomerDto;
import com.mycom.myapp.dto.MovieDto;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerDao customerDao;
	public CustomerServiceImpl(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	@Override
	public List<CustomerDto> listCustomer() {
		return customerDao.listCustomer();
	}

	@Override
	public CustomerDto detailCustomer(int cust_id) {
		return customerDao.detailCustomer(cust_id);
	}

	@Override
	public int insertCustomer(CustomerDto customerDto) {
		return customerDao.insertCustomer(customerDto);
	}

	@Override
	public int updateCustomer(CustomerDto customerDto) {
		return customerDao.updateCustomer(customerDto.getCust_id(), customerDto.getCust_phone());
	}

	@Override
	public int deleteCustomer(int cust_id) {
		return customerDao.deleteCustomer(cust_id);
	}

	@Override
	public List<CustomerDto> listCustomerLike(String searchWord) {
		return customerDao.listCustomerLike(searchWord);
	}

	

}
