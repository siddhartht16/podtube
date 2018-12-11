package com.podtube.repositories;

import com.podtube.models.Episode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface EpisodeRepository
	extends CrudRepository<Episode, Integer> {

	Episode findByEnclosureLinkEquals(String enclosure_link);

	List<Episode> findEpisodesByPodcastId(int podcastId);
}