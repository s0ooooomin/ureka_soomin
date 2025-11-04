package com.mycom.myapp.aspect;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mycom.myapp.aspect.sub.SubBusinessProcess;

public class Test {

	// main()을 SpringFramework
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xml/aspect.xml");
		
		// business logic code 수행 (개발자가 이쪽 코드에 집중, 나머지는 spring이 대신)
		// aspect의 설정에 따른 proxy의 개입, 실행 확인
		BusinessProcess bp = (BusinessProcess) context.getBean("businessProcess");
		bp.no_bp();
		System.out.println();
		bp.int_bp();
		System.out.println();
		bp.String_int_String_bp("s1", 0, "s2");
		System.out.println();
		
		System.out.println("-----------");
		
		AnotherBusinessProcess abp = (AnotherBusinessProcess) context.getBean("anotherBusinessProcess");
		abp.no_bp();
		System.out.println();
		abp.int_bp();
		System.out.println();
		abp.String_int_String_bp("s1", 0, "s2");
		System.out.println();

		System.out.println("-----------");
		
		SubBusinessProcess sbp = (SubBusinessProcess) context.getBean("subBusinessProcess");
		sbp.no_bp();
		System.out.println();
		sbp.int_bp();
		System.out.println();
		sbp.String_int_String_bp("s1", 0, "s2");
		System.out.println();

		
		
		context.close();
		
	}

}
