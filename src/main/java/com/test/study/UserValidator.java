package com.test.study;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.test.study.user.UserCreateDto;

//@Component
public class UserValidator {

	public void validate(UserCreateDto userDto, Errors errors) {
			errors.rejectValue("name", "name should be not null");
			
	}

}
