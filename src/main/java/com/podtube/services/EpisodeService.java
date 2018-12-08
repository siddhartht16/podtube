package com.podtube.services;

import com.podtube.models.Category;
import com.podtube.models.Episode;
import com.podtube.models.Podcast;
import com.podtube.repositories.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class EpisodeService {
	@Autowired
	EpisodeRepository episodeRepository;

	@GetMapping("/api/episodes")
	public List<Episode> findAllEpisodes() {
		return (List<Episode>) episodeRepository.findAll();
	}

	@GetMapping("/api/podcasts/{podcastId}/episodes")
	public List<Episode> getEpisodesForPodcasts(@PathVariable("podcastId") int podcastId) {
		return episodeRepository.findEpisodesByPodcastId(podcastId);
	}

	@GetMapping("/api/episodes/{episodeId}")
	public Episode getEpisode(@PathVariable("episodeId") int episodeId) {

		Optional<Episode> data = episodeRepository.findById(episodeId);
		if(data.isPresent()){
			Episode episode = data.get();
			return episode;
		}
		return null;
	}
}
