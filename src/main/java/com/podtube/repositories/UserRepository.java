package com.podtube.repositories;

import com.podtube.models.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository
	extends CrudRepository<User, Integer> {


}