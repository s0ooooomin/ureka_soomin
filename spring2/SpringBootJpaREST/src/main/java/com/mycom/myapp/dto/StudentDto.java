package com.mycom.myapp.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Dto 가 컨트롤러에서 파라미터 처리용도로 사용됨 -> 기본 생성자 필요 (@NoArgs~) 
// -> Builder 오류 why? NoArgs~에 의해 빌더 내의 allArgs~가 사라짐
// -> @AllArgs~ 새로이 추가
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String phone;
}
