package com.mycom.myapp.calc.has_a;

import org.springframework.stereotype.Component;

@Component
public class Calculator {
	public int add(int n1, int n2) {
		System.out.println("Calculator add()");
		return n1 + n2;
	}
}
