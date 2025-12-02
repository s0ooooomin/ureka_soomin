package com.mycom.myapp.webapp.webmvctest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.net.ssl.SSLEngineResult.Status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mycom.myapp.test.controller.TestController;

@WebMvcTest(TestController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // order 어노테이션 추가를 위한 어노테이션

public class TestControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	@Order(1)
	void test1() throws Exception {
		this.mockMvc
			.perform( get("/hello") )
			.andExpect(status().isOk());
	}

	@Test
	@Order(2)
	void test2() throws Exception {
		this.mockMvc
			.perform( 
				get("/param1")
					.param("id", "123") 	// id
					.param("name", "홍길동") 	// name
			)
			.andExpect(status().isOk());
	}
	
	@Test
	@Order(3)
	void test3() throws Exception {
		this.mockMvc
			.perform( 
//				get("/param2")	// post인데 get으로 보냄 -> Error message = Method 'GET' is not supported.
				post("/param2")
					.param("id", "123") 	// id
					.param("name", "홍길동") 	// name
				)
			.andExpect(status().isOk());
	}
	
	@Test
	@Order(4)
	// succes (오류발생) : TestController에 요청 - 응답 과정에서는 오류 X
	// MockMvc 테스트 과정 중 failure -> 넘어온 것들은 200. success
	// MockMvc 의 응답메시지는 형성 (Failure이므로) but 응답메시지에는 오류 내용 X (오류가 그 과정 이후에 생김)
	void test4() throws Exception {
		this.mockMvc
			.perform( 
				post("/response1")	
					.param("id", "123") 	// id
					.param("name", "홍길동") 	// name
				)
			.andExpect( status().isOk())
			.andExpect( content().string("succes"));
			// import 엉뚱한거 안 하도록 주의
	}

	@Test
	@Order(5)
	void test5() throws Exception {
		this.mockMvc
			.perform( 
				post("/response2")
					.param("id", "123") 
					.param("name", "홍길동")
				)
			.andExpect( status().isOk())
			.andExpect( content().contentType(MediaType.APPLICATION_JSON)) // controller 응답의 type이 application/json인지
			.andExpect( jsonPath("$.result").value("success")); // jsonPath($.result).value == json으로 넘어오는 것 중 result라는 value를 찾음
			
	}
}
