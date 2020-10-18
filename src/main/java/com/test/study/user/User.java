package com.test.study.user;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name="new_user", uniqueConstraints = {@UniqueConstraint(name="userId_UNIQUE",columnNames = {"userId"})})
@Entity(name = "new_user")
public class User {

	@Id @GeneratedValue
	private Long id;

	private String name;
	private String userId;
	private String password;
	private String gender;
	private Integer age;
	private boolean isAdult;
	private LocalDate birthday;// Java8부터 가능. 날짜 정보만 필요할 때 사용
	
	public void update() {
		if(age > 20)
			this.isAdult = true;
		else
			this.isAdult =false;
	}
}
