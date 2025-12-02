package com.mycom.myapp.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.test.dto.TestDto;
import com.mycom.myapp.test.dto.TestResultDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TestController {
	
	@GetMapping("/hello")
	public void m1() {
		log.info("/hello");
	}
	
	@GetMapping("/param1")
	public void m2(@RequestParam("id") Integer id, @RequestParam("name") String name) {
		log.info("/param1");		
		log.info(" id : "+ id + " name : " + name );
	}

	@PostMapping("/param2")
	public void m3(TestDto testDto) {
		log.info("/param2");		
		log.info(testDto.toString());
	}

	// 응답 : 단순 문자열 (String)
	@PostMapping("/response1")
	public String m4(TestDto testDto) {
		log.info("/response1");		
		log.info(testDto.toString());
		return "success";
	}

	// 응답 : json
	@PostMapping("/response2")
	public TestResultDto m5(TestDto testDto) {
		log.info("/response2");		
		log.info(testDto.toString());
		TestResultDto testResultDto = new TestResultDto();
		testResultDto.setResult("success");
		
		return testResultDto;
	}
	
	
}
