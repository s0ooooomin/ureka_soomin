package com.mycom.myapp.calc.has_a;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HasaCalculator {
	
	// 아래 Has A 관계 (필드) 를 DI하는 3가지 방법
	
//	// #1.Field DI
//	@Autowired
////	Calculator calculator = new Calculator(); 이게 원래 우리가 하던 방법
//	Calculator calculator; // 이렇게만 해놔도 field injection 됨 (autowired 에 의해서)
	
//	// #2. Setter DI
//	Calculator calculator;
//	
//	@Autowired
//	public void setCalculator(Calculator calculator) {
//		this.calculator = calculator;
//	}
	
//	// #3. Constructor DI
//	// 생성자가 1개만 있고 그 생성자가 has-a관계 필드의 값을 설정하면 @autowired 필요 X why? 1개니까.. (걍 해줌)
//	// 생성자가 여러개 -> has-a관계 필드의 값을 설정하는 생성자에 @autowired 붙여줘야함
//	Calculator calculator;
//	
//	// 기본생성자
//	public HasaCalculator() {}
//	
//	// parameter가 있는 생성자
//	@Autowired
//	public HasaCalculator(Calculator calculator) {
//		this.calculator = calculator;
//	}
	
	// #4. Constructor DI
	// final 필드 인젝션일 경우, 기본 생성자에서 오류 (Spring 오류X java 오류O)
	// -> 기본 생성자 삭제 & autowired 필요X
	private final Calculator calculator;
	
//	// 기본생성자
//	public HasaCalculator() {}
	
//	@Autowired
	public HasaCalculator(Calculator calculator) {
		this.calculator = calculator;
	}
	
	
	
	public int add(int n1, int n2) {
		System.out.println("HasaCalculator add()");
		return calculator.add(n1, n2);
	}
	
}
