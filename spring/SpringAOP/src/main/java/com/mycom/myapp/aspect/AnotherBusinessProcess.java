package com.mycom.myapp.aspect;

import org.springframework.stereotype.Component;

@Component
public class AnotherBusinessProcess {

	// 3개의 Business logic 처리 메소드
	public void no_bp() {
		System.out.println("AnotherBusinessProcess : no_bp()");
	}
	public int int_bp() {
		System.out.println("AnotherBusinessProcess : int_bp()");
		return 0;
	}
	public int String_int_String_bp(String s1, int i, String s2) {
		System.out.println("AnotherBusinessProcess : String_int_String_bp()");
		return 0;
	}
	
}
