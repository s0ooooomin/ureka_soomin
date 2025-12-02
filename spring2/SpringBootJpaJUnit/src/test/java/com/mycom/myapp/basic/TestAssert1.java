package com.mycom.myapp.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // order 어노테이션 추가를 위한 어노테이션
class TestAssert1 {

	int getA() { return 54; }
	int getB() { return 5; }
	
	@Test 
	@Order(value = 1)
	void test1() {
		int a = getA();
		int b = getB();
		
		// 두 메소드의 호출결과를 로그를 통해 눈으로 확인 (비효율적)
//		System.out.println(a);
//		System.out.println(b);
//		assertEquals(getA(), getB());
		
		// assert() 호출 결과와 비교에 따라 테스트 성공여부결정
		assertEquals(a, b, "a=b");
	
	}

	@Test 
	@Order(value = 2)
	void test2() {
		// assert() 호출 결과와 비교에 따라 테스트 성공여부결정
		assertNotEquals(getA(), 5, "a!=b");
	}

	boolean getResult() { return true; }
	boolean isTrue = false;
	@Test 
	@Order(value = 3)
	void test3() {
		
//		assertEquals(isTrue, getResult());
		assertTrue(getResult(), " getResult is True ");
	}

	// false 판별
	@Test 
	@Order(4)
	void test4() {
		assertFalse(getResult(), " getResult is False ");
	}

	// Not Null
	String getString() { return "123"; }
	@Test 
	@Order(5)
	void test5() {
		assertNull( getString(), " getString is Null ");
	}
	
	@Test 
	@Order(6)
	void test6() {
		assertNotNull( getString(), " getString is Not Null ");
	}
	
	
	
}
