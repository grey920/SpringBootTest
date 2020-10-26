package com.test.study.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
	
	@Test
	@DisplayName("Constructor로 생성한 User객체")
	public void User_객체_생성_테스트() {
		String name = "정겨운";
		String password = "1234";

		User user = new User();
		user.setName(name);
		user.setEmail("kyewoon@aaa.com");
		user.setPassword(password);
		user.setAge(25);
		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo(name);
		assertThat(user.getPassword()).isEqualTo(password);
	}
	
	@Test
	public void UserCreateDto_객체_생성_테스트() {
		String name = "정겨운";
		String email = "kyewoon@aaa.com";
		String password = "1234";
		LocalDate birthDay = LocalDate.of(1992, 1, 18);

		UserCreateDto user = new UserCreateDto();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setBirthDay(birthDay);
		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo(name);
		assertThat(user.getPassword()).isEqualTo(password);
	}
	
	@Test
	public void User_생성_성인_테스트() {
		LocalDate birthDay = LocalDate.of(2000, 1, 18);
		User user = User.builder().birthDay(birthDay).build();
		user.update();
		
		assertThat(user.isAdult()).isEqualTo(false);
	}
	
	@Test
	public void User_만_나이_계산_테스트() {
		LocalDate now = LocalDate.now();
		LocalDate userBirthDate = LocalDate.of(1992, 1, 18);
		// 생일이 지났으면 (올해 - 생년월일의 연도)
		int userAge = now.minusYears(userBirthDate.getYear()).getYear();
		
		// 생일이 안지났으면 (올해 - 생년월일의 연도) - 1을 한다
		if (userBirthDate.plusYears(userAge).isAfter(now)) {
			userAge = userAge -1;
		}
		
		User user = User.builder().birthDay(userBirthDate).age(userAge).build();
		
		assertThat(user.getAge()).isEqualTo(28);
		
	}

	

}
