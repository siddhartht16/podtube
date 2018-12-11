package com.podtube.repositories;

import com.podtube.common.UserRole;
import com.podtube.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository
	extends CrudRepository<User, Integer> {

	//Admin findAllUsers
	List<User> findAllByUserRoleEquals(UserRole userRole);

	//Admin and Normal user, Find by user role and id
	User findByIdEqualsAndUserRoleEquals(int userId, UserRole userRole);

	//Admin login
	User findUserByUsernameEqualsAndPasswordEqualsAndUserRoleEquals(String username,
																	String password,
																	UserRole userRole);

	//Register User
	User findUserByUsernameEquals(String username);

	//Get by id
	User findByIdEquals(int id);

	long countAllByUserRoleEquals(UserRole userRole);
}