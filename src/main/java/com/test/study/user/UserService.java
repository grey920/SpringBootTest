package com.test.study.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	/**회원 등록 + 수정 => save 메소드로 공통 사용 
	 * alt+shift+J : 메소드 관련된 정보가 주석으로 보임
	 * @param user
	 * @return
	 */
	public User save(User user) {
		User newUser = this.repository.save(user);
		return newUser;
	}

	// 회원 전체 조회
	public List<User> list() {
		List<User> users = this.repository.findAll();
		return users;
	}
	
	
	// 아이디로 회원조회
	public User findByEmail(String email) {
		return this.repository.findByEmail(email);
	}
	
	// 로그인 작업
	public User isEmail(User userMap) {
		User user = this.findByEmail(userMap.getEmail());
		if(user != null) {// 해당 아이디가 있는 경우
			if(user.getPassword().equals(userMap.getPassword()))//db에 있는 비번과 비교
				return user; // 일치하면 user 리턴
			else {
				return null;
			}
		}else
			return null;
		
	}

//	
//	public User findByIdAndPassword(String userId,String password) {
//		return this.repository.findByUserIdAndPassword(userId, password);
//	}


	/** 회원 삭제
	 * @param user
	 */
	public void delete(User user) {
		this.repository.delete(user);
	}
}
