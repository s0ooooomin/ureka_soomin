package com.mycom.myapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycom.myapp.dto.CustomerDto;
import com.mycom.myapp.dto.MovieDto;
import com.mycom.myapp.service.CustomerService;
@Controller
@ResponseBody
@RequestMapping("/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	// 영화목록 data
	@GetMapping("/list")
	public List<CustomerDto> listCustomer() {
		System.out.println("/customers/list");
		List<CustomerDto> customerList = customerService.listCustomer();
		return customerList;
	}
	// 영화상세 data
	@GetMapping("/detail/{cust_id}")
	@ResponseBody
	public CustomerDto detailCustomer(@PathVariable Integer cust_id) {
		System.out.println("/customers/detail");
		CustomerDto customerDto = customerService.detailCustomer(cust_id);
		return customerDto;
	}
	
	// Post
	// 영화 등록
	@PostMapping("/insert")
	public Map<String, String> insertCustomer(CustomerDto customerDto) {
		int ret = customerService.insertCustomer(customerDto);
		Map<String, String> map = new HashMap<>();
		
		if (ret == 1) { map.put("result", "success"); }
		else { map.put("result", "fail"); }
		
		return map;
	}
	// 영화 수정
	@PostMapping("/update")
	public Map<String, String> updateCustomer(CustomerDto customerDto) {
		int ret = customerService.updateCustomer(customerDto);
		Map<String, String> map = new HashMap<>();
		
		if (ret == 1) { map.put("result", "success"); }
		else { map.put("result", "fail"); }
		
		return map;
	}
	// Get
	// 영화 삭제
	@GetMapping("/delete/{cust_id}")
	public Map<String, String> deleteCustomer(@PathVariable Integer cust_id) {
		int ret = customerService.deleteCustomer(cust_id);
		Map<String, String> map = new HashMap<>();
		
		if (ret == 1) { map.put("result", "success"); }
		else { map.put("result", "fail"); }
		
		return map;
	}
	
	// 검색
	@GetMapping("/listCustomerLike") 
	public List<CustomerDto> listCustomerLike(@RequestParam String searchWord) { // requestParam : 반드시 parameter가 request에 담겨져 와야함
		return customerService.listCustomerLike(searchWord);
	}
	
}
