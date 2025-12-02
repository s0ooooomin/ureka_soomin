package com.mycom.myapp.basic;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;

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
class TestAssert2 {
	
	int getStringLength(String str) { return str.length(); }
	@Test 
	@Order(6)
	void test6() {
//		String str = "hello";
		String str = null;
		// 특정 Exception이 나는지 확인하는 함수
		assertThrows( NullPointerException.class, () -> getStringLength(str), " NullPointerException throws ");
	}
	
	// 그룹테스트
	int m1() { return 4; }
	boolean m2() { return false; }
	String m3() { return "hello"; }
	@Test 
	@Order(7)
	void test7() {
		// 관련성이 있는 테스트를 한꺼번에 묶ㅇ서
		// 순서대로 호출해 정확한 결과가 나오는지 확인가능
		assertAll("묶음테스트",
				() -> assertEquals(4, m1()), 
				() -> assertTrue(m2()), 
				() -> assertNotNull(m3())
			);
	}
	
	// Array
	int[] expectedArray = {1,2,3};	// 기댓값
//	int[] actualArray = {1,2,3};	// 실제값
	int[] actualArray = {1,2,3,4};	// 실제값2 array lengths differ, expected: <3> but was: <4>

	@Test 
	@Order(8)
	void test8() {
		assertArrayEquals(expectedArray, actualArray, " 두 정수배열이 같다.");
	}

	// Collection
	List<String> expectedList = List.of("abc", "def");
//	List<String> actualList = List.of("abc", "def");
//	List<String> actualList = List.of("abc", "def", "ghi"); // ==> iterable lengths differ, expected: <2> but was: <3>
	List<String> actualList = List.of("ghi", "def"); // ==> iterable contents differ at index [0], expected: <abc> but was: <ghi>
	@Test 
	@Order(9)
	void test9() {
		assertIterableEquals(expectedList, actualList, " 두 정수배열이 같다.");
	}
	
	// 객체비교
	@Test 
	@Order(10)
	void test10() {
		String str1 = "hello";
//		String str2 = str1; // str1 단일 객체를 참조 // success
		String str2 = new String("hello"); // str1 단일 객체를 참조 
		
		assertSame(str1, str2, "두 객체는 같다."); 
	}

	@Test 
	@Order(11)
	void test11() {
		String str1 = "hello";
//		String str2 = str1; // str1 단일 객체를 참조 // success
		String str2 = new String("hello"); // str1 단일 객체를 참조 
		
		assertEquals(str1, str2, "두 객체는 같다."); // 값이 아니라 객체 자체를 비교 -> str1, str2 는 다름.
	}

	// AssertNotSame()
	
	void testBL() {
		System.out.println("testBL()");
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test 
	@Order(12)
	void test12() {
		assertTimeout(Duration.ofSeconds(1), () -> testBL(), "1초 미만 수행 테스트");
		// testBL의 수행시간이 1초 미만인지 여부
	}
	
	
}
