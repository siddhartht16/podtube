package com.podtube.repositories;

import com.podtube.models.Podcast;
import org.springframework.data.repository.CrudRepository;


public interface PodcastRepository
	extends CrudRepository<Podcast, Integer> {


}