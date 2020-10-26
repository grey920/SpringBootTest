package com.test.study.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	// findBy+컬럼명 : 이를 이용한 검색 가능
	public User findByEmail(String email);

	public User findByEmailAndPassword(String email, String password);

//	public List<User> findByNameLike(String name); // 파라미터로 전달된 name과 유사한 user를 찾겠다
	
//	public List<User> findByNameisNull(String name); // name이 null값인 것만 검색

}
