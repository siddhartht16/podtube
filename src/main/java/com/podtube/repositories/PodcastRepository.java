package com.podtube.repositories;

import com.podtube.models.Category;
import com.podtube.models.Podcast;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PodcastRepository
	extends CrudRepository<Podcast, Integer> {

	Podcast findByUrlEquals(String url);

	@Query(value = "SELECT * FROM podcast p " +
			"INNER JOIN " +
			"podcast_categories pc " +
			"ON p.id = pc.podcast_id " +
			"WHERE " +
			"pc.categories_id = ?1", nativeQuery = true)
	List<Podcast> getPodcastsForCategory(int categoryId);
}