package com.mycom.myapp.pattern.singleton;

public class Test {

	public static void main(String[] args) {

		// 기본 생성자 public 을 통해 객체 여러개 생성 
//		Logger logger1 = new Logger();
//		Logger logger2 = new Logger();

		// #2) singleton EAGER
		Logger logger1 = Logger.getInstance1();
		Logger logger2 = Logger.getInstance1();
		logger1.log("로그1");
		logger2.log("로그2");
		
		System.out.println(logger1);
		System.out.println(logger2);

		Logger logger3 = Logger.getInstance2();
		Logger logger4 = Logger.getInstance2();
		logger1.log("로그3");
		logger2.log("로그4");
		
		System.out.println(logger3);
		System.out.println(logger4);
	}

}
