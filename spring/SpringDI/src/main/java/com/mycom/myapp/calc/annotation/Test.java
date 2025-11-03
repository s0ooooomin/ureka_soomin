package com.mycom.myapp.calc.annotation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

// annotation 기반 설정
public class Test {

	public static void main(String[] args) {
		// DI 이전
//		Calculator calculator = new Calculator();
//		System.out.println(calculator.add(3, 7) );
		
		// DI (in SpringDI)
		// 1. 스프링 framkework 환경구축
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xml/Calc-annotation.xml");
		
		Calculator calculator = (Calculator) context.getBean("calculator"); // Spring에 객체 DI 의로
		System.out.println(calculator.add(3, 7));
		
		context.close();
		
		// 
	}

}
