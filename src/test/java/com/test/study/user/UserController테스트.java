package com.test.study.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashMap;

import org.apache.tomcat.util.file.Matcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class UserController테스트 {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext ctx;

	@Autowired
	private ObjectMapper objectMapper;
	

	@BeforeEach
	public void init() throws  Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터
																														// 추가
				.alwaysDo(print()).build();
		
		UserCreateDto userDto = UserCreateDto
				.builder()
				.name("정겨운")
				.email("aaa@aaa.com")
				.password("1!2@3#AaSsZz")
				.birthDay(LocalDate.of(1992, 1, 18))
				.build();
		userDto.update();
		this.mockMvc.perform(post("/api/users/").contentType(MediaTypes.HAL_JSON_VALUE)// 안넣어도 동작함
				.content(objectMapper.writeValueAsString(userDto)) // String으로 변환해서 body에 넣겠다
		).andDo(print()).andExpect(status().isCreated()); // 컨트롤러가 반환하는 값 확인
	}
	
	@AfterEach
	public void deactivate() throws Exception {
			this.mockMvc.perform(delete("/api/users/all")).andDo(print()).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("회원가입 - 성공")
	public void user_생성_성공_테스트() throws Exception {
		UserCreateDto user = UserCreateDto.builder().name("정다와").email("dawa@google.com").password("^Na13$sgd").birthDay(LocalDate.of(1992, 1, 18))
				.build();
		user.update();

		this.mockMvc.perform(post("/api/users/").contentType(MediaTypes.HAL_JSON_VALUE)// 안넣어도 동작함
				.content(objectMapper.writeValueAsString(user)) // String으로 변환해서 body에 넣겠다
		).andDo(print()).andExpect(status().isCreated()); // 컨트롤러가 반환하는 값 확인
	}

	@DisplayName("회원가입 - 실패 : id에 null값 입력")
	@ParameterizedTest
	@NullAndEmptySource // null or "" 입력
	public void user_생성_실패_테스트(String input) throws Exception {
		UserCreateDto user = UserCreateDto.builder()
				.email(input)
				.name("정겨운")
				.password("1111111")
				.birthDay(LocalDate.of(1992, 1, 18))
				.build();

		this.mockMvc.perform(post("/api/users/").contentType(MediaTypes.HAL_JSON_VALUE)// 안넣어도 동작함
				.content(objectMapper.writeValueAsString(user)) // String으로 변환해서 body에 넣겠다
		).andDo(print())
				.andExpect(status().isBadRequest()); // 컨트롤러가 반환하는 값 확인
	}


	@DisplayName("회원가입 - 실패 : 14세 미만 가입불가")
	@Test
	public void user_14세미만_가입불가_테스트() throws Exception {
		UserCreateDto user = UserCreateDto.builder().birthDay(LocalDate.of(2020, 1, 18)).build();

		this.mockMvc
				.perform(post("/api/users/").contentType(MediaTypes.HAL_JSON_VALUE)
						.content(objectMapper.writeValueAsString(user)))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@DisplayName("회원가입 - 실패 : 비밀번호 유효성 검사")
	@ParameterizedTest
	@ValueSource(strings= {"0", "가나다라마바사","1234567891011","1,2나!다라@"})
	public void 회원가입_비밀번호_유효성검사_테스트(String input) throws Exception{
		UserCreateDto user = UserCreateDto.builder()
				.name("정겨운")
				.email("kyewoon")
				.password(input)
				.birthDay(LocalDate.of(1992, 1, 18))
				.build();
		this.mockMvc
				.perform(
						post("/api/users")
						.contentType(MediaTypes.HAL_JSON_VALUE)
						.content(this.objectMapper.writeValueAsString(user)))
				.andDo(print())
				.andExpect(status().isBadRequest());

	}
	
//	@Test
//	@DisplayName("로그인 - 성공")  
//	public void 로그인_성공_테스트() throws Exception {
//
//		//		mockMvc.perform(
////				get("/api/users/login")
////				.contentType(MediaTypes.HAL_JSON_VALUE)
////				.content(this.objectMapper.writeValueAsString(user)))
////		.andDo(print())
////		.andExpect(status().isOk());
////		HashMap<String, String> user = new HashMap<String,String>();
////		user.put("email", "aaa@aaa.com");
////		user.put("password", "1!2@3#AaSsZz");
//		
//		UserLoginDto user = new UserLoginDto();
//		user.setEmail("aaa@aaa.com");
//		user.setPassword("1!2@3#AaSsZz");
//		
//		this.mockMvc.perform(
//				post("/api/users/login/")
//				.contentType(MediaTypes.HAL_JSON_VALUE)
//				.content(objectMapper.writeValueAsString(user))
//		).andDo(print())
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("name").value("정겨운"))
//		;
//	}
	
	@Test
	@DisplayName("로그인 - 실패")  
	public void 로그인_실패_테스트() throws Exception {
		
		mockMvc.perform(
				get("/api/users/login")
				.param("email","ccc@asdf.com")
				.param("password", "1!2@3#AaSsZz")
		).andDo(print())
		.andExpect(status().isBadRequest());

	}

	@Test
	@DisplayName("특정회원 삭제 - 성공")
	public void 회원삭제_성공_테스트() throws Exception {
		User user = User.builder().id((long) 2).build();
		this.mockMvc.perform(
				delete("/api/users/")
				.contentType(MediaTypes.HAL_JSON_VALUE)
				.content(objectMapper.writeValueAsString(user))
		).andDo(print()).andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("회원 수정 - 성공")
	public void 회원수정_성공_테스트() throws Exception {
		User user = new User();
		user.setId((long) 1);
		user.setEmail("aaa@aaa.com");
		user.setPassword("1!2@3#AaSsZz");
		user.setName("정함박");
		user.setBirthDay(LocalDate.of(1983, 6, 2));

		mockMvc.perform(
				put("/api/users/")
				.contentType(MediaTypes.HAL_JSON_VALUE)
				.content(this.objectMapper.writeValueAsString(user))
		).andDo(print())
		.andExpect(status().isCreated());
	}
	
	
	
}
