package com.mycom.myapp.pattern.methodchain;

public class Test {

	public static void main(String[] args) {

		// #1. method chain 없이
		//		연속적인 변수 변경 및 계산 출력
//		Calculator calculator = new Calculator();
//		calculator.setFirst(5);
//		calculator.setSecond(3);
//		
//		calculator.showAdd();
//		calculator.showSub();
		
//		// #2. method chain pattern 적용
		Calculator calculator = new Calculator();
		calculator.setFirst(5)
					.setSecond(3)
					.showAdd()
					.showSub();
	}

}
