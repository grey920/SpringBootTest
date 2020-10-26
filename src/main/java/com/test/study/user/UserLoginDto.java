package com.test.study.user;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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
	@NotEmpty(message = "email은 필수로 넣어야 합니다")
	private String email;
	
	@NotEmpty(message = "비밀번호는 필수로 넣어야 합니다")
	@Pattern(regexp = "^[0-9]{6}$", message="0부터 9까지, 6자리 입력해야 합니다")
	private String password;	
}
