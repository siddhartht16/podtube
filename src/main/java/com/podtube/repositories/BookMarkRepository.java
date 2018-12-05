package com.podtube.repositories;

import com.podtube.models.Bookmark;
import org.springframework.data.repository.CrudRepository;


public interface BookMarkRepository
	extends CrudRepository<Bookmark, Integer> {
}