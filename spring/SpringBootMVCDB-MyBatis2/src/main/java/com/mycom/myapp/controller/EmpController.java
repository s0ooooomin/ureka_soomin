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

import com.mycom.myapp.dto.EmpDto;
import com.mycom.myapp.service.EmpService;

// 페이지 이동은 별도의 Controller에서 처리 (PageController)
// EmpController.java는 데이터 서비스만 제공 (@Controller + @ResponseBody 함께 사용)
@Controller
@ResponseBody // EmpController의 모든 메소드의 return 은 JSON으로 처리됨 (why? 다 데이터 요청에 대한 응답)
@RequestMapping("/emps") // 공통적인 부분 (모든 메소드의 url 맨 앞에 공통으로 자동입력됨)
public class EmpController {
	private final EmpService empService;
	// 생성자주입
	public EmpController(EmpService empService) {
		this.empService = empService;
	}
	
	// emp-mapper.xml 대응
	@GetMapping("/list") 
	public List<EmpDto> listEmp() {
		return empService.listEmp();
	}
	@GetMapping("/detail/{employeeId}") 
	public EmpDto detailEmp(@PathVariable int employeeId) {
		return empService.detailEmp(employeeId);
	}
	
	// Post
	@PostMapping("/insert")
	public Map<String, String> insertEmp (EmpDto empDto) {
		int ret = empService.insertEmp(empDto);
		Map<String, String> map = new HashMap<>();
		if (ret == 1) {
			map.put("result", "success");
		}else {
			map.put("result", "fail");
		}
		return map;
	}	
	// Post - 수정
	@PostMapping("/update")
	public Map<String, String> updateEmp (EmpDto empDto) {
		int ret = empService.updateEmp(empDto);
		Map<String, String> map = new HashMap<>();
		if (ret == 1) {
			map.put("result", "success");
		}else {
			map.put("result", "fail");
		}
		return map;
	}
	// Get - 삭제
	@GetMapping("/delete/{employeeId}")
	public Map<String, String> deleteEmp (@PathVariable("employeeId") Integer employeeId) {
		int ret = empService.deleteEmp(employeeId);
		Map<String, String> map = new HashMap<>();
		if (ret == 1) {
			map.put("result", "success");
		}else {
			map.put("result", "fail");
		}
		return map;
	}
	
	// emp-mapper-2.xml 에 대응
	// 검색
	@GetMapping("/listEmpLike") 
	public List<EmpDto> listEmpLike(@RequestParam String searchWord) { // requestParam : 반드시 parameter가 request에 담겨져 와야함
		return empService.listEmpLike(searchWord);
	}
	
	// Map (몇개의 column만)
	@GetMapping("/listEmpMap")
	public List<EmpDto> listEmpMap() {
		return empService.listEmpMap();
	}
	// WhereIf
	@GetMapping("/listEmpWhereIf")
	public List<EmpDto> listEmpWhereIf(@RequestParam Map<String, String> map) {
		return empService.listEmpWhereIf(map);
	}
	
	
	
}
