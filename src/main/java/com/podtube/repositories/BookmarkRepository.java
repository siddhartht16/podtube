package com.podtube.repositories;

import com.podtube.models.Bookmark;
import com.podtube.models.Rating;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface BookmarkRepository
	extends CrudRepository<Bookmark, Integer> {

	List<Bookmark> findAllByUser_IdOrderByCreatedOnDesc(int userId);

}