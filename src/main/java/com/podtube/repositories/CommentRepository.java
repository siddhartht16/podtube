package com.podtube.repositories;

import com.podtube.models.Comment;
import org.springframework.data.repository.CrudRepository;


public interface CommentRepository
	extends CrudRepository<Comment, Integer> {


}