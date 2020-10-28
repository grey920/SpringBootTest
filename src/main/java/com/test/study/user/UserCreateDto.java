package com.test.study.user;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateDto { //Entity클래스가 DTO클래스를 따로 생성하는 것이 좋다...

	@NotEmpty(message = "이름은 필수로 넣어야 합니다")
	private String name;
	
	@NotEmpty(message = "email은 필수로 넣어야 합니다")
	@Email
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수로 넣어야 합니다")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[~`!@#$%\\\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message="영문(대소문자),숫자,특수문자 조합으로 8자리 이상 입력해야 합니다")
	private String password;
	
	@NotNull
	private LocalDate birthDay;
	
	@Min(14)
	private Integer age;
	
	public void update() {
		// birthday로 나이 입력
		LocalDate now = LocalDate.now();
		this.age = now.minusYears(birthDay.getYear()).getYear();
		if(birthDay.plusYears(age).isAfter(now)) {
			this.age = age-1;
		}
		
	}
	
}
