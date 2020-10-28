package com.test.study.user;

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
public class UserLoginDto { 
	@NotNull(message = "email은 필수로 넣어야 합니다")
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수로 넣어야 합니다")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[~`!@#$%\\\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message="영문(대소문자),숫자,특수문자 조합으로 8자리 이상 입력해야 합니다")
	private String password;	
}
