package com.mycom.myapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.dto.StudentDto;
import com.mycom.myapp.dto.StudentResultDto;
import com.mycom.myapp.entity.Student;
import com.mycom.myapp.service.StudentServiceCrud;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/re") // /api/v2.2 <= 버전 추가 가능
@RequiredArgsConstructor // serviceCrud 생성자 만들어줌

// ResponseEntity를 이용하지 않는 코드 -> 브라우저 - 스프링 간의 응답 처리에 대해 직접 관여X
// ResponseEntity를 이용하는 코드 -> 브라우저 - 스프링 간의 응답 처리에 대해 직접 관여O
//	- spring에 의뢰 처리
//	- 기존 응답 객체를 ResponseEntity로 감싼다. (Wrapper)
//		=> Spring이 ResponseEntity의 설정값을 확인하고 HttpResponse 코드를 변경 (ex. HttpStatus.OK)
//	- ResponseEntity 객체에 응답코드 설정
// REsponseEntity를 통해 작업결과 전달 <= ResultDto와 의미 중복 문제
// 선택 1 : ResponseEntity 만 사용
// 선택 2 : ResultDto만 사용
// 선택 3 : 함께 사용 ( 복잡한 응답 구조 필요 시)


public class StudentControllerCRUDResponseEntity {

	// StudentServiceCrud DI (생성자는 @RequiredArgs~ 가 만들어줌)
	private final StudentServiceCrud studentServiceCrud;

	// 목록 get /students (get 방식의 /students == list. post 방식의 /students == insert)
	@GetMapping("/students")
	public ResponseEntity<StudentResultDto> listStudent() {
//		return studentServiceCrud.listStudent();
		StudentResultDto studentResultDto = studentServiceCrud.listStudent();
		return new ResponseEntity<StudentResultDto>(studentResultDto, HttpStatus.OK);
		
		// 404 & json 응답O -> 프론트(html)의 let data = response.json 코드 정상처리
//		return new ResponseEntity<StudentResultDto>(studentResultDto, HttpStatus.NOT_FOUND); // 404
//		// 404 & json 응답X -> 프론트(html)의 let data = response.json 코드 처리과정에서 catch 로 이동.
//		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 404

//		return new ResponseEntity<StudentResultDto>(studentResultDto, HttpStatus.INTERNAL_SERVER_ERROR); // 500
		
		// 다른 표현들
//		// --- 200 ---// 
//		return new ResponseEntity<StudentResultDto>(studentResultDto, HttpStatus.OK);
//		#2. return ResponseEntity
//				.status(HttpStatus.OK)
//				.body(studentResultDto);
//		#3. return ResponseEntity
//				.ok()
//				.body(studentResultDto);
		// ---404--- //
//		#1. return new ResponseEntity
//				.status(studentResultDto, HttpStatus.NOT_FOUND); // 404
//		#2. return ResponseEntity<StudentResultDto>(HttpStatus.NOT_FOUND)
//				.body(null); 
//		#3. return ResponseEntity
//				.status(HttpStatus.NOT_FOUND).build(); 
//		return ResponseEntity
//				.notFound()
//				.build(); 
		
		// --- 500 --- //
//		#1. return ResponseEntity
//				.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		return ResponseEntity
//				.internalServerError().build();
	
	}
	
	// ResultDto & ResponseEntity 함께 사용 예시
	// 작업결과 : ResponseEntity의 status로, 데이터는 StudentResultDto->StudentDto만 (결과 제외)
	
	// 상세 get /students/123
	@GetMapping("/students/{id}")
	public ResponseEntity<StudentDto> detailStudent(@PathVariable("id") Integer id) {
//		return studentServiceCrud.detailStudent(id);
		StudentResultDto studentResultDto = studentServiceCrud.detailStudent(id);
		String result = studentResultDto.getResult();
		switch(result) {
			case "success" : return ResponseEntity.ok().body(studentResultDto.getDto());
			case "notfound" : return ResponseEntity.notFound().build();
			default : return ResponseEntity.internalServerError().build();
		}
	
	}
	
	// 등록 post /stuents
	@PostMapping("/students")
	public ResponseEntity<StudentDto> insertStudent(@RequestBody StudentDto studentDto) {
		StudentResultDto studentResultDto = studentServiceCrud.insertStudent(studentDto);
		String result = studentResultDto.getResult();
		switch(result) {
			case "success" : return ResponseEntity.ok().body(studentResultDto.getDto());
			case "notfound" : return ResponseEntity.notFound().build();
			default : return ResponseEntity.internalServerError().build();
		}	
	
	}
	
	// 수정 put /students
	@PutMapping("/students/{id}")
	public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") Integer id, @RequestBody StudentDto studentDto) {
		
		StudentResultDto studentResultDto = studentServiceCrud.updateStudent(studentDto);
		String result = studentResultDto.getResult();
		switch(result) {
			case "success" : return ResponseEntity.ok().body(studentResultDto.getDto());
			case "notfound" : return ResponseEntity.notFound().build();
			default : return ResponseEntity.internalServerError().build();
		}
	}
	
	// 삭제
	@DeleteMapping("/students/{id}")
	public ResponseEntity<StudentDto> deleteStudent(@PathVariable("id") Integer id) {
		StudentResultDto studentResultDto = studentServiceCrud.deleteStudent(id);
		String result = studentResultDto.getResult();
		switch(result) {
			case "success" : return ResponseEntity.ok().body(studentResultDto.getDto());
			case "notfound" : return ResponseEntity.notFound().build();
			default : return ResponseEntity.internalServerError().build();
		}
	}
	

	// 전체 건수
	@GetMapping("/students/count")
	public ResponseEntity<Long> countStudent() {
		StudentResultDto studentResultDto = studentServiceCrud.countStudent();
		String result = studentResultDto.getResult();
		switch (result) {
			// "success" 또는 "" 또는 StudentDto (id 포함한) 이 경우 Service Layer 리턴을 변경
			case "success" : return ResponseEntity.ok().body(studentResultDto.getCount()); 
			case "fail" : 
			default : return ResponseEntity.internalServerError().build();
		}		
	}
	// 페이징
	// REST API 에서 반드시 PathVariable 또는 RequestParam 을 써야 한다는 내용 X
	// 우리는 PathVariable 사용.
	// 목록전체를 가져오는 ResponseEntity<studentResultDto> listStudent() 와 비교
	@GetMapping("/students/page/{pageNumber}/{pageSize}")
	public ResponseEntity<List<StudentDto>> listStudent(
			@PathVariable("pageNumber") Integer pageNumber, 
			@PathVariable("pageSize") Integer pageSize) {
		
		StudentResultDto studentResultDto = studentServiceCrud.listStudent(pageNumber, pageSize);
		String result = studentResultDto.getResult();
		switch (result) {
			// "success" 또는 "" 또는 StudentDto (id 포함한) 이 경우 Service Layer 리턴을 변경
			case "success" : return ResponseEntity.ok().body(studentResultDto.getList()); 
			case "fail" : 
			default : return ResponseEntity.internalServerError().build();
		}		
	}
}