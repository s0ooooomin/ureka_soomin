package com.mycom.myapp.basic;

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
class TestBasic {

	@Test // test1 메소드를 junit 으로 테스트
	@Order(value = 2)
	void test1() {
		// 테스트를 위한 실행 코드 작성
		System.out.println("test1()");
	}

	@Test // test1 메소드를 junit 으로 테스트
	@Order(1)
	void test2() {
		System.out.println("test2()");
	}

	@Test // test1 메소드를 junit 으로 테스트
	@DisplayName("회원 등록 테스트")
	@Order(4)
	void test3() {
		System.out.println("test3()");
	}
	
	@Test // test1 메소드를 junit 으로 테스트
	@DisplayName("회원 수정 테스트")
	@Order(3)
	void test4() {
		// Error 발생시키기
		System.out.println("test4(start)");
		String s = null;
		System.out.println("test4(1)");
		s.length();
		System.out.println("test4(end)");
	}
	
	// @BeforeAll, @AfterAll <- static 안 붙이면 실행 안 됨 
	@BeforeAll
	static void beforeAll() {
		System.out.println("beforeAll()");
	}
	@AfterAll
	static void afterAll() {
		System.out.println("AfterAll()");
	}
	
	// @BeforeEach, @AfterEach // 모든 개별 테스트 메소드 호출 전 후에 실행
	@BeforeEach
	void beforeEach() {
		System.out.println("beforeEach()");
	}
	@AfterEach
	void afterEach() {
		System.out.println("afterEach()");
	}
	
	
	
}
