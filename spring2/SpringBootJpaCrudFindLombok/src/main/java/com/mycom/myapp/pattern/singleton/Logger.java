package com.mycom.myapp.pattern.singleton;

// Singleton
// JVM에서 객체가 단 1개 만들어지도록 패턴 적용
// #1. 일반 클래스
//public class Logger {
//	public void log(String message) {
//	System.out.println("Log : " + message);
//	}
//}

// #2. Singleton pattern이 적용된 클래스
// 		- 1)private 생성자
//		- 2)자신과 같은 타입의 필드 (private)
// 		- 3)자신과 같은 타입을 return 하는 static method (public)

public class Logger {
	// 1)
	private Logger() {} 
	
	// Eager loading
		// 2)
		private static Logger instance1 = new Logger(); 
		// 3)
		public static Logger getInstance1() {
			return instance1;
		}
		
	// Lazy loading
		// 2)
		private static Logger instance2; 
		// 3)
		public static Logger getInstance2() {
			if (instance2 == null) {
				instance2 = new Logger();
			}
			return instance2;
		}
	
	public void log(String message) {
		System.out.println("Log : " + message);
	}
}
