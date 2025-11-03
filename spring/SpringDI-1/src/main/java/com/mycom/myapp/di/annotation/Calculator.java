package com.mycom.myapp.di.annotation;

import org.springframework.stereotype.Component;

@Component
public class Calculator {
	public int multiply(int v1, int v2) {
		return v1 * v2;
	}
}
