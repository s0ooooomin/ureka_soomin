package com.mycom.myapp.calc.annotation;

import org.springframework.stereotype.Component;

@Component // annotation이 DI 대상을 표시하는것
public class Calculator {
	public int add(int n1, int n2) {
		return n1 + n2;
	}
}
