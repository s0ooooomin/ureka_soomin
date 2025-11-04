package com.mycom.myapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


// aspect
@Component
@Aspect
public class LogAspect {
	// logging 주체
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// Pointcut 설정
	// value 속성에 매칭 설정 - aspectJ 표현식 문법
	// execution(
	// 		* 						// return type (*:모든 타입)
	//		com.mycom.myapp.aspect. // 패키지 (.. : 하위패키지 모두 || .:해당패키지의특정하위)
	//		MyClass					// class (*:모든 클래스 || 특정 class)
	//		.*						// 메소드 (*:모든 메소드 || .특정메소드)
	//		(..)					// 파라미터(..:모든파라미터 || 특정파라미터 ex. (String, int))
	// )
//	@Pointcut(value="execution(int com.mycom.myapp.aspect..*.*(String, int, String))")
//	@Pointcut(value="execution(int com.mycom.myapp.aspect..*BusinessProcess.*(String, int, String))")
	@Pointcut(value="execution(int com.mycom.myapp.aspect.*BusinessProcess.*(String, int, String))")
	private void logPointcut() {}
	
	// Advise
	// pointcut 을 통해 join point를 확인
	@Before("logPointcut()")
	public void beforeLog(JoinPoint joinPoint) {
		logger.info(" [LogAspect : before] " + joinPoint.getSignature().getName());
	}
	@After("logPointcut()")
	public void afterLog(JoinPoint joinPoint) {
		logger.info(" [LogAspect : after] " + joinPoint.getSignature().getName());
	}
	
}
