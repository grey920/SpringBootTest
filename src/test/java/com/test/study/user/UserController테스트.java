package com.test.study.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
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
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터
																														// 추가
				.alwaysDo(print()).build();
	}

	@Test
	@DisplayName("회원가입 - 성공")
	public void user_생성_성공_테스트() throws Exception {
		UserCreateDto user = UserCreateDto.builder().name("jung").userId("hambak").password("111111").gender("w").age(32)
				.build();

		this.mockMvc.perform(post("/api/users/").contentType(MediaTypes.HAL_JSON_VALUE)// 안넣어도 동작함
				.content(objectMapper.writeValueAsString(user)) // String으로 변환해서 body에 넣겠다
		).andDo(print()).andExpect(status().isCreated()); // 컨트롤러가 반환하는 값 확인
	}

	@DisplayName("회원가입 - 실패 : id에 null값 입력")
	@ParameterizedTest
	@NullAndEmptySource
	public void user_생성_실패_테스트(String input) throws Exception {
		UserCreateDto user = UserCreateDto.builder()
				.userId(input)
				.name("정겨운")
				.password("1111111")
				.gender("w")
				.age(15)
				.build();

		this.mockMvc.perform(post("/api/users/").contentType(MediaTypes.HAL_JSON_VALUE)// 안넣어도 동작함
				.content(objectMapper.writeValueAsString(user)) // String으로 변환해서 body에 넣겠다
		).andDo(print())
				// assertThat(user.getName()).isBlank();
				.andExpect(status().isBadRequest()); // 컨트롤러가 반환하는 값 확인
	}


	@DisplayName("회원가입 - 실패 : 14세 미만 가입불가")
	@Test
	public void user_14세미만_가입불가_테스트() throws Exception {
		UserCreateDto user = UserCreateDto.builder().age(2).build();

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
				.userId("kyewoon")
				.password(input)
				.gender("w")
				.age(32)
				.build();
		this.mockMvc
				.perform(
						post("/api/users")
						.contentType(MediaTypes.HAL_JSON_VALUE)
						.content(this.objectMapper.writeValueAsString(user)))
				.andDo(print())
				// .andExpect(status().isCreated());
				.andExpect(status().isBadRequest());

	}

//	@Test
//	@DisplayName("로그인 - 성공")  
//	public void 로그인_성공_테스트() throws Exception {
//		this.mockMvc.perform(
//				get("/api/users/login")
//				.contentType(MediaTypes.HAL_JSON_VALUE)
//				.content(this.objectMapper.writeValueAsString(value))
//				);
//	}
}
