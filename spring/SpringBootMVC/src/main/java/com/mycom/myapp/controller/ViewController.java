package com.mycom.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.myapp.dto.CarDto;

@Controller
public class ViewController {

	@GetMapping("/viewTest1")
	public String viewTest1() {
		// 파라미터 처리
		// service - dao(dto) - db => model
		return "viewTest1";
	}
	
	@GetMapping("/viewTest2")
	public String viewTest2() {
		// 파라미터 처리
		// service - dao(dto) - db => model
		return "sub/viewTest2";
	}
	
	// jsp 로 model 과 함께 forwarding
	// #1. model parameter
	@GetMapping("/viewTest3") 
	public String viewTest3(Model model) {
		model.addAttribute("seq", "12345");
		model.addAttribute("carDto", new CarDto("myCar", 20000, "홍길동"));
		// jsp로 넘길 땐 여기에 쓴 속성명과 동일하게 전달
		return "/viewTest3";
	}
	// #2. modelAndView에 모든 속성(obj, view,..)을 넣어서 return
	@GetMapping("/viewTest4") 
	public ModelAndView viewTest4() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("seq", "12345");
		mav.addObject("carDto", new CarDto("myCar", 20000, "홍길동"));
		// jsp로 넘길 땐 여기에 쓴 속성명과 동일하게 전달
		mav.setViewName("viewTest4");
		return mav;
	}
	
	// redirect
	@GetMapping("/redirect")
	public String redirect() {
		System.out.println("redirect");
		
//		return "viewTest1"; // 이건 forwarding (jsp로 보냄)
		// redirect는 접속했다가 다시 다른 곳으로
		// localhost:8080/redirect -> 302 + location header 확인 -> localhost:8080/viewTest1 -> @GetMapping(/viewTest1")
		// Controller를 통해 viewTest1.jsp로 이동
		return "redirect:viewTest1";
	}
	
}
