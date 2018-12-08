package com.podtube.repositories;

import com.podtube.models.Comment;
import com.podtube.models.Podcast;
import com.podtube.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CommentRepository
	extends CrudRepository<Comment, Integer> {

//	List<Comment> findCommentsByPodcast_IdAndOrderByCreatedOnDesc(int podcastId);
//
//	List<Comment> findCommentsByUser_IdAndOrderByCreatedOnDesc(int userId);

}