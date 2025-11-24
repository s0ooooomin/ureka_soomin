package com.mycom.myapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*") // 다른 애들이 origin으로 연결되는거 허용 -> 127.0.0.1:5500(vscode의 fkdlqm tjqj)
public class CORSController {
	@GetMapping("/cors")
	public Map<String, String> getCORS(@RequestParam("param") String param) {
		System.out.println("getCORS param : " + param);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map; // return controller 이므로 json으로 나옴
	}
	@PostMapping("/cors")
	public Map<String, String> postCORS(@RequestParam("param") String param) {
		System.out.println("postCORS param : " + param);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map; // return controller 이므로 json으로 나옴
	}
	@PutMapping("/cors/{num}")
	public Map<String, String> putCORS(
			@RequestParam("param") String param,
			@PathVariable("num") String num
			) {
		System.out.println("putCORS param : " + param);		
		System.out.println("putCORS param : " + num);

		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map; // return controller 이므로 json으로 나옴
	}
	@DeleteMapping("/cors/{num}")
	public Map<String, String> deleteCORS(
			@PathVariable ("num") String num) {
		System.out.println("deleteCORS num : " + num);
		Map<String, String> map = new HashMap<>();
		map.put("result", "success");
		return map; // return controller 이므로 json으로 나옴
	}
}
