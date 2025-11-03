package com.mycom.myapp.di.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xml/Calc-xml.xml");
		
		Calculator calculator = (Calculator) context.getBean("calculator");
		System.out.println(calculator.multiply(3, 7));
		
		context.close();
	
	}

}
