package com.podtube.repositories;

import com.podtube.common.UserRole;
import com.podtube.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository
	extends CrudRepository<User, Integer> {

//	@Query("SELECT * FROM User u WHERE"
//			+ " u.userRole=:username")
	List<User> findAllByUserRoleEquals(UserRole userRole);

	User findByIdEqualsAndUserRoleEquals(int userId, UserRole userRole);

}