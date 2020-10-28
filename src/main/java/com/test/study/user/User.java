package com.test.study.user;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name="new_twitty_user")
@Entity(name = "new_twitty_user")
public class User {

	@Id @GeneratedValue
	private Long id;
	@Column(nullable = false)
	private String email;
	private String name;
	private String password;
	private Integer age;
	private boolean isAdult;
	private LocalDate birthDay;// Java8부터 가능. 날짜 정보만 필요할 때 사용

	public void update() {
//		LocalDate now = LocalDate.now();
//		this.age = now.minusYears(birthDay.getYear()).getYear();
//		if(birthDay.plusYears(age).isAfter(now)) {
//			this.age = age-1;
//		}
		if(age > 20)
			this.isAdult = true;
		else
			this.isAdult =false;
	}
}
