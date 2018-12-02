package com.podtube.repositories;

import com.podtube.models.Episode;
import org.springframework.data.repository.CrudRepository;


public interface EpisodeRepository
	extends CrudRepository<Episode, Integer> {


}