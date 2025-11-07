package com.mycom.myapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycom.myapp.dto.OrderDto;
import com.mycom.myapp.service.OrderService;
@Controller
@ResponseBody
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService orderService;
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	// 대여목록 data
	@GetMapping("/list")
	public List<OrderDto> listOrder() {
		System.out.println("/orders/list");
		List<OrderDto> orderList = orderService.listOrder();
		return orderList;
	}
	// 대여상세 data
	@GetMapping("/detail/{order_id}")
	public OrderDto detailOrder(@PathVariable Integer order_id) {
		System.out.println("/orders/detail");
		OrderDto orderDto = orderService.detailOrder(order_id);
		return orderDto;
	}
	
	// 대여 수정
	@PostMapping("/update")
	public Map<String, String> updateOrder(OrderDto orderDto) {
		int ret = orderService.updateOrder(orderDto);
		Map<String, String> map = new HashMap<>();
		
		if (ret == 1) { map.put("result", "success"); }
		else { map.put("result", "fail"); }
		
		return map;
	}
	// Get
	// 대여 삭제
	@GetMapping("/delete/{order_id}")
	public Map<String, String> deleteOrder(@PathVariable Integer order_id) {
		int ret = orderService.deleteOrder(order_id);
		Map<String, String> map = new HashMap<>();
		
		if (ret == 1) { map.put("result", "success"); }
		else { map.put("result", "fail"); }
		
		return map;
	}
	
	// 검색
	@GetMapping("/listOrderLike") 
	public List<OrderDto> listOrderLike(@RequestParam String searchWord) { // requestParam : 반드시 parameter가 request에 담겨져 와야함
		return orderService.listOrderLike(searchWord);
	}
	
}
