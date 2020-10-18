package com.test.study.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserDomainTest {
	
	/*생성*/
	@Test
	public void User_빌더_테스트() {
		User user = User.builder().build();
		assertThat(user).isNotNull();
	}
	
	@Test
	public void UserCreateDto_빌더_테스트() {
		UserCreateDto user = UserCreateDto.builder().build();
		assertThat(user).isNotNull();
	}
	
//	@Test
//	@DisplayName("Builder로 생성한 User객체")
//	public void User_빌더로_객체_생성_테스트() {
//		User user = User().b
//		
//		assertThat(user).isNotNull();
//		assertThat(user.getName()).isEqualTo(name);
//		assertThat(user.getPassword()).isEqualTo(password);
//	}
	
	@Test
	@DisplayName("Constructor로 생성한 User객체")
	public void User_객체_생성_테스트() {
		String name = "정겨운";
		String password = "1234";

		User user = new User();
		user.setName(name);
		user.setUserId("juwon");
		user.setPassword(password);
		user.setGender("w");
		user.setAge(25);
		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo(name);
		assertThat(user.getPassword()).isEqualTo(password);
	}
	
	@Test
	public void UserCreateDto_객체_생성_테스트() {
		String name = "정겨운";
		String userId = "kyewoon";
		String password = "1234";

		UserCreateDto user = new UserCreateDto();
		user.setName(name);
		user.setUserId(userId);
		user.setPassword(password);
		user.setGender("w");
		user.setAge(25);
		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo(name);
		assertThat(user.getPassword()).isEqualTo(password);
	}
	
	@Test
	public void User_생성_성인_테스트() {
		User user = User.builder().age(20).build();
		user.update();
		
		assertThat(user.isAdult()).isEqualTo(false);
	}
	

	

}
