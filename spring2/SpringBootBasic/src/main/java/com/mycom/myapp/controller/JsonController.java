package com.mycom.myapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.dto.CarDto;
import com.mycom.myapp.dto.EmpDto;

// 이 컨트롤러의 모든 응답이 모두 json일 경우 @Controller + @ResponseBody = @RestController
//@Controller @ResponseBody
@RestController
public class JsonController {

	// 단순문자열
	@GetMapping("/string")
	public String m1() {
		System.out.println("/string");
		return "안녕하세요~!~~!";
	}
	// JSON 문자열
	@GetMapping("/jsonstring")
	public String m2() {
		System.out.println("/jsonstring");
		return "\"result\":\"success\"";
	}
	// dto
	@GetMapping("/dto")
	public CarDto m3() {
		System.out.println("/dto");
		return new CarDto("소나타", 4000, "홍길동"); // 원래는 백엔드 다녀오는거 (객체꺼내옴)
	}
	// List of dto
	@GetMapping("/listdto")
	public List<CarDto> m4() {
		System.out.println("/listdto");
		List<CarDto> list = new ArrayList<>();
		list.add(new CarDto("소나타", 40000, "홍길동"));
		list.add(new CarDto("그렌저", 50000, "이길동"));
		list.add(new CarDto("제네시스", 60000, "삼길동"));
		return list;
	}
	
	//json request
	// #1. postman m5(EmpDto dto) 테스트
	// => O
	
	// #2. jsonController.html m5(EmpDto dto) 테스트
	// => X null 로 나옴
	
	// #3. jsonController.html 으로 m5(@RequestBody EmpDto dto)
	// => EmpDto [employeeId=1, firstName=길동, lastName=홍, email=hong@gildong.com, hireDate=2025-11-24]
		
	// #4. 브라우저 Network 의 request payload 의 json 문자열을 복사해서, postman - body-raw-json 으로 요청
	// => EmpDto [employeeId=1, firstName=길동, lastName=홍, email=hong@gildong.com, hireDate=2025-11-24]
	// emp
	@PostMapping("/emp")
	public Map<String, String> m5(@RequestBody EmpDto dto) {
		System.out.println("/emp");
		System.out.println(dto);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
	@PostMapping("/emplist")
	public Map<String, String> m6(@RequestBody List<EmpDto> list) { // @RequestBody + List 하면 dto 하나씩 O
		System.out.println("/emplist");
		System.out.println(list);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map;
	}
	
	
	
}
