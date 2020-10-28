package com.test.study.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/users",produces= MediaTypes.HAL_JSON_VALUE)
public class UserController {
	
	@Autowired
	UserService service;
	
//	@Autowired
//	private UserValidator userValidator;
	
	@Autowired
	private ModelMapper modelmapper;

	/* 회원 생성 */
	@PostMapping
	public ResponseEntity create(@RequestBody @Valid UserCreateDto userDto, Errors errors){
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors); // 에러를 직접 바디에 담아서 리턴 
													// -> 에러발생! => errors를 Serialize해서 ResponseEntity에 담아야 한다
		}
		userDto.update();
		User newUser = modelmapper.map(userDto, User.class);
		newUser.update();
		if(newUser.isAdult()==false)
			return ResponseEntity.badRequest().body(errors);
		newUser = this.service.save(newUser);
		
		URI uri = linkTo(UserController.class).slash(newUser.getId()).toUri();
		return ResponseEntity.created(uri).body(newUser);
	}
	
	
	// 회원 전체 조회
	@GetMapping
	public ResponseEntity<List<User>> findall(){
		List<User> users = this.service.list();
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	// 회원 아이디 조회
	@GetMapping("{email}")
	public ResponseEntity<User> findByEmail(@PathVariable("email") String email){
		User user = this.service.findByEmail(email);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	// 로그인 
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userDto, Errors errors){
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors);
		}
		User user = modelmapper.map(userDto, User.class);
		User checkedUser = this.service.isEmail(user);
		return new ResponseEntity<Object>(checkedUser,HttpStatus.OK);
	}
	
	// 회원 수정
	@PutMapping
	public ResponseEntity<User> update(@RequestBody User user){
		User newUser = this.service.save(user);
		System.out.println(user.toString());
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	// 회원 삭제
	@DeleteMapping
	public ResponseEntity<User> delete(@RequestBody User user){
		this.service.delete(user);
		return new ResponseEntity<User>(HttpStatus.CREATED);
	}
	
	// 회원 전체 삭제
	@DeleteMapping("/All")
	public void deleteAll(){
		this.service.deleteList();
	}
	
	

}
