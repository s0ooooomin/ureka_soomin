package com.mycom.myapp.lombok;

public class Test {

	public static void main(String[] args) {
		
		// lombok을 통한 생성자를 통해 객체를 생성할 경우, 필드의 순서가 변경되면 자동으로 lombok에 의해 생성자 속 필드 변경됨
		// <= 생성자 코드가 눈에 보이지 않기 때문에 발생. 기존 생성자를 사용했던 코드에 문제 발생 (기존에 지정해둔 순서와 달라짐)
		EmpDto2 empDto2 = new EmpDto2(1, "길동", "홍", "hong@gildong.com", "2025-11-25");
		System.out.println(empDto2);
		// 결론) lombok 생성자를 통해 객체 생성X
		// 꼭 필요한 생성자만 만들기 + builder pattern 객체 생성

		// @Builder 추가 후
		EmpDto2 empDto3 = EmpDto2.builder()
								.employeeId(5)
								.firstName("길동")
								.lastName("홍")
								.hireDate("2025-11-25")
								.email("hong@gildong.com")
								.build();
		System.out.println(empDto3);
	}

}
