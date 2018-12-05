package com.podtube.repositories;

import com.podtube.models.Category;
import com.podtube.models.Podcast;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PodcastRepository
	extends CrudRepository<Podcast, Integer> {

	Podcast findByUrlEquals(String url);

	List<Podcast> findPodcastsByCategoriesContaining(Category category);

}