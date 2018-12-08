package com.podtube.services;

import com.podtube.models.Category;
import com.podtube.models.Episode;
import com.podtube.models.Podcast;
import com.podtube.repositories.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//	@GetMapping("/api/episodes")
//	public ResponseEntity<List<Episode>> findAllEpisodes() {
//
//		return new ResponseEntity<>((List<Episode>) episodeRepository.findAll(), HttpStatus.OK);
//	}

	@GetMapping("/api/podcasts/{podcastId}/episodes")
	public ResponseEntity<List<Episode>>  getEpisodesForPodcasts(@PathVariable("podcastId") int podcastId) {
		return new ResponseEntity<>(episodeRepository.findEpisodesByPodcastId(podcastId), HttpStatus.OK);
	}

	@GetMapping("/api/episodes/{episodeId}")
	public ResponseEntity<Episode> getEpisode(@PathVariable("episodeId") int episodeId) {

		Optional<Episode> data = episodeRepository.findById(episodeId);
		if (data.isPresent()) {
			Episode episode = data.get();
			return new ResponseEntity<>(episode, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
