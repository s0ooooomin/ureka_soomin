package com.mycom.myapp.aspect.sub;

import org.springframework.stereotype.Component;

@Component
public class SubBusinessProcess {

	// 3개의 Business logic 처리 메소드
	public void no_bp() {
		System.out.println("SubBusinessProcess : no_bp()");
	}
	public int int_bp() {
		System.out.println("SubBusinessProcess : int_bp()");
		return 0;
	}
	public int String_int_String_bp(String s1, int i, String s2) {
		System.out.println("SubBusinessProcess : String_int_String_bp()");
		return 0;
	}
	
}
